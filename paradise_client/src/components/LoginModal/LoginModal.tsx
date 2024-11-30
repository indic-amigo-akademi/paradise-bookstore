import React, { Box, Button, Modal, Typography } from "@mui/material";
import { Component, ReactNode } from "react";

interface LoginModalState {}

interface LoginModalProps {
    isOpen: boolean;
    toggleOpen: (value: boolean) => void;
}

export default class LoginModal extends Component<LoginModalProps, LoginModalState> {
    state: LoginModalState = {};

    handleFormSubmit() {
        console.log("Login form submitted!");
        const formData = new FormData();
    }

    render() {
        const style: Object = {
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: 400,
            bgcolor: "background.paper",
            border: "2px solid #000",
            boxShadow: 24,
            p: 4
        };

        return (
            <Modal
                open={this.props.isOpen}
                onClose={() => this.props.toggleOpen(false)}
                aria-labelledby="login-modal-title"
                aria-describedby="login-modal-description"
            >
                <Box component="form" sx={style}>
                    <Typography className="login-modal-title" variant="h6" component="h2">
                        Login Form
                    </Typography>
                    <Box sx={{ marginTop: 1 }}>
                        <></>
                    </Box>
                    <Box sx={{ marginTop: 1, display: "flex" }}>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={() => {
                                this.handleFormSubmit();
                            }}
                        >
                            Submit
                        </Button>
                    </Box>
                </Box>
            </Modal>
        );
    }
}
