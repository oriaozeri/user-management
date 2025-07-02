package org.example.service;

import org.example.dto.UserDTO;

import java.util.UUID;

public interface IUserRetryableService {

    UserDTO createUser(UserDTO userDTO);

    void deleteUser(UUID id);
}
