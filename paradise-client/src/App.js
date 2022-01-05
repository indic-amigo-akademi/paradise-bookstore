import React from 'react';
import { fetch } from 'whatwg-fetch';

import './App.css';

class App extends React.Component {
	state = {
		msg: '',
	};

	componentDidMount() {
		fetch('/home')
			.then((res) => res.text())
			.then((txt) => {
				this.setState({ msg: txt });
				console.log(txt);
			});
	}
	render() {
		return (
			<div className="App">
				<p>{this.state.msg}</p>
			</div>
		);
	}
}
export default App;
