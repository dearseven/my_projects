
window.setTimeout(() => {
    //window.location.href='https://www.baidu.com'
}, 2000)

var fs = require("fs")

var os = require('os');
var homedir = os.homedir();
console.log('homedir', homedir);

//引用已经写好的db初始化模块
var db = require('../js_modules/datastore')

const ipcRenderer = require('electron').ipcRenderer;

window.addEventListener("load", () => {
    let folderSelectorBtn = document.getElementById("folderSelector")
    folderSelectorBtn.onclick = () => {
        //选择文件夹,multiSelections表示多选
        ipcRenderer.send('open-directory-dialog', ['openDirectory', 'multiSelections']);

    }
    let fileSelectorBtn = document.getElementById("fileSelector")
    fileSelectorBtn.onclick = () => {
        //选择文件,multiSelections表示多选
        ipcRenderer.send('open-directory-dialog', ['openFile', 'multiSelections']);
    }
}, false)

ipcRenderer.on('selectedItem', megetPath);

function megetPath(event, paths) {
    //console.log(paths)
    if (paths == null) {
        console.log('请选择文件/文件夹')
    } else {
        var first = true
        //保存是openFile还是openDirectory
        var channelidId
        //保存路径
        var fnfs = []
        //这个是在页面上直接引入的普通js的里的
        __toCyanStream(paths).forEach((x) => {
            if (first) {
                first = false
                channelidId = x
                console.log("channelid=" + x)
            } else {
                console.log(x)
                fnfs.push(x)
            }
        })
        //测试一下db啊---
        //如果没有这个key则追加
        if (!db.has("fnfPaths").value()) {
            db.set('fnfPaths', []).write()
        }
        //读出来然后写进去
        var _fnf = db.get('fnfPaths').value()
        // console.log(_fnf)
        __toCyanStream(fnfs).forEach((x) => {
            _fnf.push(x)
        })
        db.set('fnfPaths', _fnf).write()
    }
}

