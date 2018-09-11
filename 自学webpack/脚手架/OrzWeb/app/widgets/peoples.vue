<template>
	<!-- 联系人 -->
	<!--<mt-index-list>
		<mt-index-section index="A">
			<mt-cell title="Aaron"></mt-cell>
			<mt-cell title="Alden"></mt-cell>
			<mt-cell title="Austin"></mt-cell>
		</mt-index-section>
		<mt-index-section index="B">
			<mt-cell id="B" class="name_item" title="Baldwin"></mt-cell>
			<mt-cell title="Braden"></mt-cell>
		</mt-index-section>
		...
		<mt-index-section index="Z">
			<mt-cell title="Zack"></mt-cell>
			<mt-cell title="Zane"></mt-cell>
			<mt-cell title="Zane"></mt-cell>
			<mt-cell title="Zane"></mt-cell>
			<mt-cell title="Zane"></mt-cell>
			<mt-cell title="Zane"></mt-cell>
			<mt-cell title="Zane"></mt-cell>
		</mt-index-section>
	</mt-index-list>-->
	
	<!--双循环生成组件-->
	<div style="width: 100%;min-height: 1%;height: auto;">
		<mt-index-list>
			<!-- 其实应该要迭代谁就写在谁上,我以前以为是迭代子内容.-->
			<mt-index-section :index="item.groupName" v-for="item in persons" style="overflow:auto;width: 100%;min-height: 1%;height: auto;">
				<div class="name_item" @click="goDialog([one.id,one.name]);" v-for="one in item.groupData" :title="one.name">
					<!--<mt-cell :title="one.name"></mt-cell>-->
					<br/> {{one.name}}
					<br/> &nbsp;
				</div>

			</mt-index-section>
		</mt-index-list>
	</div>
</template>

<script>
	import Router from 'vue-router'
	import dialog from '../widgets/dialog.vue'

	document.body.addEventListener('touchstart', function() {
		//...空函数即可
	});
	export default {
		created() {

		},
		mounted() {

		},
		data() {
			return {
//				router: new Router({
//					routes: [{
//						path: '/dialog/:id/:name',
//						name: 'dialog',
//						component: dialog,
//						meta: {
//							keepAlive: false // 不需要缓存
//						}
//					}]
//				}),
				persons: [{
					"groupName": "A",
					"groupData": [{
						"name": "AB",
						"id": "123"
					}, {
						"name": "AC",
						"id": "123"
					}, {
						"name": "AD",
						"id": "123"
					}]
				}, {
					"groupName": "B",
					"groupData": [{
						"name": "bB",
						"id": "123"
					}, {
						"name": "bC",
						"id": "123"
					}, {
						"name": "bD",
						"id": "123"
					}]
				}, {
					"groupName": "C",
					"groupData": [{
						"name": "CB",
						"id": "123"
					}, {
						"name": "CD",
						"id": "123"
					}, {
						"name": "CED",
						"id": "123"
					}]
				}, {
					"groupName": "D",
					"groupData": [{
						"name": "DCB",
						"id": "123"
					}, {
						"name": "DCD",
						"id": "123"
					}, {
						"name": "DCED",
						"id": "123"
					}]
				}]
			}
		},
		methods: {
			//编程式路由
			goDialog([id, name]) {
				this.$router.push({
					path: `/dialog/` + id + `/` + name
				})
			}
		}
	}
</script>

<style>
	.name_item {
		padding-left: 0.5rem;
		-webkit-tap-highlight-color: transparent;
		background: #fff;
	}
	
	.name_item:hover {
		background: #5552!important;
	}
	
	.name_item:active {
		background: #5552!important;
	}
</style>