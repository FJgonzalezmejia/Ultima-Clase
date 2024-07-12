package com.ada.mongodb.adaMongoDBClass.controller;

import com.ada.mongodb.adaMongoDBClass.dto.UserRequestDto;
import com.ada.mongodb.adaMongoDBClass.dto.UserResponseDto;
import com.ada.mongodb.adaMongoDBClass.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void get_all_users_from_not_empty_list_test(){
        //organizar
        List<UserResponseDto> usersMock = new ArrayList<>();
        usersMock.add(new UserResponseDto("1a", "juanito", "juanito@mail.com", LocalDateTime.now()));
        usersMock.add(new UserResponseDto("1b", "maria", "maria@mail.com", LocalDateTime.now()));
        Mockito.when(userService.getAllUsers()).thenReturn(usersMock);
        //actuar
        ResponseEntity<List<UserResponseDto>> response = userController.getAllUsers();

        //afirmar

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    public void get_all_users_from_empty_list_test(){
        //organizar
        Mockito.when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        //actuar
        ResponseEntity<List<UserResponseDto>> response = userController.getAllUsers();

        //afirmar

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void get_all_users_server_error_test(){
        //organizar
        Mockito.when(userService.getAllUsers()).thenThrow(new RuntimeException("An error has occurred during the execution"));
        //actuar
        ResponseEntity<List<UserResponseDto>> response = userController.getAllUsers();

        //afirmar

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void find_user_by_id_existing_test(){
        //organizar

        String userId = "a1";
        UserResponseDto userMock = new UserResponseDto("1a", "juanito", "juanito@mail.com", LocalDateTime.now());
        Mockito.when(userService.findUserById(userId)).thenReturn(userMock);

        //actuar

        ResponseEntity<UserResponseDto> response = userController.findUserById(userId);

        //afirmar

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(userMock, response.getBody());

    }

    @Test
    public void find_user_by_id_not_found_test(){
        //organizar

        String userId = "a1";
        Mockito.when(userService.findUserById(userId)).thenThrow(new NoSuchElementException("User " + userId + " not found"));
        //actuar

        ResponseEntity<UserResponseDto> response = userController.findUserById(userId);

        //afirmar

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        //Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("User " + userId + " not found", response.getBody());

    }

    @Test
    public void create_user_success_test() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto("juanito", "1234", "juanito@mail.com");
        UserResponseDto userResponseDto = new UserResponseDto("1a", "juanito", "juanito@mail.com", LocalDateTime.now());
        Mockito.when(userService.createUser(userRequestDto)).thenReturn(userResponseDto);

        // Act
        ResponseEntity<UserResponseDto> response = userController.createUser(userRequestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userResponseDto, response.getBody());
    }

    @Test
    public void create_user_failure_test() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto("juanito", "1234", "juanito@mail.com");
        Mockito.when(userService.createUser(userRequestDto)).thenThrow(new RuntimeException("Failed to create user"));

        // Act
        ResponseEntity<UserResponseDto> response = userController.createUser(userRequestDto);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void update_user_success_test() {
        // Arrange
        String userId = "123";
        UserRequestDto userRequestDto = new UserRequestDto("juanito", "1234", "juanito@mail.com");
        Mockito.when(userService.updateUser(userId, userRequestDto)).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = userController.updateUser(userId, userRequestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    public void update_user_not_found_test() {
        // Arrange
        String userId = "123";
        UserRequestDto userRequestDto = new UserRequestDto("juanito", "1234", "juanito@mail.com");
        Mockito.when(userService.updateUser(userId, userRequestDto)).thenReturn(false);

        // Act
        ResponseEntity<Boolean> response = userController.updateUser(userId, userRequestDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody());
    }

    @Test
    public void update_user_exception_test() {
        // Arrange
        String userId = "123";
        UserRequestDto userRequestDto = new UserRequestDto("juanito", "1234", "juanito@mail.com");
        Mockito.when(userService.updateUser(userId, userRequestDto)).thenThrow(new NoSuchElementException("User not found"));

        // Act
        ResponseEntity<Boolean> response = userController.updateUser(userId, userRequestDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The user " + userId + " doesn't exist in the data base", response.getBody());
    }

    @Test
    public void delete_user_success_test() {
        // Arrange
        String userId = "123";
        Mockito.when(userService.deleteUser(userId)).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    public void delete_user_not_found_test() {
        // Arrange
        String userId = "123";
        Mockito.when(userService.deleteUser(userId)).thenReturn(false);

        // Act
        ResponseEntity<Boolean> response = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody());
    }

    @Test
    public void delete_user_exception_test() {
        // Arrange
        String userId = "123";
        Mockito.when(userService.deleteUser(userId)).thenThrow(new NoSuchElementException("User not found"));

        // Act
        ResponseEntity<Boolean> response = userController.deleteUser(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("The user " + userId + " doesn't exist in the data base", response.getBody());
    }

}