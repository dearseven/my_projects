import Vue from 'vue'

import Mint from 'mint-ui';
import 'mint-ui/lib/style.css';
Vue.use(Mint)

//这个是配置好的路由
import Router from 'vue-router'
import hello from '../widgets/hello.vue'
import banner from '../widgets/banner.vue'
import chatlist from '../widgets/chatlist.vue'
import dialog from '../widgets/dialog.vue'
import peoples from '../widgets/peoples.vue'
import me from '../widgets/me.vue'

//
//import send_view from '../widgets/send_view.vue'
//import reciever_view from '../widgets/reciever_view.vue'

Vue.use(Router)
const router = new Router({
	routes: [{
			path: '/',
			name: 'chatlist',
			component: chatlist,
			meta: {
				keepAlive: true // 需要缓存
			}
		},
		{
			path: '/dialog/:id/:name',
			name: 'dialog',
			component: dialog,
			meta: {
				keepAlive: false // 不需要缓存
			}
		},
		{
			path: '/peoples',
			name: 'peoples',
			component: peoples,
			meta: {
				keepAlive: false // 不需要缓存
			}
		},
		{
			path: '/me',
			name: 'me',
			component: me,
			meta: {
				keepAlive: true // 需要缓存
			}
		}
	]
})

//这个是入口
import App from '../widgets/App.vue'
new Vue({
	el: '#app',
	router,
	components: {
		App
	},
	template: '<App/>'
})

//export default new Router({
//routes: [
//  {
//    path: '/',
//    name: 'hello',
//    component: hello
//  },
//  {
//    path: '/b',
//    name: 'banner',
//    component: banner
//  }
//]
//})