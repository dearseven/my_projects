const VueLoaderPlugin = require('vue-loader/lib/plugin')

module.exports = {
	entry: {
		m: __dirname + "/app/main/main.js",
		w: __dirname + "/app/welcome/welcome.js"
	}, //已多次提及的唯一入口文件
	output: {
		filename: '[name].js',
		path: __dirname + '/build',
		//publicPath: __dirname + '/build/out' //指定静态资源 (图片等) 的发布地址
	},
	plugins: [
		new VueLoaderPlugin()
		//webpack插件部分
		//		分割css插件
		//		new ExtractTextWebpackPlugin({
		//			filename: __dirname + "/css/[name].css", //制定编译后的目录
		//			allChunks: true, //把分割的块分别打包
		//
		//		}),
		//	配置html模板， 因为是多页面， 所以需配置多个模板
		//		new HtmlWebpackPlugin({
		//			title: 'main!', //html标题
		//			filename: __dirname + "/build/main/main.html", //文件目录名
		//			template: __dirname + "/main/main.html", //文件模板目录
		//			hash: true, //是否添加hash值
		//			chunks: ["m"] //模板需要引用的js块，vendors是定义的公共块，index是引用的自己编写的块
		//		}),
		//		new HtmlWebpackPlugin({
		//			title: '页面一',
		//			filename: __dirname + "/build/welcome/welcome.html", //文件目录名
		//			template: __dirname + "/welcome/welcome.html",
		//			hash: true,
		//			chunks: ['w']
		//		}),
		//		//	每次清空dist目录
		//		new CleanWebpackPlugin(['dist'])
	],
	devtool: 'cheap-module-source-map', //'cheap-module-eval-source-map','eval-source-map',
	devServer: {
		contentBase: "./build", //本地服务器所加载的页面所在的目录
		historyApiFallback: true, //不跳转
		inline: true //实时刷新
	},
	module: {
		rules: [{
				test: /(\.jsx|\.js)$/,
				use: {
					loader: "babel-loader",
					options: {
						presets: [
							"env", "react"
						]
					}
				},
				exclude: /node_modules/
			},
			{
				test: /\.css$/,
				use: [{
					loader: "style-loader"
				}, {
					loader: "css-loader",
					options: {
						modules: true, // 指定启用css modules
						//						localIdentName: '[name]__[local]--[hash:base64:5]' // 指定css的类名格式
						localIdentName: '[name]-[local]' // 指定css的类名格式
					}
				}]
			},
			{
				test: /\.(png|jpg|gif)$/,
				use: [{
					// 需要下载file-loader和url-loader
					loader: "url-loader",
					//loader: 'url-loader?limit=8192&name=[name].[ext]?[hash]',
					options: {
						limit: 50, //小于8192字节的图片以 base64 的方式引用
						//publicPath: '../../', //你实际项目的引用地址前缀
						outputPath:'./imgs', // 指定打包后的图片位置
						name:'[name]_[hash:base64:7].[ext]'
					}
				}]
			},
			{
				test: /\.vue$/,
				use: [{
					// 需要下载vue-loader
					loader: "vue-loader",
					options: {}
				}]
			}
		]
	},
	resolve: {
		alias: {
			'vue': 'vue/dist/vue.js'
		}
	},
	watch: true,
	mode: 'development'
};