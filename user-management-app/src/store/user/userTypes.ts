export interface User {
    id: string;
    firstName: string;
    lastName: string;
    emailAddress: string;
  }
  
  export interface CreateUserRequest {
    firstName: string;
    lastName: string;
    emailAddress: string;
    password: string;
  }
  
  export interface UserState {
    users: User[];
    error: string | null;
  }
  