<template>
	<div style="margin: 0px;height: 100%;width:100%;">
		<div class="app-div-text-v-center" style="color:white;font-size: 1.5rem;margin: 0px;height: 10%;width:100%;background-color: cadetblue;">
			<div style="margin-left:5%;">
				{{name}}&nbsp;{{$route.params.name}}
			</div>
		</div>
		<!--聊天对话 -->
		<div>
			<!-- 动态添加组件-->
			<component :is="getType(item[0])" :_item="item" v-for="item in datas">
				
			</component> 
			<!--<ul>
				<li v-for="item in datas">
					<div v-if="item[0]==0">
						<rv :_item="item"></rv>
					</div>
					<div v-if="item[0]==1">
						<sv :_item="item"></sv>
					</div>
				</li>
			</ul>-->
		</div>
	</div>
</template>

<script>
	import send_view from '../widgets/send_view.vue'
	import reciever_view from '../widgets/reciever_view.vue'
	//
	export default {
		created: function() {
			this.toUserId = this.$route.params.id
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
					[1, "你在做什么呢?", "2018-09-06 08:01:53", 1],
					[0, "梦里打字", "2018-09-06 08:02:30", 1],
					[1, "那怕是醉生梦死啊", "2018-09-06 08:02:03", 1],
					[0, "哈哈哈,你知道就好啊", "2018-09-06 08:02:20", 1],
					[1, "那你再睡一下,我9点再联系你", "2018-09-06 08:03:01", 1]
				]
				items.forEach((e, i) => {
					window.setTimeout(() => {
						//console.log(e + " " + i)
						this.datas.push(e);
					}, i * 800)
				})
			},
			getMessage() {

			},
			sendMessage() {

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
</script>

<style>

</style>