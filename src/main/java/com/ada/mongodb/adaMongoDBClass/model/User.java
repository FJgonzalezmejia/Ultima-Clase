package com.ada.mongodb.adaMongoDBClass.model;

import com.ada.mongodb.adaMongoDBClass.dto.UserRequestDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Document(collection = "user_collection")
public class User {

    @Id
    private String idUser;
    private String username;
    private String password;
    private String email;
    private LocalDateTime dateCreation;
    private LocalDateTime dateUpdate;
    private List<RoleEnum> roles;

    public User() {
        this.roles = new ArrayList<>(Collections.singleton(RoleEnum.USER));
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.email = email;
        this.dateCreation = LocalDateTime.now();
        this.roles = new ArrayList<>(Collections.singleton(RoleEnum.USER));
    }

    public User(UserRequestDto userRequestDto) {
        this.username = userRequestDto.getUsername();
        this.password = new BCryptPasswordEncoder().encode(userRequestDto.getPassword());
        this.email = userRequestDto.getEmail();
        this.dateCreation = LocalDateTime.now();
        this.roles = new ArrayList<>(Collections.singleton(RoleEnum.USER));
    }

    public String getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public List<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEnum> roles) {
        this.roles = roles;
    }

    public void updateUser(User user){
        setUsername(user.getUsername());
        setPassword(user.getPassword());
        setEmail(user.getEmail());
        this.dateUpdate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser='" + idUser + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateUpdate=" + dateUpdate +
                ", roles=" + roles +
                '}';
    }
}
