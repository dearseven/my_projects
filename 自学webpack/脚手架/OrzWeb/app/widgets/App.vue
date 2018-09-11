<template>
	<div id="app" class="main_body" style="overflow: hidden">
		<!--  <img src="../../img/r_4.png">-->
		<div class="main_container_parent">
			<!--这样配置以后需要缓存的就会在keep-alive中渲染然后缓存,而不需要的就会在外面渲染,不会缓存-->
			<!--这种写法结合了v-if为false的时候不渲染的功能,真是劳动人民的智慧-->
			<keep-alive>
				<router-view v-if="$route.meta.keepAlive" class="main_container" style="background-color: aliceblue;"></router-view>
			</keep-alive>
			<router-view v-if="!$route.meta.keep_alive" class="main_container" style="background-color: aliceblue;"></router-view>
		</div>
		<div id="main_footer_" class="main_footer" style="background-color: dodgerblue;">
			<div style="font-size: 0.8rem;width: 33.3%;height: 100%;display: inline-block;float: left;">
				<div class="app-v_center" style="overflow-y: hidden;">
					<router-link to="/" class="main_link">
						对话</router-link>
				</div>
			</div>
			<div style="font-size:  0.8rem;width: 33.3%;height: 100%;display: inline-block;float: left;">
				<div class="app-v_center" style="overflow-y: hidden;">
					<router-link to="/peoples" class="main_link">
						联系人</router-link>
				</div>
			</div>
			<div style="font-size:  0.8rem;width: 33.3%;height: 100%;display: inline-block;float: left;">
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
		name: 'App',
		mounted() {
			//因为键盘弹出会引起百分比布局的比例失调,所以用px换算
			let bodyBaseHeight = (document.documentElement.clientHeight || document.body.clientHeight);
			document.getElementById("main_footer_").style.height = (bodyBaseHeight * 0.09) + "px";
		}
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
	
	.app-div-text-v-center {
		/*设置div的文本居中*/
		line-height: 225%;
		//text-align: center;
		/*其他设置*/
		font-size: 1.1em;
		color: white;
	}
	/*div只显示一行*/
	
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
	/*start-这一整套是为了建立一个content+footer的结构*/
	
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
		bottom: 9%;
		width: 100%;
		min-height: 90.5%;
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
		height: 9%;
		z-index: 9999;
	}
	/*end-这一整套是为了建立一个content+footer的结构*/
	/*下面这个css是为了让div刚和内容一样大*/
	
	.div_min_fit_content {
		width: -moz-fill-available;
		width: -moz-available;
		/* FireFox目前这个生效 */
		width: fill-available;
		width: -webkit-fill-available;
		width: -webkit-fit-content;
		/*		width: -webkit-min-content;
*/
	}
</style>