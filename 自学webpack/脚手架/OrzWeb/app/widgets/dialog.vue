<template>
	<div style="margin: 0px;height: 100%;width:100%;">
		<div class="app-div-text-v-center" id="dialog_top_area" style="color:white;font-size: 1.5rem;margin: 0px;height: 10%;width:100%;background-color: cadetblue;">
			<div style="margin-left:5%;">
				{{name}}&nbsp;{{$route.params.name}}
			</div>
		</div>
		<!--聊天对话 -->
		<!---->
		<div id="dialog_scroll_root" style="width: 100%;height: 81%;top: 10%;position: absolute;overflow:overlay">
			<!-- 动态添加组件
			<component :is="getType(item[0])" :_item="item" v-for="item in datas">
				
			</component> -->
			<!--对话显示框 -->
			<div style="width: 100%;height: 100%;">
				<div v-for="item in datas" style="overflow:auto;list-style: none;width: 100%;min-height: 1%;height: auto;">
					<div style="width: 100%;min-height: 1rem;height: auto;" v-if="item[0]==0">
						<rv :_item="item" :_from="$route.params.name"></rv>
					</div>
					<div style="width: 100%;min-height: 1rem;height: auto;" v-if="item[0]==1">
						<sv :_item="item"></sv>
					</div>
				</div>
			</div>
		</div>
		<!-- 输入框,他的高度会再计算一次 -->
		<div id="dialog_txt_root" style="background-color:#0000;margin-left: 2.5%;overflow:hidden;z-index:1999;position:fixed;bottom:9.5%;height: 8.5%;width:95%;">
			<input id="dialog_txt_input" type="text" style="left: 2.5%;width: 74%;height: 99%;float: left;display: inline-block;">
			<mt-button @click="clickSend()" type="primary" style="width: 24%;height: 100%;float: right;display: inline-block;font-size: 1rem;">发送</mt-button>

		</div>
	</div>
</template>

<script>
	import send_view from '../widgets/send_view.vue'
	import reciever_view from '../widgets/reciever_view.vue'
	//
	export default {
		created: function() {
			this.toUserId = this.$route.params.id;
			//将方法注册到window上
			window._loadMessage = this.loadMessage
			console.log("window._loadMessage=" + window._loadMessage)
		},
		destroyed: function() {
			//console.log("dialog destroyed")
			//将方法从window上取消
			window._loadMessage = undefined;
			console.log("window._loadMessage=" + window._loadMessage)
		},
		mounted: function() {
			window.setTimeout(() => {
				this.loadMessage()
			}, 2000);
			//因为键盘弹出会引起百分比布局的比例失调,所以用px换算
			let bodyBaseHeight = (document.documentElement.clientHeight || document.body.clientHeight);
			document.getElementById("dialog_txt_root").style.height=(bodyBaseHeight*0.085)+"px";		
			document.getElementById("dialog_txt_root").style.bottom=(bodyBaseHeight*0.095)+"px";
						document.getElementById("dialog_top_area").style.height=(bodyBaseHeight*0.1)+"px";		

			
//			document.getElementById("dialog_scroll_root").style.height=(bodyBaseHeight*0.81)+"px";	
//			document.getElementById("dialog_scroll_root").style.top=(bodyBaseHeight*0.1)+"px";
//						document.getElementById("dialog_scroll_root").style.bottom=(bodyBaseHeight*0.19)+"px";		

		},
		data() {
			return {
				item: "",
				name: 'To:',
				toUserId: 0,
				/*[
				0收1发,
				消息内容,
				消息时间,
				消息类型1文本,2图片,3语音
				]
				*/
				datas: [],
			}
		},
		methods: {
			getType(type) {
				let t = Number.parseInt(type);
				if(t == 0) {
					return "rv";
				}
				return "sv";
			},
			loadMessage() {
				//这里假设从native端获取的数据 
				const items = [
					[1, "你在做什么呢?", "2018-09-06 08:01:53", 1, 100],
					[0, "梦里打字2222222222222222222222222222", "2018-09-06 08:02:30", 1, 101],
					[1, "那怕是醉生梦死啊33333333333333333333333那怕是醉生梦死啊33333333333333333333333", "2018-09-06 08:02:03", 1, 102],
					[0, "哈哈哈,你知道就好啊444444444444444444444", "2018-09-06 08:02:20", 1, 103],
					[1, "那你再睡一下,我9点再联系你5555555555555555", "2018-09-06 08:03:01", 1, 104],
					[0, "オーケー～また明日ね！6666666666666666666", "2018-09-06 08:03:01", 1, 105]
				]
				items.forEach((e, i) => {
					window.setTimeout(() => {
						//console.log(e + " " + i)
						this.datas.push(e);
						window.setTimeout(
							() => {
								let scroller = document.getElementById("dialog_scroll_root");
								scroller.scrollTop = scroller.scrollHeight;

							}, 300)
					}, i * 500)
				})
			},
			getMessage() {

			},
			sendMessage() {

			},
			clickSend() {
				let inputer=document.getElementById("dialog_txt_input");
				let str=inputer.value;
				
				this.datas.push([new Date().getTime() % 2,
					str, dateFtt("yyyy-MM-dd HH:mm:ss", new Date), 1, 106
				]);
				inputer.value="";
				window.setTimeout(
					() => {
						let scroller = document.getElementById("dialog_scroll_root");
						scroller.scrollTop = scroller.scrollHeight;
					}, 300)
			}
		},
		components: {
			rv: reciever_view,
			sv: send_view
			//			s: {
			//				template: '<div>这是子组件1<div>'
			//			},
			//			r: {
			//				template: '<div>这是子组件2<div>'
			//			}
		}
	}

	/**
 * 使用
 *   var crtTime = new Date(value);
    return top.dateFtt("yyyy-MM-dd hh:mm:ss",crtTime);//直接调用公共JS里面的时间类处理的办法     
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

	//		window.setTimeout(function() {
	//			alert(dateFtt)
	//		}, 2000);
</script>

<style>

</style>