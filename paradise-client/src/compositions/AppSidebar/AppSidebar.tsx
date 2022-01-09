import React from 'react';
import logo from '../../images/logo.png';

import {
	HomeOutlined as HomeIcon,
	StarOutline as StarIcon,
	BookOutlined as LibraryIcon,
	CategoryOutlined as CategoryIcon,
	PeopleOutline as AuthorsIcon,
	AccountCircleOutlined as ProfileIcon,
	ExpandLess as CollapseIcon,
	ExpandMore as ExpandIcon,
} from '@mui/icons-material';

import './AppSidebar.scss';
import { Box } from '@mui/material';
import { DeviceSize, DeviceSizeConstant } from '../../types/DeviceSize';

type AppSidebarProps = {
	isOpen: boolean;
	toggleSidebar: () => void;
};
type AppSidebarState = {
	deviceSize: DeviceSizeConstant;
	showProfileMenu: boolean;
};

export default class AppSidebar extends React.Component<AppSidebarProps, AppSidebarState> {
	state: AppSidebarState = {
		deviceSize: DeviceSize.getDeviceSize(),
		showProfileMenu: false,
	};

	toggleProfileMenu() {
		if (!this.props.isOpen) {
			this.props.toggleSidebar();
		}
		this.setState({
			showProfileMenu: !this.state.showProfileMenu,
		});
	}

	render() {
		return (
			<Box sx={{ bgcolor: 'primary.main', color: 'primary.contrastText' }} className="AppSidebar">
				<div
					className="AppSidebar_Logo"
					onClick={() => {
						this.props.toggleSidebar();
					}}
				>
					<img src={logo} alt="logo" />
					{this.props.isOpen ? 'Paradise Bibliothecca' : ''}
				</div>
				<nav className="AppSidebar_Navbar">
					{this.state.deviceSize >= DeviceSizeConstant.Mobile_L ? (
						<ul>
							<li
								className="AppSidebar_Navitem"
								title="Profile"
								onClick={() => {
									this.toggleProfileMenu();
								}}
							>
								<ProfileIcon />{' '}
								{this.props.isOpen ? (
									<>
										Profile
										{this.state.showProfileMenu ? <CollapseIcon /> : <ExpandIcon />}
									</>
								) : (
									<></>
								)}
							</li>
							<span
								className={
									'AppSidebar_AccordionSummary' +
									(this.state.showProfileMenu ? '' : ' AppSidebar_Accordion_Close')
								}
							>
								<li className="AppSidebar_Navitem">Register</li>
								<li className="AppSidebar_Navitem">Login</li>
							</span>
						</ul>
					) : (
						<></>
					)}
					<ul>
						<li className="AppSidebar_Navitem" title="Home">
							<HomeIcon /> {this.props.isOpen ? 'Home' : ''}
						</li>
						<li className="AppSidebar_Navitem" title="Library">
							<LibraryIcon />
							{this.props.isOpen ? 'Library' : ''}
						</li>
						<li color="primary" className="AppSidebar_Navitem" title="Categories">
							<CategoryIcon />
							{this.props.isOpen ? 'Categories' : ''}
						</li>
						<li className="AppSidebar_Navitem" title="Authors">
							<AuthorsIcon />
							{this.props.isOpen ? 'Authors' : ''}
						</li>
						<li className="AppSidebar_Navitem" title="Recommended">
							<StarIcon />
							{this.props.isOpen ? 'Recommended' : ''}
						</li>
					</ul>
				</nav>
			</Box>
		);
	}
}
