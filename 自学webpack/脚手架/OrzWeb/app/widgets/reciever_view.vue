<template>
	<div style="width: 100%;height: auto;">
		<span :id="'span_rv_0_'+item[4]" style="float: left;display: inline-block;width: 60%;height: 100%;background-color: gold;">
				{{item[1]}}
		</span>
		<span :id="'span_rv_1_'+item[4]" style="float: left;display: inline-block;width: 40%;height: 100%;background-color: #FFE4C4;">
				&nbsp;
		</span>
	</div>
</template>

<script>
	//接收消息的组件
	export default {
		props: ["_item"],
		mounted: function() {
			let span0 = document.getElementById("span_rv_0_" + this._item[4]);
			span0.style.paddingBottom="10px"
			span0.style.paddingTop="10px"
		},
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