//文件下载

const ipcRenderer = require('electron').ipcRenderer;


window.addEventListener("load", () => {
	let runSimpleDownBtn = document.getElementById("runSimpleDownBtn")
	runSimpleDownBtn.onclick = () => {
		_sendRunSimpleDownEvent()
	}

}, false)

function _sendRunSimpleDownEvent() {
	function refreshProgress(hasGet, totalGet) {
		var flag = document.getElementById('icon-flag'),
			processBar = document.getElementById('process-bar'),
			widthPercentage = Math.round(hasGet / totalGet * 100);
		if (widthPercentage >= 100) {
			widthPercentage = 100;
		}
		flag.style.left = (widthPercentage - 1) + '%';
		processBar.style.width = widthPercentage + '%';
		if (widthPercentage == 0) {
			processBar.style.borderStyle = 'none';
		}
	}
	ipcRenderer.send('testDownloadPorcess', 'param=1');
	//接收主线程发回的消息
	ipcRenderer.on('_progress', function(_e, _p) {
		console.log(_p)
		refreshProgress(_p,100)
	});
}
