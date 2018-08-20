import Vue from 'vue'
import './main.css'; //使用require导入css文件
//引入一个js，反正这里写的也是源码路径，但是会按照webpack打包以后的路径引入,具体我们不管
require('../../js/title_change.js')
import '../../css/global.css'

import banner from "../widgets/banner.vue"

var bn = new Vue({
	el: '#_banner',
	components: {
		//banner是这个bn的第一个子元素，其实这里实例化的是bn，然后他有子元素banner(banner.vue)
		banner
	},
	methods: {
		/*
		 * 一下两个方法其实是要调用banner.vue里面的方法，banner是this的第一个子元素所以~~要再调用一次
		 */
		changeObjects: function(k, v) {
			//顺便说一下 可以通过这个banner的root元素的id来判断，这里打印出来的id就是banner.vue的根元素的id
			console.log(this.$children[0].$el.id)
			//这个改的是一个json
			this.$children[0].changeObjects(k, v)
		},
		changeObject2: function(i, v) {
			//这个改的是一个数组
			this.$children[0].changeObject2(i, v)
		}
	}
})
let _f = () => {
	bn.changeObjects('lastName', "setTimeout");
	bn.changeObject2(0, "setTimeout");
}
window.setTimeout(
	_f,
	1000
)