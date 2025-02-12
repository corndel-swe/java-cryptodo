package com.corndel.utils;

import com.corndel.cryptodo.models.User;
import com.corndel.cryptodo.repositories.UserRepository;

import java.sql.SQLException;
import java.util.List;

public class PasswordHasher {

    public static void main(String[] args) {

        try {
            List<User> users = UserRepository.findAll();

            for (User user : users) {

                System.out.println(user);


            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
