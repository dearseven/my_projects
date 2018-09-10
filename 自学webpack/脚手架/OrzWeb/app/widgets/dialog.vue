<template>
	<div style="margin: 0px;height: 100%;width:100%;">
		<div class="app-div-text-v-center" style="color:white;font-size: 1.5rem;margin: 0px;height: 10%;width:100%;background-color: cadetblue;">
			<div style="margin-left:5%;">
				{{name}}&nbsp;{{$route.params.name}}
			</div>
		</div>
		<!--聊天对话 -->
		<!---->
		<div id="dialog_scroll_root" style="width: 100%;height: 80%;top: 10%;position: absolute;overflow:overlay">
			<!-- 动态添加组件
			<component :is="getType(item[0])" :_item="item" v-for="item in datas">
				
			</component> -->

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
		<div style="background-color:#0000;margin-left: 2.5%;overflow:hidden;z-index:1999;position:fixed;bottom:15%;height: 8.5%;width:95%;">
			<input type="text" style="left: 2.5%;width: 74%;height: 99%;float: left;display: inline-block;">
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
			}, 2000)
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
				datas: []
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
					[1, "你在做什么呢1111111111111111111111111?", "2018-09-06 08:01:53", 1, 100],
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
				this.datas.push([new Date().getTime() % 2, new Date().getTime(), "2018-09-06 08:01:53", 1, 100]);
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

	//	window.setTimeout(function() {
	//		window._loadMessage()
	//	}, 2000);
</script>

<style>

</style>