package com.ada.mongodb.adaMongoDBClass.service;

import com.ada.mongodb.adaMongoDBClass.dto.UserRequestDto;
import com.ada.mongodb.adaMongoDBClass.dto.UserResponseDto;
import com.ada.mongodb.adaMongoDBClass.model.User;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();
    UserResponseDto findUserById(String idUser);
    UserResponseDto createUser(UserRequestDto user);
    Boolean updateUser(String idUser, UserRequestDto user);
    Boolean deleteUser(String idUser);
    User findByEmail(String email);
}
