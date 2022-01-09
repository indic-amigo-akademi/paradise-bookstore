import React from 'react';

import { Badge, Button, InputAdornment, Menu, MenuItem, TextField } from '@mui/material';
import {
	AccountCircleOutlined as ProfileIcon,
	NotificationsOutlined as NotificationsIcon,
	SearchOutlined,
	ShoppingCartOutlined as CartIcon,
} from '@mui/icons-material';

import { DeviceSize, DeviceSizeConstant } from '../../types/DeviceSize';

import './AppHeader.scss';

type AppHeaderProps = {};
type AppHeaderState = {
	search: String;
	deviceSize: DeviceSizeConstant;
	profileMenuAnchor: Element | null;
};

export default class AppHeader extends React.Component<AppHeaderProps, AppHeaderState> {
	state: AppHeaderState = {
		search: '',
		deviceSize: DeviceSize.getDeviceSize(),
		profileMenuAnchor: null,
	};

	componentDidMount(): void {
		console.log(this.state.deviceSize);
	}

	searchSubmitForm(): void {
		console.log(this.state.search);
		if (this.state.search.trim() === '') return;
	}

	closeProfileMenu(): void {
		this.setState({
			profileMenuAnchor: null,
		});
	}
	openProfileMenu(event: Event): void {
		this.setState({
			profileMenuAnchor: event.currentTarget as Element,
		});
	}

	render() {
		const isOpenProfileMenu = Boolean(this.state.profileMenuAnchor);
		return (
			<header className="AppHeader">
				<TextField
					className="AppHeader__search"
					placeholder="Search..."
					size="small"
					InputProps={{
						startAdornment: (
							<InputAdornment position="start">
								<SearchOutlined
									onClick={() => {
										this.searchSubmitForm();
									}}
								/>
							</InputAdornment>
						),
					}}
				/>
				<div className="AppHeader__right">
					<Button color="primary" className="AppHeader__Btn">
						<Badge color="primary" badgeContent={0}>
							<NotificationsIcon />
						</Badge>
						{this.state.deviceSize < DeviceSizeConstant.Tablet ? 'Notifications' : ''}
					</Button>
					<Button color="primary" className="AppHeader__Btn">
						<Badge color="primary" badgeContent={5}>
							<CartIcon />
						</Badge>
						{this.state.deviceSize < DeviceSizeConstant.Tablet ? 'Cart' : ''}
					</Button>

					{this.state.deviceSize < DeviceSizeConstant.Mobile_L ? (
						<React.Fragment>
							<Button
								className="AppHeader__Btn"
								color="primary"
								id="profile-button"
								aria-controls={isOpenProfileMenu ? 'profile-menu' : undefined}
								aria-haspopup="true"
								aria-expanded={isOpenProfileMenu ? 'true' : undefined}
								onClick={this.openProfileMenu.bind(this)}
							>
								<ProfileIcon />
								{this.state.deviceSize < DeviceSizeConstant.Tablet ? 'Profile' : ''}
							</Button>
							<Menu
								id="profile-menu"
								anchorEl={this.state.profileMenuAnchor}
								open={isOpenProfileMenu}
								onClose={this.closeProfileMenu.bind(this)}
								MenuListProps={{
									'aria-labelledby': 'basic-button',
								}}
							>
								<MenuItem onClick={() => {}}>Register</MenuItem>
								<MenuItem onClick={() => {}}>Login</MenuItem>
							</Menu>
						</React.Fragment>
					) : (
						<></>
					)}
				</div>
			</header>
		);
	}
}
