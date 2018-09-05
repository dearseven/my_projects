<template>
	<div class="hello-d_rott" style="width: 100%;height: 100%;" >
		<!-- 因为我配置了webpack编译后的名字是[模块名-原始名字]，这里加了一个[hello-]在前面-->
		<div style="width: 100%;height:20%;" >
			{{ posts }}</div>
		<img :src="imgfile" style="width: 50%;height: 50%;"  />
	</div>
</template>

<script>
	//通过模块化的方式加载图片，图片才会被打包,因为打包后的图片是正在放build目录下，所以在webpack.config.js中配置了publicPath: 'build/'
	//不然访问图片的时候，就要自己添加./build/ 这样的路径不科学啊
	//这虽然访问的图片地址是原地址，但是实际上返回的地址是 webpack打包后的图片地址
	var imgf= require( '../../img/r_4.png');
	export default {
		data: function() {
			return {
				posts: "hello vue!!",
				////因为页面的位置是在外面的所以这里要根据页面的位置来找好尴尬,按道理还有办法
				imgfile:imgf 
			}
		}
	}
</script>

<style scoped="scoped">
	.hello-d_rott {
		background: white;
		color: red;
	}
</style>