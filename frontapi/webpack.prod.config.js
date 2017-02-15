var Webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');

var DefinePlugin = new Webpack.DefinePlugin({
	'process.env': {
		NODE_ENV: JSON.stringify('production'),
	},	
});

var HTMLWebpackPluginConfig = new HtmlWebpackPlugin({template: 'public/index.html'});
var DedupePlugin = new Webpack.optimize.DedupePlugin();

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
	plugins: [DefinePlugin, HTMLWebpackPluginConfig,  DedupePlugin]
}