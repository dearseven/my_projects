// This file is required by the index.html file and will
// be executed in the renderer process for that window.
// No Node.js APIs are available in this process because
// `nodeIntegration` is turned off. Use `preload.js` to
// selectively enable features needed in the rendering
// process.

console.log(window.location.href)
// const { BrowserWindow } = require('electron').remote
// let win = new BrowserWindow({ width: 800, height: 600 })
// win.loadURL('https://www.baidu.com')



window.setTimeout(()=>{
    window.location.href='pages/path1.html'
},2000)