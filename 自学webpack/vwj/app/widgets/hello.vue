

<template>
	<div class="hello-d_rott">
		<!-- 因为我配置了webpack编译后的名字是[模块名-原始名字]，这里加了一个[hello-]在前面-->
		<div>
			{{ posts }}</div>
		<img :src="imgfile"  />
	</div>
</template>

<script>
	//通过模块化的方式加载图片，图片才会被打包
	var imgf= require( '../../img/r_4.png');
	export default {
		data: function() {
			return {
				posts: "hello vue!!",
				////因为页面的位置是在外面的所以这里要根据页面的位置来找好尴尬,按道理还有办法
				imgfile:"./build/"+imgf 
			}
		}
	}
</script>

<style scoped="scoped">
	.d_rott {
		background: white;
		color: red;
	}
</style>