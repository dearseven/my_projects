<template>
	<div id="app" class="main_body" style="">
		<!--  <img src="../../img/r_4.png">-->
		<div class="main_container_parent">
			<keep-alive>
				<router-view class="main_container" style="background-color: aliceblue;"></router-view>
			</keep-alive>
		</div>
		<div class="main_footer" style="background-color: dodgerblue;">
			<div style="font-size: 1.2rem;width: 33.3%;height: 100%;display: inline-block;float: left;">
				<div class="app-v_center" style="overflow-y: hidden;">
					<router-link to="/" class="main_link">
						对话</router-link>
				</div>
			</div>
			<div style="font-size:  1.2rem;width: 33.3%;height: 100%;display: inline-block;float: left;">
				<div class="app-v_center" style="overflow-y: hidden;">
					<router-link to="/h" class="main_link">
						联系人</router-link>
				</div>
			</div>
			<div style="font-size:  1.2rem;width: 33.3%;height: 100%;display: inline-block;float: left;">
				<div class="app-v_center" style="overflow-y: hidden;">
					<router-link to="/b" class="main_link">
						其他</router-link>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'App'
	}
</script>

<style>
	#app {
		font-family: 'Avenir', Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		/*background-color: #Fc3e50;*/
		margin-top: 0px;
		height: 100%;
		width: 100%;
	}
	
	* {
		margin: 0px;
		padding: 0px;
	}
	
	a {
		text-decoration: none;
		color: #333;
	}
	
	.main_link {
		text-decoration: none;
		color: #fff;
	}
	
	.app-v_center {
		/*设置这个div自己居中*/
		width: 50%;
		height: 50%;
		overflow: auto;
		margin: auto;
		position: relative;
		top: 25%;
		left: 0;
		bottom: 0;
		right: 0;
		/*设置div的文本居中*/
		line-height: 225%;
		text-align: center;
		/*其他设置*/
		font-size: 1.1em;
		color: white;
	}
	
	.max_1_length_hide {
		text-overflow: ellipsis;
		word-break: keep-all;
		overflow: hidden;
		white-space: nowrap;
		display: block;
	}
	
	.noullistyle {
		list-style: none;
	}
	
	.main_body {
		display: block;
		position: relative;
		width: 100%;
		height: 100%;
		min-height: 100%;
	}
	
	.main_container_parent {
		position: fixed;
		display: block;
		bottom: 14%;
		width: 100%;
		min-height: 86%;
	}
	
	.main_container {
		position: absolute;
		overflow-y: scroll;
	}
	
	.main_footer {
		position: fixed;
		display: block;
		left: 0;
		bottom: 0;
		width: 100%;
		height: 14%;
		z-index: 9999;
	}
</style>