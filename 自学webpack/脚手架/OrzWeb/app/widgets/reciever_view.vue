<template>
	<div>
		reciever:{{item[1]}}
	</div>
</template>

<script>
	//接收消息的组件
	export default {
		props: ["_item"],
		data() {
			return {
				//因为这个值是父组件传过来了 这里用item替换_item ,不直接修改_item
				item: this._item
			}
		}
	}
</script>

<style>

</style>