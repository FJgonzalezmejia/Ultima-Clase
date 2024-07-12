package com.ada.mongodb.adaMongoDBClass.service;

import com.ada.mongodb.adaMongoDBClass.dto.UserRequestDto;
import com.ada.mongodb.adaMongoDBClass.dto.UserResponseDto;
import com.ada.mongodb.adaMongoDBClass.model.User;
import com.ada.mongodb.adaMongoDBClass.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : userRepository.getAllUsers()){
            userResponseDtos.add(new UserResponseDto(user));
        }
        return userResponseDtos;
    }

    @Override
    public UserResponseDto findUserById(String idUser) {
        return new UserResponseDto(userRepository.findUserById(idUser));
    }

    @Override
    public UserResponseDto createUser(UserRequestDto user) {
        return new UserResponseDto(userRepository.createUser(new User(user)));
    }

    @Override
    public Boolean updateUser(String idUser, UserRequestDto user) {
        return userRepository.updateUser(idUser, new User(user));
    }

    @Override
    public Boolean deleteUser(String idUser) {
        return userRepository.deleteUser(idUser);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
