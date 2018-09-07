<template>
	<div style="width: 100%;height: auto;">
		<span :id="'span_sv_0_'+item[4]" style="float: left;display: inline-block;width: 40%;height: 100%;background-color: #FFE4C4;">
			&nbsp;
			</span>
		<span :id="'span_sv_1_'+item[4]" style="float: left;display: inline-block;width: 60%;height: 100%;background-color: gold;">
				<span style="float: right;">
				{{item[1]}}
				</span>
		</span>
	</div>
</template>

<script>
	//发送消息的组件
	export default {
		props: ["_item"],
		mounted: function() {
			let span1 = document.getElementById("span_sv_1_" + this._item[4]);
			span1.style.paddingBottom = "10px"
			span1.style.paddingTop = "10px"
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
	.send_pop {}
</style>