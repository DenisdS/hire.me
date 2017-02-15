var React = require('react');

var header = require('./styles/header.css');

var logo = require('./images/logoBemobi.png');

var { Router, Route, Link, hashHistory } = require ('react-router');

var Header = React.createClass({
	render: function(){
		return(
			<header className="header">
				<Link to='Home'>
					<img src={logo} alt="Bemobi" />
				</Link>
			</header>
		);
	}
});

module.exports = Header;