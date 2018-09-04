<template>
	<div class="stl">
		<a :href="hrefTo">
			<!-- 这个插槽在banner.vue的第二个ul用到了-->
			<slot></slot>跳转至{{hrefToName}}
		</a>
	</div>
</template>

<script>
	export default {
		props: ['v_props_href'],
		created: function() {
			// `this` 指向 vm 实例
			console.log(this.v_props_href)
			if(this.v_props_href == "main") {
				this.hrefToName = "主页";
				this.hrefTo = "m.html?" + new Date().getTime();
			} else if(this.v_props_href == "welcome") {
				this.hrefToName = "欢迎页";
				this.hrefTo = "w.html?" + new Date().getTime();
			}
		},
		data() {
			return {
				hrefToName: "",
				hrefTo: ""
			}
		}
	}
</script>

<style>
	.stl {}
</style>