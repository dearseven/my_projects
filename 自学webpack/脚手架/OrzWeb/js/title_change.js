(function() {
	window.onfocus = function() {
		document.title = '恢复正常噜';
	};
	window.onblur = function() {
		document.title = '快回来~页面崩溃惹！';
	};

	window.setTimeout((function(p) {
		return function(e) {
			document.title = p;
		}
	})("今日はご機嫌いかがですか"), 1000);
})()

/**
 * dateFtt("yyyy-MM-dd hh:mm:ss",new Date())
 * @param {Object} fmt
 * @param {Object} date
 */
function dateFtt(fmt, date) { //author: meizz   
	var o = {
		"M+": date.getMonth() + 1, //月份   
		"d+": date.getDate(), //日   
		"h+": date.getHours(), //小时   
		"m+": date.getMinutes(), //分   
		"s+": date.getSeconds(), //秒   
		"q+": Math.floor((date.getMonth() + 3) / 3), //季度   
		"S": date.getMilliseconds() //毫秒   
	};
	if(/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}