import React from "react";

import { Badge, Box, Button, InputAdornment, Menu, MenuItem, TextField } from "@mui/material";
import {
    AccountCircleOutlined as ProfileIcon,
    NotificationsOutlined as NotificationsIcon,
    SearchOutlined,
    ShoppingCartOutlined as CartIcon
} from "@mui/icons-material";

import { DeviceSize, DeviceSizeConstant } from "../../types/DeviceSize";

import "./AppHeader.scss";

type AppHeaderProps = {
    isLoginOpen: boolean;
    isRegisterOpen: boolean;
    toggleLoginModal: (value: boolean) => void;
    toggleRegisterModal: (value: boolean) => void;
};
type AppHeaderState = {
    search: String;
    deviceSize: DeviceSizeConstant;
    profileMenuAnchor: Element | null;
};

export default class AppHeader extends React.Component<AppHeaderProps, AppHeaderState> {
    state: AppHeaderState = {
        search: "",
        deviceSize: DeviceSize.getDeviceSize(),
        profileMenuAnchor: null
    };

    componentDidMount(): void {}

    searchSubmitForm(): void {
        console.log(this.state.search);
        if (this.state.search.trim() === "") return;
    }

    closeProfileMenu(): void {
        this.setState({
            profileMenuAnchor: null
        });
    }
    openProfileMenu(event: Event): void {
        this.setState({
            profileMenuAnchor: event.currentTarget as Element
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
                        )
                    }}
                />
                <Box className="AppHeader__right">
                    <Button color="primary" className="AppHeader__Btn" sx={{ minWidth: "unset" }}>
                        <Badge color="primary" badgeContent={0}>
                            <NotificationsIcon />
                        </Badge>
                        {this.state.deviceSize < DeviceSizeConstant.Tablet ? "Notifications" : ""}
                    </Button>
                    <Button color="primary" className="AppHeader__Btn" sx={{ minWidth: "unset" }}>
                        <Badge color="primary" badgeContent={5}>
                            <CartIcon />
                        </Badge>
                        {this.state.deviceSize < DeviceSizeConstant.Tablet ? "Cart" : ""}
                    </Button>

                    {this.state.deviceSize < DeviceSizeConstant.Mobile_L ? (
                        <React.Fragment>
                            <Button
                                className="AppHeader__Btn"
                                color="primary"
                                id="profile-button"
                                aria-controls={isOpenProfileMenu ? "profile-menu" : undefined}
                                aria-haspopup="true"
                                aria-expanded={isOpenProfileMenu ? "true" : undefined}
                                onClick={this.openProfileMenu.bind(this)}
                                sx={{ minWidth: "unset" }}
                            >
                                <ProfileIcon />
                                {this.state.deviceSize < DeviceSizeConstant.Tablet ? "Profile" : ""}
                            </Button>
                            <Menu
                                id="profile-menu"
                                anchorEl={this.state.profileMenuAnchor}
                                open={isOpenProfileMenu}
                                onClose={this.closeProfileMenu.bind(this)}
                                MenuListProps={{
                                    "aria-labelledby": "basic-button"
                                }}
                            >
                                <MenuItem
                                    sx={{
                                        color: "primary.main"
                                    }}
                                    onClick={() => {
                                        this.closeProfileMenu();
                                        this.props.toggleRegisterModal(!this.props.isRegisterOpen);
                                    }}
                                >
                                    Register
                                </MenuItem>
                                <MenuItem
                                    sx={{
                                        color: "primary.main"
                                    }}
                                    onClick={() => {
                                        this.closeProfileMenu();
                                        this.props.toggleLoginModal(!this.props.isLoginOpen);
                                    }}
                                >
                                    Login
                                </MenuItem>
                            </Menu>
                        </React.Fragment>
                    ) : (
                        <></>
                    )}
                </Box>
            </header>
        );
    }
}
