var http = require('http');
var url = require('url');
var util = require('util');
var querystring = require('querystring');
var crypto = require('crypto')
// ----------------导入所有需要被路由的类---------------------
//---变量名就是模块名,要和对应js的expotrs的相同
var a = require('./biz/test/a');// a是测试模块
var intellicyan = require('./biz/route/intellicyan');// 主模块
var holidayInfo = require('./biz/route/holidayinfo');// 节日信息，这是专门做的一个api
var CollectUncatched = require('./biz/route/UncatchedExceptionRecorder');//未知异常捕获记录 
var fhAppSize = require('./biz/route/ttfhSize');//记录凤凰测试那边的屏幕尺寸

var modules = {};//配置模块 如果有配置1，参见53行

var moduleInstance={};//放入模组的实例，避免多次初始化

// http://120.26.226.18:8888/intellicyan/sayHello?name=name&url=url

// ~~~~~~!!!!!!!!!!!启动服务器代码!!!!用cluster启动 也是可以做保护 然后还用了forever（forever start
// server.js）!!!!!!
var cluster = require('cluster');
(function() {
	StartProcess();
})();

function StartProcess(args) {
	if (cluster.isMaster) {
		var numCPUs = require('os').cpus().length;
		console.log("cpu length:" + numCPUs);
		require('os').cpus().forEach(function() {
			cluster.fork();
		});
		cluster.on('exit', function(worker, code, signal) {
			console.log('worker ' + worker.process.pid + ' died');
			if (code != 0) {
				console.log('fork new');
				cluster.fork();
			}
		});
		cluster.on('listening', function(worker, address) {
			console.log("A worker with #" + worker.id + " is now connected to "
					+ address.address + ":" + address.port);
		});
	} else {
		_createHttpServer();
	}
}
/**
 * 这个方法是创建一个http server服务
 */
function _createHttpServer() {

	http.createServer(function(req, res) {
		modules.a = 1;
		modules.intellicyan = 1;
		modules.holidayInfo = 1;
		modules.CollectUncatched=1;
		modules.fhAppSize=1;

		// 发送 HTTP 头部
		// HTTP 状态值: 200 : OK
		// 内容类型: text/plain

		var postbuffer = '';// 这个用于把post的数据转成json供api使用
		var rawStr = '';// 这个是post数据的原格式，用来效验sign
		res.writeHead(200, {
			'Content-Type' : 'text/plain;charset=utf-8'
		});

		req.on("data", function(trunk) {
			// 累加post参数
			postbuffer += trunk;
		}).on("end", function() {
			rawStr = postbuffer;
			// res.end();
			// post参数解析
			if (postbuffer != '') {
				// console.log("raw post:"+postbuffer);
				postbuffer = querystring.parse(postbuffer);
				// 其实貌似本来就是个json不要转来转去了ok？

				// console.log("postbuffer
				// String(json):"+JSON.stringify(postbuffer));
				// postbuffer=util.inspect(postbuffer);
				// console.log("postbuffer2:"+postbuffer);
				// res.write("\n"+postbuffer);
			} else {
			}

			// 解析 url 参数 get参数
			var _getParams = url.parse(req.url, true).query;

			// ----检查签名-----
			var dataPart = rawStr.split("&sign=")[0];
			var signPart = rawStr.split("&sign=")[1];
			var serverSign = md5(sha1(dataPart));
			// console.log(dataPart+","+serverSign+","+signPart);
			if (serverSign != signPart) {
				console.log("invalid sign!")
				res.end("{'err':'1','err_msg':'invalid sign!'}");
				return;
			}

			// -----路由解析----
			route(req, res, postbuffer);
			// -----

			// 发送end响应数据 "Hello World"
			// res.end('\nHello World\n');
		});

	}).listen(8888);
}
// ~~~~~~!!!!!!!!!!!!!!!!!!!!!

/**
 * 当有请求时，这个方法负责处理路由，然后派发到对应的模块
 */
