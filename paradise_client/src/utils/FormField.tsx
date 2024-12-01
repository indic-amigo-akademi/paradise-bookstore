import React, { Component } from "react";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { Dayjs } from "dayjs";
import { IconButton, InputAdornment, TextField, TextFieldProps } from "@mui/material";
import { Visibility, VisibilityOff } from "@mui/icons-material";

interface PasswordFieldState {
    showPassword: boolean;
}

class PasswordField extends Component<TextFieldProps, PasswordFieldState> {
    state = {
        showPassword: false
    };

    handleClickShowPassword() {
        this.setState((prevState) => {
            return { showPassword: !prevState.showPassword };
        });
    }

    render() {
        const { ...otherProps } = this.props;
        return (
            <TextField
                {...otherProps}
                type={this.state.showPassword ? "text" : "password"}
                slotProps={{
                    htmlInput: {
                        endAdornment: (
                            <InputAdornment position="end">
                                <IconButton
                                    aria-label="Toggle password visibility"
                                    onClick={this.handleClickShowPassword.bind(this)}
                                >
                                    {this.state.showPassword ? <Visibility /> : <VisibilityOff />}
                                </IconButton>
                            </InputAdornment>
                        )
                    }
                }}
            />
        );
    }
}

interface FormFieldType {
    label: string;
    name: string;
    type: string;
    autocomplete: string;
}

interface FormFieldErrorType {
    [key: string]: string[];
}

interface FormFieldStateType {
    [key: string]: any;
    errors: FormFieldErrorType;
}

function getFormField(
    field: FormFieldType,
    state: FormFieldStateType,
    setState: React.Dispatch<React.SetStateAction<any>>,
    key: number
) {
    if (field.type === "date") {
        return (
            <DatePicker
                key={key}
                label={field.label}
                value={state[field.name]}
                format="DD-MM-YYYY"
                onChange={(dob) => {
                    setState({
                        ...state,
                        dob
                    });
                }}
                slots={{
                    textField: TextField
                }}
                slotProps={{
                    textField: {
                        helperText: state.errors.dob.length > 0 ? state.errors.dob[0] : "",
                        error: state.errors.dob.length > 0,
                        fullWidth: true,
                        sx: {
                            marginTop: 2
                        }
                    }
                }}
            />
        );
    }

    if (field.type === "password") {
        return (
            <PasswordField
                sx={{
                    width: "100%",
                    marginTop: 2
                }}
                key={key}
                label={field.label}
                name={field.name}
                autoComplete={field.autocomplete}
                value={state[field.name]}
                error={state.errors[field.name].length > 0}
                helperText={state.errors[field.name].length > 0 ? state.errors[field.name][0] : ""}
                onChange={(event: React.ChangeEvent<HTMLInputElement>) =>
                    setState({
                        ...state,
                        [field.name]: event.target.value
                    })
                }
            />
        );
    }

    return (
        <TextField
            sx={{
                width: "100%",
                marginTop: 2
            }}
            key={key}
            label={field.label}
            name={field.name}
            autoComplete={field.autocomplete}
            type={field.type}
            value={state[field.name]}
            error={state.errors[field.name].length > 0}
            helperText={state.errors[field.name].length > 0 ? state.errors[field.name][0] : ""}
            onChange={(event: React.ChangeEvent<HTMLInputElement>) =>
                setState({
                    ...state,
                    [field.name]: event.target.value
                })
            }
        />
    );
}

export { getFormField, PasswordField };
export type { FormFieldType, FormFieldStateType, FormFieldErrorType };
