//package com.example.userservice.security.service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//@Service
//public class TokenService {
//
////    private final SecretKey jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//String sharedSecretKey = "tokenKey";
//
//    // Generate a secure hash of the key using SHA-256
//    String hashedKey = sha256Hash(sharedSecretKey);
//
//    // Convert the hashed key to a byte array
//    byte[] keyBytes = hashedKey.getBytes();
//
//    private final SecretKey jwtSecretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
//
////    private final SecretKey jwtSecretKey = Keys.hmacShaKeyFor(sharedSecretKey.getBytes());
//
//    public String generate(Claims claims) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .signWith(jwtSecretKey)
//                .compact();
//    }
//    public Claims parseToken(String jwt) {
//        Claims claims;
//        try {
//            claims = Jwts.parser()
//                    .setSigningKey(jwtSecretKey)
//                    .parseClaimsJws(jwt)
//                    .getBody();
//        } catch (Exception e) {
//            return null;
//        }
//        return claims;
//    }
//
//    private static String sha256Hash(String input) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = digest.digest(input.getBytes());
//            StringBuilder hexString = new StringBuilder();
//
//            for (byte b : hash) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) hexString.append('0');
//                hexString.append(hex);
//            }
//
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}

package com.example.userservice.security.service;

import com.example.userservice.messagebroker.MessageSender;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class TokenService {

    private final SecretKey jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final MessageSender messageSender;

    @Autowired
    public TokenService(MessageSender messageSender) {
        // Your original shared secret key
        this.messageSender = messageSender;


        byte[] keyBytes = jwtSecretKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(keyBytes);
        System.out.println("Encoded Key: " + encodedKey);

//        SecretKey reconstructedKey = reconstructSecretKey(encodedKey);

//        System.out.println("Keys are equal: " + jwtSecretKey.equals(reconstructedKey));

        this.messageSender.sendMessage("notify-service/setkey",encodedKey);
        this.messageSender.sendMessage("schedule-service/setkey",encodedKey);
    }

    public String generate(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(jwtSecretKey)
                .compact();
    }

    public Claims parseToken(String jwt) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
        return claims;
    }


    private static SecretKey reconstructSecretKey(String encodedKey) {
        // Dekodirajte base64 string u bajtni niz
        byte[] keyBytes = Base64.getDecoder().decode(encodedKey);

        // Ponovno konstruirajte SecretKey iz bajtnog niza
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
}
