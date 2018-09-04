<template>
	<div id="app" style="width:100%;height: 100%;">
		<!--  <img src="../../img/r_4.png">-->
		<router-view style="height: 86%;background-color: gainsboro;"></router-view>
		<div style="width: 100%;height: 14%;background-color: dodgerblue;">
			<div style="width: 33.3%;height: 100%;display: inline-block;float: left;">
				<div class="app-v_center">
					<router-link to="/" class="main_link">
						对话</router-link>
				</div>
			</div>
			<div style="width: 33.3%;height: 100%;display: inline-block;float: left;">
				<div class="app-v_center">
					<router-link to="/h" class="main_link">
					联系人</router-link>
				</div>
			</div>
			<div style="width: 33.3%;height: 100%;display: inline-block;float: left;">
				<div class="app-v_center">
						<router-link to="/b" class="main_link">
					其他</router-link></div>
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
		background-color: #Fc3e50;
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
</style>