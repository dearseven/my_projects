<template>
	<div id="bannerref">
		我用到了组合vue组件：第二个列表用了atag组件
		<br/>
		<ul id="v-for-object" class="bul">
			<li v-for="(value, key) in objects" class="bli">
				<a href="">{{ key }}_{{ value }}</a>
			</li>
		</ul>
		<br/>
		<ul id="v-for-object" class="ul">
			<li v-for="value in object2" class="bli">
				<atag v_props_href="welcome">
					<!-- 显示这个值用到了插槽，可以看atag.vue-->
					{{value}}
				</atag>
			</li>
		</ul>
	</div>
</template>

<script>
	//至于这为什么要用这个路径，我是猜因为编译的时候实际上是用的入口地址在匹配的
	//如果地址是main.js所以（所以所有入口需要是同层级的目录），所以atag的地址才是../widgets/atag.vue
	import atag from "../widgets/atag.vue"
	import Vue from 'vue'

	export default {
		created: function() {
			this.changeObjects("firstName", "changeBycreated");
		},
		data() {
			return {
				objects: {
					firstName: 'John',
					lastName: 'Doe',
					age: 30
				},
				object2: [
					'John2',
					'Doe2',
					32
				]
			}
		},
		components: {
			atag
		},
		methods: {
			changeObjects: function(k, v) {
				Vue.set(this.objects, k, v)
			},
			changeObject2: function(i, v) {
				Vue.set(this.object2, i, v)
			}
		}
	}
</script>

<style>
	ul li {}
	
	.bul {
		list-style-type: none;
	}
	
	.bli {
		list-style-type: none;
		float: left;
		width: 248px;
	}
</style>