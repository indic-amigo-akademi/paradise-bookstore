import React from 'react';
import RegisterModal from '../../components/RegisterModal/RegisterModal';
import './AppView.scss';

type AppViewProps = {};

type AppViewState = {};

function AppView(props: AppViewProps) {
	return (
		<React.Fragment>
			<main className="AppView">Hello, AppView</main>
		</React.Fragment>
	);
}

export default AppView;
