package org.example.service;

import org.example.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    void deleteUser(UUID id);

    UserDTO createUserWithValidation(UserDTO userDTO);

    List<UserDTO> getAllUsers();
}
