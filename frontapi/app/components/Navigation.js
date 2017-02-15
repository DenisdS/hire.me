var React = require('react');

var StyleNav = require('./styles/nav.css');

var { Router, Route, Link, browserHistory } = require ('react-router');

var BoxMenu = React.createClass({
	
	render: function() {
		return(
			<nav className="nav">
				<ul>
					<li>
						<Link to='IdShorten'>IdShorten</Link>
					</li>
					<li>
						<Link to='SearchUrl'>SearchURL</Link>
					</li>
				</ul>
			</nav>
		);
	}
});

module.exports = BoxMenu;