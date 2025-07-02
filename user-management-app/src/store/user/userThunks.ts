import { createAsyncThunk } from '@reduxjs/toolkit';
import type { CreateUserRequest, User } from './userTypes';

export const fetchUsers = createAsyncThunk<User[]>(
  'users/fetchUsers',
  async (_, thunkAPI) => {
    try {
      const res = await fetch('users');
      if (!res.ok) throw new Error('Server Error');
      return await res.json();
    } catch (error: any) {
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const createUser = createAsyncThunk<User, CreateUserRequest>(
  'users/addUser',
  async (user, thunkAPI) => {
    try {
      const res = await fetch('users/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user),
      });
      if (!res.ok) {
        const errorBody = await res.json();
        throw new Error(errorBody.error || 'Server Error');
      }
      return await res.json();
    } catch (error: any) {
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);

export const deleteUser = createAsyncThunk<string, string>(
  'users/deleteUser',
  async (id, thunkAPI) => {
    try {
      const res = await fetch(`users/delete/${id}`, { method: 'DELETE' });
      if (!res.ok) throw new Error('Server Error');
      return id;
    } catch (error: any) {
      return thunkAPI.rejectWithValue(error.message);
    }
  }
);
