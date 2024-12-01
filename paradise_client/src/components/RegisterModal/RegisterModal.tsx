import React, { Component } from "react";

import { Box, Button, Modal, Step, StepLabel, Stepper, Typography } from "@mui/material";
import { getFormField, FormFieldErrorType, FormFieldStateType } from "@/utils/FormField";
import { Dayjs } from "dayjs";

interface RegisterModalFormError extends FormFieldErrorType {
    name: Array<string>;
    username: Array<string>;
    email: Array<string>;
    password: Array<string>;
    confirmPassword: Array<string>;
    dob: Array<string>;
}
interface RegisterModalState extends FormFieldStateType {
    name: string;
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
    dob: Dayjs | null;
    currentStep: number;
    errors: RegisterModalFormError;
    isLoading: boolean;
}
interface RegisterModalProps {
    isOpen: boolean;
    toggleOpen: (value: boolean) => void;
}

export default class RegisterModal extends Component<RegisterModalProps, RegisterModalState> {
    state: RegisterModalState = {
        name: "",
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
        dob: null,
        currentStep: 0,
        errors: {
            name: [],
            username: [],
            email: [],
            password: [],
            confirmPassword: [],
            dob: []
        },
        isLoading: false
    };

    handleFormSubmit() {
        console.log("Register form submitted!");
        const formData = new FormData(),
            newErrors: RegisterModalFormError = {
                name: [],
                username: [],
                email: [],
                password: [],
                confirmPassword: [],
                dob: []
            },
            dobString = this.state.dob ? this.state.dob.format("YYYY-MM-DD") : "";
        let currentStep = 2;
        formData.append("name", this.state.name);
        formData.append("username", this.state.username);
        formData.append("email", this.state.email);
        formData.append("password", this.state.password);
        formData.append("dob", dobString);

        fetch("/auth/register", {
            method: "POST",
            headers: {
                // 'Content-Type': 'multipart/form-data',
            },
            body: formData
        })
            .then((response) => response.json())
            .then((resJson) => {
                // console.log(resJson);
                if (resJson.success) {
                    this.props.toggleOpen(false);
                } else {
                    for (const key in resJson.message) {
                        // if (Object.prototype.hasOwnProperty.call(resJson.message, key)) {
                        newErrors[key as keyof RegisterModalFormError].push(
                            ...resJson.message[key as keyof RegisterModalFormError]
                        );
                        // }
                        if (["name", "dob", "email"].includes(key)) currentStep = currentStep < 0 ? currentStep : 0;
                        if (["username", "password"].includes(key)) currentStep = currentStep < 1 ? currentStep : 1;
                    }
                    this.setState({ errors: newErrors, currentStep });
                }
            })
            .catch((error) => {
                console.log(error);
            });
    }

