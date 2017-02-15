var React = require('react');

var axios = require ('axios');

var style = require('./styles/style.css');

var App = require('.././App');

var Header = require('./Header');

var BoxMenu = require('./Navigation');

var Footer = require('./Footer');

var IdShorten = React.createClass({

    changeUrl (e) {
        this.setState({ url: e.target.value });
    },

    changeCustomAlias (e) {
        this.setState({ customalias : e.target.value });
    },
	
	handleSubmit: function(e){
        var self

        e.preventDefault()
        self = this

        var urllong = this.state.url
		var customalias =	this.state.customalias

		axios.get("http://localhost:9090/shortener/id/v1/created?url=" + urllong + '&customAlias=' + customalias).then(function(result){    
			document.getElementsByClassName("returnObjectUrl")[0].innerHTML = (JSON.stringify(result.data));
			console.log(result.data);
		})		
	},

	render: function() {
	
		return(
			<div className="container contSecound">
				<Header />
				
				<BoxMenu />
								
				<div className="contMain">
					<h2 className="titleCont">IdShorten</h2>
					<h3 className="descCont">Shorten yours URLs</h3>
				
					<div className="contForm">
						<form onSubmit={this.handleSubmit}>
							<label className="lbIdShorten">
								What's the URL do you would like to shorten? 
							</label>
							
							<input className="iptIdShorten" type="text" ref="url" placeholder="Ex: http://www.bemobi.com.br/" onChange={this.changeUrl}/>

							<label className="lbIdShorten">
								What's the Custom Alias do you would like to URL (Optional)?
							</label>

							<input className="iptIdShorten" type="text" ref="customalias" placeholder="Ex: ixaz" onChange={this.changeCustomAlias}/>

							<input type="submit" className="btn" value="Shorten URL" />
						</form>
						
						<div className="containerReturn">
							<p className="returnObjectUrl"></p>
						</div>
					</div>	
				</div>
				
				<Footer />
			</div>	
		);
	}
});

module.exports = IdShorten;