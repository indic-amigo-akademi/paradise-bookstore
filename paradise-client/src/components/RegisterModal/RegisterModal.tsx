import React, { ChangeEventHandler, Component } from 'react';
import DateAdapter from '@mui/lab/AdapterMoment';
import { LocalizationProvider, DatePicker } from '@mui/lab';
import {
	Box,
	Button,
	IconButton,
	InputAdornment,
	Modal,
	Step,
	StepLabel,
	Stepper,
	TextField,
	Typography,
} from '@mui/material';
import { VisibilityOutlined as Visibility, VisibilityOffOutlined as VisibilityOff } from '@mui/icons-material';

class PasswordField extends Component {
	state = {
		showPassword: false,
	};

	handleClickShowPassword() {
		this.setState({
			showPassword: !this.state.showPassword,
		});
	}

	render() {
		const { ...otherProps } = this.props;
		return (
			<TextField
				{...otherProps}
				type={this.state.showPassword ? 'text' : 'password'}
				InputProps={{
					endAdornment: (
						<InputAdornment position="end">
							<IconButton
								aria-label="Toggle password visibility"
								onClick={this.handleClickShowPassword.bind(this)}
							>
								{this.state.showPassword ? <Visibility /> : <VisibilityOff />}
							</IconButton>
						</InputAdornment>
					),
				}}
			/>
		);
	}
}

interface RegisterModalState {
	name: String;
	username: String;
	email: String;
	password: String;
	confirmPassword: String;
	dob: Date | null;
	currentStep: number;
}
interface RegisterModalProps {
	isOpen: boolean;
	toggleOpen: (value: boolean) => void;
}

export default class RegisterModal extends Component<RegisterModalProps, RegisterModalState> {
	state: RegisterModalState = {
		name: '',
		username: '',
		email: '',
		password: '',
		confirmPassword: '',
		dob: null,
		currentStep: 0,
	};

	handleFormSubmit() {
		console.log('Form submitted!');
		this.props.toggleOpen(false);
	}

	render() {
		const steps = [
			{
				title: 'Personal Info',
				fields: [
					{
						label: 'Name',
						name: 'name',
						type: 'text',
					},
					{
						label: 'Date of Birth',
						name: 'dob',
						type: 'date',
					},
					{
						label: 'Email',
						name: 'email',
						type: 'email',
					},
				],
			},
			{
				title: 'Account Info',
				fields: [
					{
						label: 'Username',
						name: 'username',
						type: 'text',
					},
					{
						label: 'Password',
						name: 'password',
						type: 'password',
					},
					{
						label: 'Confirm Password',
						name: 'confirmPassword',
						type: 'password',
					},
				],
			},
		];
		const style: Object = {
			position: 'absolute',
			top: '50%',
			left: '50%',
			transform: 'translate(-50%, -50%)',
			width: 400,
			bgcolor: 'background.paper',
			border: '2px solid #000',
			boxShadow: 24,
			p: 4,
		};

		return (
			<Modal
				open={this.props.isOpen}
				onClose={() => this.props.toggleOpen(false)}
				aria-labelledby="register-modal-title"
				aria-describedby="register-modal-description"
			>
				<Box sx={style}>
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
								{steps[this.state.currentStep].fields.map((field, j) => {
									if (field.type === 'date')
										return (
											<LocalizationProvider dateAdapter={DateAdapter} key={j}>
												<DatePicker
													label={field.label}
													value={this.state.dob}
													onChange={(dob) => {
														this.setState({ dob });
													}}
													renderInput={(params) => (
														<TextField
															sx={{
																width: '100%',
																marginTop: 2,
															}}
															{...params}
														/>
													)}
												/>
											</LocalizationProvider>
										);
									if (field.type === 'password')
										return (
											<PasswordField
												sx={{
													width: '100%',
													marginTop: 2,
												}}
												key={j}
												label={field.label}
												name={field.name}
												value={this.state[field.name]}
												onChange={
													((e: Event) =>
														this.setState({
															[field.name]: (e.target as HTMLInputElement)
																.value as String,
														})) as unknown as ChangeEventHandler
												}
											/>
										);
									return (
										<TextField
											sx={{
												width: '100%',
												marginTop: 2,
											}}
											key={j}
											label={field.label}
											name={field.name}
											type={field.type}
											value={this.state[field.name]}
											onChange={
												((e: Event) =>
													this.setState({
														[field.name]: (e.target as HTMLInputElement).value as String,
													})) as unknown as ChangeEventHandler
											}
										/>
									);
								})}
							</>
						) : (
							<Typography sx={{ textAlign: 'center' }}>Click on Submit to create your account</Typography>
						)}
					</Box>
					<Box sx={{ marginTop: 1, display: 'flex' }}>
						<Button
							sx={{
								marginRight: 'auto',
							}}
							variant="contained"
							color="primary"
							disabled={this.state.currentStep === 0}
							onClick={() => {
								this.setState({
									currentStep: this.state.currentStep - 1,
								});
							}}
						>
							Back
						</Button>
						{this.state.currentStep < steps.length && (
							<Button
								variant="contained"
								color="primary"
								onClick={() => this.setState({ currentStep: this.state.currentStep + 1 })}
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
