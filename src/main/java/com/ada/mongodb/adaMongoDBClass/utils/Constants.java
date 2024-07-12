package com.ada.mongodb.adaMongoDBClass.utils;

public interface Constants {
    String USER_NAME_KEY = "username";
    String CLAIMS_ROLES_KEY = "ada_roles";

    int TOKEN_DURATION_MINUTES = 2440;

    String ADMIN_ROLE = "ADMIN";
    String USER_ROLE = "USER";

    String MISSING_TOKEN_ERROR_MESSAGE = "Missing or wrong token";
    String TOKEN_EXPIRED_MALFORMED_ERROR_MESSAGE = "Token expired or malformed";

    String TEST_CHANGE = "test change 1";
}
