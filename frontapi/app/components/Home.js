var React = require('react');

var Style = require('./styles/style.css');

var Header = require('./Header');

var Navigation = require('./Navigation');

var Footer = require('./Footer');

var Home = React.createClass({
	
	render: function() {
		return(
			<div className="container">
				<Header />
				
				<div className="contMain">
					<h2 className="titleCont">
						Hi, Welcome to URL management platform!!!
					</h2>
					
					<h3 className="descCont">
						Here you can create and manage the your shortened urls.
					</h3>
					
					<Navigation />
				</div>
				
				<p className="linkHelp">
					
				</p>
				
				<Footer />
			</div>
		);
	}
});

module.exports = Home;