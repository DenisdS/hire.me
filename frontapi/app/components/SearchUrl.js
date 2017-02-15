var React = require('react');

var style = require('./styles/style.css');

var App = require('.././App');

var axios = require ('axios');

var Header = require('./Header');

var BoxMenu = require('./Navigation');

var Footer = require('./Footer');

var SearchUrl = React.createClass({

	changeUrl (e) {
        this.setState({ url: e.target.value });
    },

	handleSubmit: function(e){
	
		var url = this.state.url
		
        e.preventDefault()
		
		axios.get("http://localhost:9090/shortener/id/v1/searchURL?url=" + url).then(function(result){    
			console.log(result.data);
			
			var error = "ERR_CODE: 002 Description: SHORTENED_URL_NOT_FOUND";	
			
			if(error == result.data){
				alert("URL not found!");
			}else {
				alert(result.data);
				window.location.href = result.data;
			}
		})
	},

	render: function() {
	
		return(
			<div className="container contSecound">
				<Header />
				
				<BoxMenu />
		
				<div className="contMain">
					<h2 className="titleCont">IdShorten</h2>
					<h3 className="descCont">Search the original URL.</h3>
					
					<div className="contForm">
						<form onSubmit={this.handleSubmit}>
							<label className="lbIdDictionary">
								Please, report the shortened URL 
							</label>
							
							<input className="iptIdDictionary" type="text" ref="url" placeholder="Ex: http://idShort/3rebsw" onChange={this.changeUrl} />
							
							<input type="submit" className="btn" value="Search URL" />
						</form>
					</div>						
				</div>	

					
				<Footer />
			</div>
		);
	}
});

module.exports = SearchUrl;