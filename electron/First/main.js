// Modules to control application life and create native browser window
const {
	app,
	BrowserWindow
} = require('electron')
const path = require('path')
const mDialog = require('electron').dialog;
const ipcMain = require('electron').ipcMain;
const CyanLambdaNode = require('./js_modules/cstream')
//---文件下载的依赖
const request = require("request").defaults({
	//    proxy: "http://127.0.0.1:8888",
	rejectUnauthorized: false,
});
const fs = require("fs");

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
	mainWindow.on('closed', function() {
		// Dereference the window object, usually you would store windows
		// in an array if your app supports multi windows, this is the time
		// when you should delete the corresponding element.
		mainWindow = null
	})


	ipcMain.on('open-directory-dialog', function(event, p) {
		mDialog.showOpenDialog({
			properties: p
		}).then(result => {
			//把第一个参数作为表示，因为p[0]是个行为参数啊，所以穿回去，我也知道是什么行为哈哈哈~我真聪明
			let results = new CyanLambdaNode(result.filePaths).addFirst(p[0]).returnArray()
			event.sender.send('selectedItem', results)
		}).catch(err => {
			console.log("err")
		})
	});

	ipcMain.on('testDownloadPorcess', (event, p) => {
		aysncDownload(event).then((ret)=>{
			console.log(ret)//这里最终输Promiss返回的经过await再次传递的aysnc的返回值
			//Promise result=1
		})
		console.log("-----i show first!-----")
	})
}

var aysncDownload = async function(_e) {
	return await testPromiss(_e)//返回Promoss返回的结果:Promise result=1
}

var testPromiss = (_e) => {
	return new Promise((resolve, reject) => {
		runSimpleDown(_e)
		//通过resolve返回结果
		resolve('Promise result=1')
	})
}

var runSimpleDown = function(_e) {
	//process.env.NODE_TLS_REJECT_UNAUTHORIZED = '0';
	//创建文件夹目录
	let progress = 0
	let dirPath = path.join(__dirname, "file");
	console.log("directory path:" + dirPath);
	if (!fs.existsSync(dirPath)) {
		fs.mkdirSync(dirPath);
		console.log("folder make success");
	} else {
		console.log("folder exist");
	}


	let fileName = "SFS1.zip"
	let stream = fs.createWriteStream(path.join(dirPath, fileName));

	// let url =
	// 	'https://192.168.3.213/intelliv/api.php/owncloud/dirToZipForDownload?token=9edc2b41f0465e07af84003d1488ef7e&files={%220d04c460f16357e9596e982b9bf01098%22:%22/local/index.html%22}&user_name=008618373689252'
	let url = 'https://008618373689252:123456@192.168.3.213/owncloud/remote.php/webdav/local/MstarUpgrade.bin'
	let dataSize = 0
	let totolSize = 0
	request({
			"url": url,
			method: "GET",
			// json: true,
			headers: {
				"content-type": "application/x-www-form-urlencoded",
				"Accept": "text/plain",
			},
			body: ""
		}).on('response', function(response) {
			//console.log(response)
			console.log("file size:" + (totolSize = response.headers['content-length'])) // b
			console.log(response.headers['content-type']) // 'image/png'
		})
		.on('data', function(chunk) {
			dataSize = dataSize + chunk.length
			//
			let _progress = parseInt((dataSize /
				totolSize * 100).toFixed(0))
			if ( _progress - progress > 1 || _progress==100) {
				progress = _progress
				_e.sender.send('_progress',progress)
			}
			//
			console.log("duration:" + (dataSize / 1024 / 1000) + "MB,total" + (totolSize / 1024 / 1000) + "MB,progress" + (
				dataSize /
				totolSize * 100) + "%")
		})
		.pipe(stream).on("close", function(err) {
			console.log("4 file[" + fileName + "]download completed");
		});
	// }

}

//------------------------------------------


// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', createWindow)

// Quit when all windows are closed.
app.on('window-all-closed', function() {
	// On macOS it is common for applications and their menu bar
	// to stay active until the user quits explicitly with Cmd + Q
	if (process.platform !== 'darwin') app.quit()
})

app.on('activate', function() {
	// On macOS it's common to re-create a window in the app when the
	// dock icon is clicked and there are no other windows open.
	if (mainWindow === null) createWindow()
})

// In this file you can include the rest of your app's specific main process
// code. You can also put them in separate files and require them here.
