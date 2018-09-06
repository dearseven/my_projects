<template>
	<div style="margin: 0px;height: 100%;width:100%;">
		<!--<mt-button type="primary">primary</mt-button>-->
		<!--<br/>-->
		<ul style="height: 100%;margin: 0px;width:100%;" id="dialog_ul" class="noullistyle" v-infinite-scroll="loadMore" infinite-scroll-disabled="loading" infinite-scroll-distance="10">
			<!--router js动态生成-->
			<li v-for=" (value, key) in objects " style="height: 15%;text-align: left;width:100%; ">
				<router-link class="chatlist_link_" style="display: block;height: 98%;width:100%;" :to="{ path: 'dialog/'+value.id } ">
					<div style="height: 40%;color: cadetblue;font-size: 1.15rem;">
						&nbsp;{{value.name}}
					</div>
					<div class="max_1_length_hide" style="height:60%;color:darkblue;font-size:1.3rem;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{value.msg}}
					</div>
				</router-link>
				<div style="width: 100%;height: 1px;background-color: #33333335;"></div>
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
					name: "xz1 ",
					msg: "hello "
				}, {
					id: 1,
					name: "xz2 ",
					msg: "hello "
				}, {
					id: 2,
					name: "dz ",
					msg: "olleh hello olleh hello olleh hello white-space : normal nowrap white-space : normal nowrap white-space : normal nowrap "
				}, {
					id: 2,
					name: "dz ",
					msg: "olleh hello olleh hello olleh hello white-space : normal nowrap white-space : normal nowrap white-space : normal nowrap "
				}, {
					id: 2,
					name: "dz ",
					msg: "olleh hello olleh hello olleh hello white-space : normal nowrap white-space : normal nowrap white-space : normal nowrap "
				}, {
					id: 2,
					name: "dz ",
					msg: "olleh hello olleh hello olleh hello white-space : normal nowrap white-space : normal nowrap white-space : normal nowrap "
				}, {
					id: 2,
					name: "dz ",
					msg: "olleh hello olleh hello olleh hello white-space : normal nowrap white-space : normal nowrap white-space : normal nowrap "
				}, {
					id: 2,
					name: "dz ",
					msg: "olleh hello olleh hello olleh hello white-space : normal nowrap white-space : normal nowrap white-space : normal nowrap "
				}, {
					id: 2,
					name: "dz ",
					msg: "olleh hello olleh hello olleh hello white-space : normal nowrap white-space : normal nowrap white-space : normal nowrap "
				}, {
					id: 2,
					name: "dz ",
					msg: "olleh hello olleh hello olleh hello white-space : normal nowrap white-space : normal nowrap white-space : normal nowrap "
				}, {
					id: 2,
					name: "dz ",
					msg: "olleh hello olleh hello olleh hello white-space : normal nowrap white-space : normal nowrap white-space : normal nowrap "
				}, {
					id: 2,
					name: "dz ",
					msg: "olleh hello olleh hello olleh hello white-space : normal nowrap white-space : normal nowrap white-space : normal nowrap "
				}, {
					id: 2,
					name: "dz end ",
					msg: "olleh hello olleh hello olleh hello white-space : normal nowrap white-space : normal nowrap white-space : normal nowrap "
				}]
			}
		},
		methods: {
			loadMore() {
				this.loading = true;
				setTimeout(() => {
					let last = this.objects[this.objects.length - 1];
					for(let i = 1; i <= 15; i++) {
						this.objects.push({
							id: (last + i),
							name: "dz ",
							msg: "olleh "
						});
					}
					this.loading = false;
				}, 2500);
			}
		}
	}
</script>

<style>
	.chatlist_link_:hover {
		background-color: #55555533;
	}
	
	.chatlist_link_ {
		background-color: aliceblue;
	}
</style>