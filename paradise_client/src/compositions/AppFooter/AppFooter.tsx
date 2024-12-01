import React from 'react';
// import PropTypes from 'prop-types';
import './AppFooter.scss';
import { FavoriteRounded as LoveIcon } from '@mui/icons-material';

type AppFooterProps = {};
type AppFooterState = {};

function AppFooter(props: AppFooterProps) {
	return (
		<footer className="AppFooter">
			<div className="AppFooter_Copyright">
				Made with <LoveIcon className="LoveIcon" /> by Indic Amigo Akademi
			</div>
		</footer>
	);
}

export default AppFooter;
