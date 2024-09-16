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
import { Box, Collapse, List, ListItem, ListItemButton, ListItemIcon, ListItemText } from '@mui/material';
import { DeviceSize, DeviceSizeConstant } from '../../types/DeviceSize';

type AppSidebarProps = {
	isOpen: boolean;
	toggleSidebar: () => void;
	isLoginOpen: boolean;
	isRegisterOpen: boolean;
	toggleLoginModal: (value: boolean) => void;
	toggleRegisterModal: (value: boolean) => void;
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
				{/* <nav className="AppSidebar_Navbar">
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
				</nav> */}
				<List component="nav" className="AppSidebar_Navbar" sx={{ bgcolor: 'primary.main' }} disablePadding>
					{this.state.deviceSize >= DeviceSizeConstant.Mobile_L ? (
						<React.Fragment>
							<ListItemButton
								onClick={() => {
									this.toggleProfileMenu();
								}}
							>
								<ListItemIcon sx={{ color: 'primary.contrastText', minWidth: 30 }}>
									<ProfileIcon />
								</ListItemIcon>
								{this.props.isOpen ? (
									<React.Fragment>
										<ListItemText primary="Profile" />
										{this.state.showProfileMenu ? <CollapseIcon /> : <ExpandIcon />}
									</React.Fragment>
								) : (
									''
								)}
							</ListItemButton>
							<Collapse in={this.state.showProfileMenu} timeout="auto" unmountOnExit>
								<List component="div" disablePadding>
									<ListItemButton sx={{ pl: 4 }}>
										<ListItemText primary="Register" />
									</ListItemButton>
									<ListItemButton sx={{ pl: 4 }}>
										<ListItemText primary="Login" />
									</ListItemButton>
								</List>
							</Collapse>
						</React.Fragment>
					) : (
						<></>
					)}
					<ListItem className="AppSidebar_Navitem">
						<ListItemIcon sx={{ color: 'primary.contrastText', minWidth: 30 }}>
							<HomeIcon />
						</ListItemIcon>
						{this.props.isOpen ? <ListItemText primary="Home" /> : ''}
					</ListItem>
					<ListItem className="AppSidebar_Navitem">
						<ListItemIcon sx={{ color: 'primary.contrastText', minWidth: 30 }}>
							<LibraryIcon />
						</ListItemIcon>
						{this.props.isOpen ? <ListItemText primary="Library" /> : ''}
					</ListItem>
					<ListItemButton className="AppSidebar_Navitem">
						<ListItemIcon sx={{ color: 'primary.contrastText', minWidth: 30 }}>
							<CategoryIcon />
						</ListItemIcon>
						{this.props.isOpen ? <ListItemText primary="Categories" /> : ''}
					</ListItemButton>
					<ListItemButton className="AppSidebar_Navitem">
						<ListItemIcon sx={{ color: 'primary.contrastText', minWidth: 30 }}>
							<AuthorsIcon />
						</ListItemIcon>
						{this.props.isOpen ? <ListItemText primary="Authors" /> : ''}
					</ListItemButton>
					<ListItemButton className="AppSidebar_Navitem">
						<ListItemIcon sx={{ color: 'primary.contrastText', minWidth: 30 }}>
							<StarIcon />
						</ListItemIcon>
						{this.props.isOpen ? <ListItemText primary="Recommended" /> : ''}
					</ListItemButton>
				</List>
			</Box>
		);
	}
}
