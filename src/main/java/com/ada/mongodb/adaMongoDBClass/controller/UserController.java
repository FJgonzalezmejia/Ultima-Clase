package com.ada.mongodb.adaMongoDBClass.controller;

import com.ada.mongodb.adaMongoDBClass.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ada.mongodb.adaMongoDBClass.dto.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        try{
            List<UserResponseDto> usersFound = userService.getAllUsers();
            if (usersFound.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity("An error has occurred during the execution", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{idUser}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable("idUser") String idUser) {
        try {
            return new ResponseEntity<>(userService.findUserById(idUser), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity("User " + idUser + " not found", HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto user) {
        try {
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        }catch (RuntimeException e){
            return new ResponseEntity("An error has occurred while create the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping("/{idUser}")
    public ResponseEntity<Boolean> updateUser(@PathVariable("idUser") String idUser, @RequestBody UserRequestDto user) {

        try{
            Boolean isUpdated = userService.updateUser(idUser, user);
            if (isUpdated){
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            return new ResponseEntity("The user " + idUser + " doesn't exist in the data base", HttpStatus.NOT_FOUND);
        }

    }


    @DeleteMapping("/{idUser}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("idUser") String idUser) {

        try{
            Boolean isUpdated = userService.deleteUser(idUser);
            if (isUpdated){
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        }catch (NoSuchElementException e){
            return new ResponseEntity("The user " + idUser + " doesn't exist in the data base", HttpStatus.NOT_FOUND);
        }
        //return new ResponseEntity<>(userService.deleteUser(idUser), HttpStatus.OK);
    }
}