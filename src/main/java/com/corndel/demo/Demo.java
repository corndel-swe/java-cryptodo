package com.corndel.demo;

import java.util.Base64;

import io.javalin.Javalin;
import io.javalin.security.BasicAuthCredentials;
import org.mindrot.jbcrypt.BCrypt;

public class Demo {
    public static void main(String[] args) {
        demoBasicAuthRetrieval();
        demoBasicAuthWithJavalinServer();
    }

    private static void demoBasicAuthRetrieval() {

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

    }

    public static void demoBasicAuthWithJavalinServer() {
        // Create an endpoint to test auth
        Javalin.create().get("/", (context) -> {
            try {
                // BasicAuthCredentials class encapsulates the basic authentication header.
                BasicAuthCredentials authCredentials = context.basicAuthCredentials();

                if (authCredentials == null) {
                    throw new RuntimeException("No Credentials provided");
                }

                // Retrieve basic authentication credentials using getter methods
                String username = authCredentials.getUsername();
                String password = authCredentials.getPassword();
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);

                // Hash the password to match
                String passwordToMatch = "openSesame";
                String hash = BCrypt.hashpw(passwordToMatch, BCrypt.gensalt());
                System.out.println("Password hash: " + hash);

                // Compare the given password against the hashed password
                // - The FE will have to send openSesame as the password when prompted to authenticate
                boolean result = BCrypt.checkpw(password, hash);
                System.out.println("Hashed plaintext password matches hash: " + result);

                if (result) {
                    context.result("Hello " + username);
                } else {
                    throw new RuntimeException("Incorrect Credentials provided");
                }

            } catch (RuntimeException e) {
                // Respond with a 401 status and prompt for basic authentication if credentials are invalid
                context.status(401).header("WWW-Authenticate", "Basic realm=\"cryptodo\"").result(e.getMessage());
            }

        }).start(5123);

    }
}
