<template>
	<div style="width: 100%;height: 100%">
		<div style="height: 40px;width: 100%;">
			<mt-header fixed title="个人中心"></mt-header>
		</div>
		<div style="display: block;width: 100%;height: 100%">
			<mt-navbar v-model="selected" style="width: 100%;">
				<mt-tab-item id="1">基本信息</mt-tab-item>
				<mt-tab-item id="2">其他功能</mt-tab-item>
				<!--				<mt-tab-item id="3">选项三</mt-tab-item>
--></mt-navbar>

			<!-- tab-container -->
			<mt-tab-container v-model="selected" style="width: 100%;height: 100%">
				<mt-tab-container-item id="1" style="width: 100%;height: 100%">

					<div class="me_avatar_div">
						<img :src="tou_xiang" class="round_icon" />
						<div style="margin-left: 2rem;">My Name</div>
					</div>

					<div class="me_avatar_div">
						123
					</div>
				</mt-tab-container-item>
				<mt-tab-container-item id="2" style="width: 100%;height: 100%">
					<div class="me_avatar_div">
						456
					</div>
				</mt-tab-container-item>
			</mt-tab-container>
		</div>
	</div>
</template>

<script>
	var imgf = require('../../img/r_4.png');
	export default {
		data() {
			return {
				selected: '1',
				tou_xiang: imgf,
			}
		},
		components: {}
	}
</script>

<style>
	.me_avatar_div {
		flex-direction: row;
		/*(横向从左到右排列==左对齐）*/
		width: 95%!important;
		height: 80px;
		/*		background-color: #FFE4C4;
*/
		margin-left: auto;
		margin-right: auto;
		display: flex!important;
		/*子元素居中*/
		align-items: center;
		/*垂直居中*/
		/*justify-content: center;水平居中*/
	}
	
	.round_icon {
		width: 72px;
		height: 72px;
		display: flex;
		border-radius: 50%;
	}
</style>