<template>

	<div style="width: 100%;height: auto;">
		<div :id="'span_rv_0_'+item[4]" class="reciever_view_show_txt_c" style="">
			&nbsp;&nbsp;<span style="color:#555555aa;font-size: 0.8rem;">{{_from}}:</span>
			<div class="div_min_fit_content" style="color:#335588;padding-top: 0.5rem;padding-bottom:0.5rem;margin-left: 2rem;font-size:1.15rem;background-color: #FFE4C4;">{{item[1]}}</div>
		</div>
		<span :id="'span_rv_1_'+item[4]" style="float: left;display: inline-block;max-width: 40%;min-width:20%;height: 100%;">
				&nbsp;
		</span>
	</div>

</template>

<script>
	//接收消息的组件
	export default {
		props: ["_item", "_from"],
		mounted: function() {
			let span0 = document.getElementById("span_rv_0_" + this._item[4]);
			span0.style.paddingBottom = "10px"
			span0.style.paddingTop = "10px"
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
	.reciever_view_show_txt_c {
		float: left;
		display: block;
		width: 60%;
		/*max-width: 90%;*/
		height: 100%;
	}
	
	
</style>