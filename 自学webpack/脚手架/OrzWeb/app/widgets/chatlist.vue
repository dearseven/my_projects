<template>
	<div id="_chatlist">
		<ul id="dialog_ul" class="" v-infinite-scroll="loadMore" infinite-scroll-disabled="loading" infinite-scroll-distance="50">
			<li v-for="(value, key) in objects" class="">
				<span href='key'>{{ value.name }}</span>
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
					name: "xz",
					msg: "hello"
				}, {
					id: 2,
					name: "dz",
					msg: "olleh"
				}]
			}
		},
		methods: {
			loadMore() {
			  this.loading = true;
			  setTimeout(() => {
			    let last = this.objects[this.objects.length - 1];
			    for (let i = 1; i <= 50; i++) {
			      this.objects.push({
					id: (last+i),
					name: "dz"+(last+i),
					msg: "olleh"
				});
			    }
			    this.loading = false;
			  }, 2500);
			}
		}
	}
</script>

<style>

</style>