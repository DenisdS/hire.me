var React = require('react');

var footer = require('./styles/footer.css');

var Footer = React.createClass({
	render: function(){
		return(
			<footer className="footer">
				<h3 className="titleFooter">
					Developed by Denis Santos
				</h3>
			</footer>
		);
	}
});

module.exports = Footer;