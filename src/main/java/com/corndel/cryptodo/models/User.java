package com.corndel.cryptodo.models;


public record User(String username, String password, String email, int id, String createdAt) {
    public User(String username, String password, String email) {
        this(username, password, email, -1, null);
    }
}

