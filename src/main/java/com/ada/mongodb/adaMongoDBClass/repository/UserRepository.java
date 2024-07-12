package com.ada.mongodb.adaMongoDBClass.repository;

import com.ada.mongodb.adaMongoDBClass.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
    User findUserById(String idUser);
    User createUser(User user);
    Boolean updateUser(String idUser, User user);
    Boolean deleteUser(String idUser);
    User findByEmail(String email);
}
