package com.ada.mongodb.adaMongoDBClass.security.jwt;

import com.ada.mongodb.adaMongoDBClass.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import static com.ada.mongodb.adaMongoDBClass.utils.Constants.*;

@Component
public class OperationJwtImpl implements OperationJwt{

    @Value("${jwt.secret-key}")
    private String secret_key;

    @Override
    public String generateJwt(User user, Calendar durationToken) {
        return Jwts.builder()
                .setSubject(user.getIdUser())
                .claim(USER_NAME_KEY, user.getUsername())
                .claim(CLAIMS_ROLES_KEY, user.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(durationToken.getTime())
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact()
                ;
    }

    /*private SecretKey generateKey(){
        byte[] passwordDecoded = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(passwordDecoded);
    }*/
}
