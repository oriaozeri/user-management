package org.example.service;

import org.example.dto.UserDTO;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRetryableService retryableService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRetryableService retryableService, UserRepository userRepository, UserMapper userMapper) {
        this.retryableService = retryableService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO createUserWithValidation(UserDTO userDTO) {
        if (userRepository.existsByEmailAddress(userDTO.getEmailAddress())) {
            throw new IllegalArgumentException("Email address already exists: " + userDTO.getEmailAddress());
        }
        return retryableService.createUser(userDTO);
    }

    @Override
    public void deleteUser(UUID id) {
        retryableService.deleteUser(id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toListDTO(users);
    }

}
