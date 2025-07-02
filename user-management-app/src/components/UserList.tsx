import { useCallback, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import type { RootState, AppDispatch } from "../store";
import { fetchUsers, deleteUser } from "../store/user/userThunks";
import UserForm from "./UserForm";
import "../css/UserList.css";
import {
    Box,
    Typography,
    Button,
    Paper,
    List,
    ListItem,
    ListItemText
} from "@mui/material";

function UserList() {
    const dispatch = useDispatch<AppDispatch>();
    const { users } = useSelector((state: RootState) => state.users);
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        dispatch(fetchUsers());
    }, [dispatch]);

    const handleDelete = useCallback(
        (userId: string) => {
            dispatch(deleteUser(userId));
        },
        [dispatch]
    );

    return (
        <Box className="user-list-container">
            <Box className="user-list-header">
                <Typography variant="h5" component="h2">
                    Users List
                </Typography>
                <Button variant="contained" onClick={() => setShowModal(true)}>
                    Add New User
                </Button>
            </Box>

            <List>
                {users.length === 0 ? (
                    <Typography className="no-users-message">
                        No users to display
                    </Typography>
                ) : (
                    users.map((user) => (
                        <Paper key={user.id} className="user-item">
                            <ListItem>
                                <ListItemText
                                    primary={`${user.firstName} ${user.lastName}`}
                                    secondary={user.emailAddress}
                                    className="user-name"
                                />
                                <Box ml="auto">
                                    <Button
                                        variant="contained"
                                        className="delete-btn"
                                        onClick={() => handleDelete(user.id)}
                                    >
                                        Delete
                                    </Button>
                                </Box>
                            </ListItem>
                        </Paper>
                    ))
                )}
            </List>


            {showModal && <UserForm setShowModal={setShowModal} />}
        </Box>
    );
}

export default UserList;
