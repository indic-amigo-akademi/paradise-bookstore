import React from 'react';
import logo from '../../images/logo.png';

import {
	HomeOutlined as HomeIcon,
	StarOutline as StarIcon,
	BookOutlined as LibraryIcon,
	CategoryOutlined as CategoryIcon,
	PeopleOutline as AuthorsIcon,
} from '@mui/icons-material';

import './AppSidebar.scss';

type AppSidebarProps = {
	isOpen: boolean;
	toggleSidebar: () => void;
};
type AppSidebarState = {};

export default class AppSidebar extends React.Component<AppSidebarProps, AppSidebarState> {
	state: AppSidebarState = {};

	render() {
		return (
			<aside className="AppSidebar">
				<div
					className="AppSidebar_Logo"
					onClick={() => {
						this.props.toggleSidebar();
					}}
				>
					<img src={logo} alt="logo" />
					{this.props.isOpen ? 'Paradise' : ''}
				</div>
				<nav className="AppSidebar_Navbar">
					<ul>
						<li className="AppSidebar_Navitem" title="Home">
							<HomeIcon /> {this.props.isOpen ? 'Home' : ''}
						</li>
						<li className="AppSidebar_Navitem" title='Library'>
							<LibraryIcon />
							{this.props.isOpen ? 'Library' : ''}
						</li>
						<li className="AppSidebar_Navitem" title="Categories">
							<CategoryIcon />
							{this.props.isOpen ? 'Categories' : ''}
						</li>
						<li className="AppSidebar_Navitem" title="Authors">
							<AuthorsIcon />
							{this.props.isOpen ? 'Authors' : ''}
						</li>
						<li className="AppSidebar_Navitem" title='Recommended'>
							<StarIcon />
							{this.props.isOpen ? 'Recommended' : ''}
						</li>
					</ul>
				</nav>
			</aside>
		);
	}
}
