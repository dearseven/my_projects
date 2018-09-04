<template>
	<div style="margin: 0px;height: 100%;width:100%;">
		<!--<mt-button type="primary">primary</mt-button>-->
		<!--<br/>-->
		<ul style="height: 100%;margin: 0px;" id="dialog_ul" class="noullistyle" v-infinite-scroll="loadMore" infinite-scroll-disabled="loading" infinite-scroll-distance="10">
			<!--router js动态生成-->
			<li v-for=" (value, key) in objects " style="height: 10%;text-align: left; ">
				<router-link class="none" style="white-space : nowrap; display: block;height: 100%;" :to="{ path: 'dialog/'+value.id } " >
					<div style="height: 40%;color: cadetblue;">
						&nbsp;{{value.name}}
					</div>
					<div style="height:60%;color:darkblue;font-size:18px;display:inline-block">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{value.msg}}
					</div>
				</router-link>
			</li>
		</ul>
	</div>
</template>

<script>
	export default {
		created: function() {},
		data() {
			return {
				objects: [{
					id: 1,
					name: "xz ",
					msg: "hello "
				}, {
					id: 2,
					name: "dz ",
					msg: "olleh hello olleh hello olleh hello white-space : normal nowrap white-space : normal nowrap white-space : normal nowrap "
				}]
			}
		},
		methods: {
			loadMore() {
				this.loading = true;
				setTimeout(() => {
//					let last = this.objects[this.objects.length - 1];
//					for(let i = 1; i <= 50; i++) {
//						this.objects.push({
//							id: (last + i),
//							name: "dz ",
//							msg: "olleh "
//						});
//					}
					this.loading = false;
				}, 2500);
			}
		}
	}
</script>

<style>
	.noullistyle {
		list-style: none;
	}
</style>