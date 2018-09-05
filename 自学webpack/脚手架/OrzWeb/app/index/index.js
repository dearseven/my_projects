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

Vue.use(Router)
const router = new Router({
	routes: [
		{
			path: '/',
			name: 'chatlist',
			component: chatlist
		}
		,
		{
			path: '/dialog/:id',
			name: 'dialog',
			component: dialog
		}
		,
		{
			path: '/h',
			name: 'hello',
			component: hello
		},
		{
			path: '/b',
			name: 'banner',
			component: banner
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