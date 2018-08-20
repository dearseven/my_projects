<template>
	<div>
		我用到了组合vue组件：第二个列表用了atag组件
		<br/>
		<ul id="v-for-object" class="banner-ul">
			<li v-for="(value, key) in object" class="banner-li">
				<a href="">{{ key }}={{ value }}</a>
			</li>
		</ul>
		<br/>
		<ul id="v-for-object" class="banner-ul">
			<li v-for="value in object2" class="banner-li">
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

	export default {
		created: function() {

		},
		data() {
			return {
				object: {
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
		}
	}
</script>

<style>
	ul li {}
	
	.ul {
		list-style-type: none;
	}
	
	.li {
		list-style-type: none;
		float: left;
		width: 208px;
	}
</style>