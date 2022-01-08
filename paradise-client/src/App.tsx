import React from 'react';

import AppHeader from './compositions/AppHeader/AppHeader';
import AppView from './compositions/AppView/AppView';
import AppFooter from './compositions/AppFooter/AppFooter';
import AppSidebar from './compositions/AppSidebar/AppSidebar';

import './App.scss';

type AppState = {
	isSidebarOpen: boolean;
};

type AppProps = {};

class App extends React.Component<AppProps, AppState> {
	state: AppState = {
		isSidebarOpen: false,
	};

	toggleAppSidebar() {
		this.setState({
			isSidebarOpen: !this.state.isSidebarOpen,
		});
	}

	componentDidMount() {}

	render() {
		return (
			<div className="App">
				<div className={'AppSidebar_container' + (this.state.isSidebarOpen ? '' : ' Sidebar_Close')}>
					<AppSidebar isOpen={this.state.isSidebarOpen} toggleSidebar={this.toggleAppSidebar.bind(this)} />
				</div>
				<div className="AppContent_container">
					<AppHeader />
					<AppView />
					<AppFooter />
				</div>
			</div>
		);
	}
}
export default App;
