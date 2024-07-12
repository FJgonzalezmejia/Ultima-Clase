package com.ada.mongodb.adaMongoDBClass.controller;

import com.ada.mongodb.adaMongoDBClass.dto.LoginDto;
import com.ada.mongodb.adaMongoDBClass.dto.TokenDto;
import com.ada.mongodb.adaMongoDBClass.model.User;
import com.ada.mongodb.adaMongoDBClass.security.encrypt.PasswordEncryptionService;
import com.ada.mongodb.adaMongoDBClass.security.jwt.OperationJwt;
import com.ada.mongodb.adaMongoDBClass.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthorizationController {

    private final OperationJwt operationJwt;
    private final UserService userService;
    private final PasswordEncryptionService passwordEncryptionService;

    public AuthorizationController(OperationJwt operationJwt, UserService userService, PasswordEncryptionService passwordEncryptionService) {
        this.operationJwt = operationJwt;
        this.userService = userService;
        this.passwordEncryptionService = passwordEncryptionService;
    }

    @PostMapping
    public ResponseEntity<TokenDto> generationJwt(@RequestBody LoginDto loginDto){
        User userFound = userService.findByEmail(loginDto.getEmail());
        if (userFound != null){
            TokenDto tokenDto = new TokenDto();
            Calendar durationToken = Calendar.getInstance();
            durationToken.add(Calendar.MINUTE, 5);
            String jwt = "";
            if (passwordEncryptionService.isPasswordMatch(loginDto.getPassword(), userFound.getPassword())){
                jwt = operationJwt.generateJwt(userFound, durationToken);
            }

            tokenDto.setJwt(jwt);
            tokenDto.setExpirationDateJwt(durationToken.getTime());
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        }else {
            return new ResponseEntity("The email " + loginDto.getEmail() + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
