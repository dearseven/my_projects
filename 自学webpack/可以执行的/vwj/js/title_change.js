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