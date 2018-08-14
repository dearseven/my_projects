module.exports = {
	entry: {
		m: __dirname + "/app/main/main.js",
		w: __dirname + "/app/welcome/welcome.js"
	}, //已多次提及的唯一入口文件
	output: {
		filename: '[name].js',
		path: __dirname + '/build'
	},
	plugins: [ //webpack插件部分
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
	devtool: 'eval-source-map',
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
						localIdentName: '[name]__[local]--[hash:base64:5]' // 指定css的类名格式
					}
				}]
			},
			{
				test: /\.(png|jpg|gif)$/,
				use: [{
					// 需要下载file-loader和url-loader
					loader: "url-loader",
					options: {
						limit: 50,
						// 图片文件输出的文件夹
						outputPath: "images"
					}
				}]
			}
		]
	}
};