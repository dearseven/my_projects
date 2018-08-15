import Vue from 'vue'
import hello from "../widgets/hello.vue"
import './welcome.css'; //使用require导入css文件


//
new Vue({
	el: '#_bd',
	components: {
		hello
	}
})