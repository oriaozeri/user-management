import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useDispatch, useSelector } from "react-redux";
import type { AppDispatch, RootState } from "../store";
import { clearError } from "../store/user/userSlice";
import { createUser } from "../store/user/userThunks";
import type { CreateUserRequest } from "../store/user/userTypes";
import {
    Box,
    Dialog,
    DialogContent,
    DialogTitle,
    TextField,
    Button,
    Typography
} from "@mui/material";
import "../css/UserForm.css";

interface UserFormProps {
    setShowModal: (value: boolean) => void;
}

const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

function UserForm({ setShowModal }: UserFormProps) {
    const dispatch = useDispatch<AppDispatch>();
    const { error } = useSelector((state: RootState) => state.users);

    const {
        register,
        handleSubmit,
        formState: { errors, isValid },
        reset
    } = useForm<CreateUserRequest>({
        mode: "onChange",
        defaultValues: {
            firstName: "",
            lastName: "",
            emailAddress: "",
            password: ""
        }
    });

    useEffect(() => {
        dispatch(clearError());
    }, [dispatch]);

    const onSubmit = async (data: CreateUserRequest) => {
        const resultAction = await dispatch(createUser(data));
        if (createUser.fulfilled.match(resultAction)) {
            reset();
            setShowModal(false);
        }
    };

    return (
        <Dialog
            open
            onClose={() => setShowModal(false)}
            slotProps={{
                paper: {
                    className: "dialog-paper"
                }
            }}
        >
            <DialogTitle className="dialog-title">Create New User</DialogTitle>
            <DialogContent>
                <Box
                    component="form"
                    onSubmit={handleSubmit(onSubmit)}
                    className="form-container"
                >
                    {error && <Typography color="error">{error}</Typography>}

                    <TextField
                        label="First Name"
                        variant="outlined"
                        fullWidth
                        error={!!errors.firstName}
                        helperText={errors.firstName?.message}
                        {...register("firstName", { required: "First name is required" })}
                    />

                    <TextField
                        label="Last Name"
                        variant="outlined"
                        fullWidth
                        error={!!errors.lastName}
                        helperText={errors.lastName?.message}
                        {...register("lastName", { required: "Last name is required" })}
                    />

                    <TextField
                        label="Email"
                        type="email"
                        variant="outlined"
                        fullWidth
                        error={!!errors.emailAddress}
                        helperText={errors.emailAddress?.message}
                        {...register("emailAddress", {
                            required: "Email is required",
                            pattern: {
                                value: emailRegex,
                                message: "Invalid email format"
                            }
                        })}
                    />

                    <TextField
                        label="Password"
                        type="password"
                        variant="outlined"
                        fullWidth
                        error={!!errors.password}
                        helperText={errors.password?.message}
                        {...register("password", {
                            required: "Password is required",
                            minLength: {
                                value: 6,
                                message: "Password must be at least 6 characters"
                            }
                        })}
                    />

                    <Button
                        type="submit"
                        variant="contained"
                        fullWidth
                        disabled={!isValid}
                        className="submit-button"
                    >
                        Submit
                    </Button>
                </Box>
            </DialogContent>
        </Dialog>
    );
}

export default UserForm;
