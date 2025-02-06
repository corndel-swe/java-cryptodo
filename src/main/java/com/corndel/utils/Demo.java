package com.corndel.utils;

import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

public class Demo {
    public static void main(String[] args) {
        try {
            // Encode credentials into a basic auth header
            String credential = "imauser:supersecret";
            String header = "Basic " + Base64.getEncoder().encodeToString(credential.getBytes());
            System.out.println("Basic auth header: " + header);

            // Decode the basic auth header
            String base64 = header.split(" ")[1];
            String decoded = new String(Base64.getDecoder().decode(base64));
            String[] parts = decoded.split(":");
            String username = parts[0];
            String password = parts[1];
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            // Hash the password
            String hash = BCrypt.hashpw(password, BCrypt.gensalt());
            System.out.println("Password hash: " + hash);

            // Compare passwords
            boolean result = BCrypt.checkpw(password, hash);
            System.out.println("Hashed plaintext password matches hash: " + result);

            // Compare wrong password
            boolean result2 = BCrypt.checkpw("wrong", hash);
            System.out.println("Hashed incorrect password matches hash: " + result2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
