package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserDTO;
import org.example.exception.UserCreationRetryException;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@Service
public class UserRetryableService implements IUserRetryableService {

    private final UserRepository userRepository;
    public final UserMapper userMapper;

    public UserRetryableService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Retryable(
            value = {DataAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Attempting to create user: {}", userDTO.getEmailAddress());
        // retry simulator
        //        if (true) {
        //            throw new DataAccessException("Simulated failure") {};
        //        }

        User user = userMapper.toEntity(userDTO);
        user.setPassword(hashPassword(user.getPassword()));

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    @Retryable(
            value = {DataAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    @Recover
    public UserDTO recoverCreate(DataAccessException e) {
        log.error("Retry failed for createUser: {}", e.getMessage());
        throw new UserCreationRetryException("Failed to create user after retries", e);
    }

    @Recover
    public void recoverDelete(DataAccessException e) {
        log.error("Retry failed for deleteUser: {}", e.getMessage());
        throw new UserCreationRetryException("Failed to delete user after retries", e);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
