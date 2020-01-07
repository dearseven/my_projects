//文件下载
var fs = require("fs");
var path = require("path");
var request = require("request").defaults({
	//    proxy: "http://127.0.0.1:8888",
	rejectUnauthorized: false,
});
//process.env.NODE_TLS_REJECT_UNAUTHORIZED = '0';


//创建文件夹目录
var dirPath = path.join(__dirname, "file");
console.log("文件夹路径：" + dirPath)
if (!fs.existsSync(dirPath)) {
	fs.mkdirSync(dirPath);
	console.log("文件夹创建成功");
} else {
	console.log("文件夹已存在");
}

//循环多线程下载
// for (let i = 0; i < 60; i++) {
//     let fileName = "out" + intToString(i, 3) + ".ts";
//     let url = "https://xxx.sdhdbd1.com/cb9/sd/gc/g1/670BC531/SD/" + fileName;
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
		//body: 'token=9edc2b41f0465e07af84003d1488ef7e&files={"0d04c460f16357e9596e982b9bf01098":"/local/index.html"}&user_name=008618373689252'
		body: ""
	}).on('response', function(response) {
		console.log("文件大小：" + (totolSize = response.headers['content-length'])) // b
		console.log(response.headers['content-type']) // 'image/png'
	})
	.on('data', function(chunk) {
		dataSize = dataSize + chunk.length
		console.log("当前大小：" + (dataSize / 1024 / 1000) + "MB,总大小" + (totolSize / 1024 / 1000) + "MB，进度" + (dataSize /
			totolSize * 100) + "%")
	})
	.pipe(stream).on("close", function(err) {
		console.log("4 文件[" + fileName + "]下载完毕");
	});
// }

//整数转字符串，不足的位数用0补齐
function intToString(num, len) {
	let str = num.toString();
	while (str.length < len) {
		str = "0" + str;
	}
	return str;
}
