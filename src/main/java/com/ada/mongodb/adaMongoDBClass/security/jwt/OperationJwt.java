package com.ada.mongodb.adaMongoDBClass.security.jwt;

import com.ada.mongodb.adaMongoDBClass.model.User;

import java.util.Calendar;

public interface OperationJwt {
    String generateJwt(User user, Calendar durationToken);
}
