import Vue from 'vue'
import hello from "../widgets/hello.vue"
import atag from "../widgets/atag.vue"
import './welcome.css'; //使用require导入css文件
//引入一个js，反正这里写的也是源码路径，但是会按照webpack打包以后的路径引入,具体我们不管
require('../../js/title_change.js')
import  '../../css/global.css'

//
new Vue({
	el: '#_bd',
	components: {
		hello,atag
	}
})

new Vue({
	el: '#_router',
	components: {
		atag
	}
})