function route(req, res, postParam) {
	var path = url.parse(req.url, true).pathname;
	if (path == "/favicon.ico") {
		return;
	}
	// console.log("\npath:"+path);
	var arr = path.split("/");
	var moduleName = arr[1];
	var moduleOpt = arr[2];
	//console.log("\n"+moduleName+","+moduleOpt);
	if (modules[moduleName] != 1) {
		console.log("not such a module exist:"+moduleName);
		res.end("{'err':'1','err_msg':'not such a module!'}");
		return;// JSON.parse,JSON.stingify
	}
	var obj=moduleInstance[moduleName]
	if(!obj){
		console.log("did not found instance for " + moduleName + ", instance a new one!");
		obj= eval("new " + moduleName + "()");
		moduleInstance[moduleName]=obj
	}else{
		console.log("found instance for " + moduleName + " !");
	}
	// console.log(obj);
	if (typeof (obj) != "undefined" && typeof (obj[moduleOpt]) == "function") {
		var serverRet = obj[moduleOpt](req, res, postParam);// 这里传过去req和res是因为我发现在callback里写return是没用的
															// 所以为了使用所以把res放过去，req是怕用得上
		// console.log("serverRet:"+serverRet);
		// res.end(serverRet);
	} else {
		console.log("not such a function exist in module " + moduleName + "!");
		res.end("{'err':'1','err_msg':'not such a function!'}");
	}
}

/**
 * @param str
 *            字符串
 * @param key
 *            秘钥
 */
function md5(str, key) {
	var decipher = crypto.createHash('md5', key)
	if (key) {
		return decipher.update(str).digest()
	}
	return decipher.update(str).digest('hex')
}

/**
 * @param str
 *            字符串
 */
function sha1(str) {
	var md5sum = crypto.createHash('sha1');
	md5sum.update(str,'utf-8');
	str = md5sum.digest('hex');
	return str;
}

/*
 * TEST http.createServer(function (req, res) { modules.a=1;
 * modules.intellicyan=1;
 *  // 发送 HTTP 头部 // HTTP 状态值: 200 : OK // 内容类型: text/plain var postbuffer='';
 * res.writeHead(200, {'Content-Type': 'text/plain;charset=utf-8'});
 * 
 * req.on("data",function(trunk){ console.log("\ntrunk:"+trunk); //累加post参数
 * postbuffer+=trunk; }).on("end",function(){ console.log("\nend:"+postbuffer);
 * 
 * //res.end(); //post参数解析 if(postbuffer!=''){ res.write("\nsee post string");
 * postbuffer = querystring.parse(postbuffer);
 * postbuffer=util.inspect(postbuffer); res.write("\n"+postbuffer); }else{
 * res.write("\npost is empty string"); }
 * 
 * res.write("\nsee get string"); // 解析 url 参数 get参数 var params =
 * url.parse(req.url, true).query; res.write("\n网站名：" + params.name);
 * res.write("\n网站 URL：" + params.url);
 * 
 * //-----路由解析---- route(req,res); route(req,res,postbuffer); //-----
 * 
 * 
 * //发送end响应数据 "Hello World" res.end('\nHello World\n'); });
 * 
 * 
 * }).listen(8888);
 * 
 * 
 * function route(req,res){ var path=url.parse(req.url, true).pathname;
 * if(path=="/favicon.ico"){ return; } console.log("\npath:"+path); var
 * arr=path.split("/"); var moduleName=arr[1]; var moduleOpt=arr[2];
 * console.log("\n"+moduleName+","+moduleOpt); if(modules[moduleName]!=1){
 * console.log("not such a module exist!") return;//JSON.parse,JSON.stingify }
 * var obj=eval("new "+ moduleName+"()"); //console.log(obj);
 * if(typeof(obj)!="undefined"&&typeof(obj[moduleOpt])=="function"){
 * obj[moduleOpt](req,res); }else{ console.log("not such a function exist in
 * module "+moduleName+"!"); } }
 * 
 */
