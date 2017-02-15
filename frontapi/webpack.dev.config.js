var HtmlWebpackPlugin = require('html-webpack-plugin');

var HTMLWebpackPluginConfig = new HtmlWebpackPlugin({template: 'public/index.html'});

module.exports = {
	entry: './app/App.js',
	output: {
		filename: 'public/bundle.js'
	},
	module: {
		loaders: [
			{
				test: /\.js$/,
				exclude: /node_modules/,
				loader: 'babel-loader',
				query: {
					presets: ['react']
				}
			},
			{
				test: /\.css$/,
				loaders: ['style-loader', 'css-loader']
			},
			{
				test: /\.(gif|png|jpe?g|svg)$/i,
				loader: 'url-loader'
			}
		]
	},
	plugins: [HTMLWebpackPluginConfig]
}