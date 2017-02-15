var React = require('react');

var ReactDOM = require('react-dom');

var IdShorten = require('./components/IdShorten');

var SearchUrl = require('./components/SearchUrl');

var Home = require('./components/Home');


var  { Router, Route, Link, hashHistory } = require('react-router');


var Routes = (
				<Router history={hashHistory}>
					<Route component={Home} path="/" />
					<Route component={IdShorten} path="/IdShorten" />   
					<Route component={SearchUrl} path="/SearchUrl" />
					<Route component={Home} path="/Home" />	
				</Router>
			);


ReactDOM.render(Routes, document.getElementById('app'));