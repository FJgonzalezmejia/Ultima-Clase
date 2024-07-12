package com.ada.mongodb.adaMongoDBClass.security.encrypt;

public interface PasswordEncryptionService {

    String encrypt(String password);
    boolean isPasswordMatch(String password, String encryptedPassword);

}
