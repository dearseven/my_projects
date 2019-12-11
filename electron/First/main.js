// Modules to control application life and create native browser window
const { app, BrowserWindow } = require('electron')
const path = require('path')
const mDialog = require('electron').dialog;
const ipcMain = require('electron').ipcMain;
const CyanLambdaNode = require ('./js_modules/cstream')
// Keep a global reference of the window object, if you don't, the window will
// be closed automatically when the JavaScript object is garbage collected.
let mainWindow

function createWindow() {
  // Create the browser window.
  mainWindow = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      //nodeIntegration设置为false以后，则只能在preload中使用node的模块了，这显然不是我的目的啊
      nodeIntegration: true,
      preload: path.join(__dirname, 'preload.js')
    }
  })

  // and load the index.html of the app.
  mainWindow.loadFile('index.html')

  // Open the DevTools.
  // mainWindow.webContents.openDevTools()

  // Emitted when the window is closed.
  mainWindow.on('closed', function () {
    // Dereference the window object, usually you would store windows
    // in an array if your app supports multi windows, this is the time
    // when you should delete the corresponding element.
    mainWindow = null
  })


  ipcMain.on('open-directory-dialog', function (event, p) {
    mDialog.showOpenDialog({
      properties: p
    }).then(result => {
       //把第一个参数作为表示，因为p[0]是个行为参数啊，所以穿回去，我也知道是什么行为哈哈哈~我真聪明
       let results=new CyanLambdaNode(result.filePaths).addFirst(p[0]).returnArray()
       event.sender.send('selectedItem', results)
    }).catch(err=>{
      console.log("err")
    })
  });
}


// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', createWindow)

// Quit when all windows are closed.
app.on('window-all-closed', function () {
  // On macOS it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  if (process.platform !== 'darwin') app.quit()
})

app.on('activate', function () {
  // On macOS it's common to re-create a window in the app when the
  // dock icon is clicked and there are no other windows open.
  if (mainWindow === null) createWindow()
})

// In this file you can include the rest of your app's specific main process
// code. You can also put them in separate files and require them here.
