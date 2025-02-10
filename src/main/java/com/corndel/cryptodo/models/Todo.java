package com.corndel.cryptodo.models;


public record Todo(int id, int userId, String description, boolean completed) {
    public Todo(int userId, String description) {
        this(-1, userId, description, false);
    }
}