    validateFormStep() {
        const emailRegex: RegExp =
                /^(([^<>()[]\.,;:s@"]+(.[^<>()[]\.,;:s@"]+)*)|(".+"))@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}])|(([a-zA-Z-0-9]+.)+[a-zA-Z]{2,}))$/,
            newErrors: RegisterModalFormError = {
                name: [],
                username: [],
                email: [],
                password: [],
                confirmPassword: [],
                dob: []
            };

        switch (this.state.currentStep) {
            case 0:
                if (this.state.name === "") {
                    newErrors.name.push("Name is required");
                }
                if (this.state.name.length < 3 || this.state.name.length > 256) {
                    newErrors.name.push("Name should have between 3 and 256 characters");
                }

                if (this.state.dob === null) {
                    newErrors.dob.push("Date of birth is required");
                }

                if (this.state.email === "") {
                    newErrors.email.push("Email is required");
                }
                if (emailRegex.test(this.state.email)) {
                    newErrors.email.push("Email is invalid");
                }

                this.setState((prevState) => {
                    return {
                        ...prevState,
                        errors: newErrors
                    };
                });

                return newErrors.dob.length === 0 && newErrors.name.length === 0 && newErrors.email.length === 0;
            case 1:
                if (this.state.username === "") {
                    newErrors.username.push("Username is required");
                }
                if (this.state.username.length < 3 && this.state.username.length > 64) {
                    newErrors.username.push("Username should have between 3 and 64 characters");
                }

                if (this.state.password === "") {
                    newErrors.password.push("Password is required");
                }
                if (this.state.password.length < 8 && this.state.password.length > 64) {
                    newErrors.password.push("Password should have between 8 and 64 characters");
                }

                if (this.state.confirmPassword === "") {
                    newErrors.confirmPassword.push("Confirm password is required");
                }
                if (this.state.confirmPassword !== this.state.password) {
                    newErrors.confirmPassword.push("Confirm password should be the same as password");
                }

                this.setState((prevState) => {
                    return {
                        ...prevState,
                        errors: newErrors
                    };
                });

                return (
                    newErrors.username.length === 0 &&
                    newErrors.password.length === 0 &&
                    newErrors.confirmPassword.length === 0
                );
            default:
                return false;
        }
    }

    render() {
        const steps = [
            {
                title: "Personal Info",
                fields: [
                    {
                        label: "Name",
                        name: "name",
                        type: "text",
                        autocomplete: "name"
                    },
                    {
                        label: "Date of Birth",
                        name: "dob",
                        type: "date",
                        autocomplete: "bday"
                    },
                    {
                        label: "Email",
                        name: "email",
                        type: "email",
                        autocomplete: "email"
                    }
                ]
            },
            {
                title: "Account Info",
                fields: [
                    {
                        label: "Username",
                        name: "username",
                        type: "text",
                        autocomplete: "username"
                    },
                    {
                        label: "Password",
                        name: "password",
                        type: "password",
                        autocomplete: "new-password"
                    },
                    {
                        label: "Confirm Password",
                        name: "confirmPassword",
                        type: "password",
                        autocomplete: "new-password"
                    }
                ]
            }
        ];
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
                aria-labelledby="register-modal-title"
                aria-describedby="register-modal-description"
            >
                <Box component="form" sx={style}>
                    <Typography className="register-modal-title" variant="h6" component="h2">
                        Register Form
                    </Typography>
                    <Stepper activeStep={this.state.currentStep} alternativeLabel>
                        {steps.map((step, i) => (
                            <Step key={i}>
                                <StepLabel>{step.title}</StepLabel>
                            </Step>
                        ))}
                    </Stepper>
                    <Box sx={{ marginTop: 1 }}>
                        {this.state.currentStep < steps.length ? (
                            <>
                                {steps[this.state.currentStep].fields.map((field, j) =>
                                    getFormField(field, this.state, (state) => this.setState(state), j)
                                )}
                            </>
                        ) : (
                            <Typography sx={{ textAlign: "center" }}>Click on Submit to create your account</Typography>
                        )}
                    </Box>
                    <Box sx={{ marginTop: 1, display: "flex" }}>
                        <Button
                            sx={{
                                marginRight: "auto"
                            }}
                            variant="contained"
                            color="primary"
                            disabled={this.state.currentStep === 0}
                            onClick={() => {
                                this.setState((prevState) => {
                                    return { currentStep: prevState.currentStep - 1 };
                                });
                            }}
                        >
                            Back
                        </Button>
                        {this.state.currentStep < steps.length && (
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={() => {
                                    if (!this.validateFormStep()) {
                                        return;
                                    }
                                    this.setState((prevState) => {
                                        return { currentStep: prevState.currentStep + 1 };
                                    });
                                }}
                            >
                                Next
                            </Button>
                        )}
                        {this.state.currentStep === steps.length && (
                            <Button
                                variant="contained"
                                color="primary"
                                onClick={() => {
                                    this.handleFormSubmit();
                                }}
                            >
                                Submit
                            </Button>
                        )}
                    </Box>
                </Box>
            </Modal>
        );
    }
}
