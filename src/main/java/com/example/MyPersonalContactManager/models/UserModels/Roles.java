package com.example.MyPersonalContactManager.models.UserModels;

public enum Roles {
    USER(false),
    ADMIN(true);

    private final boolean role;

    Roles(boolean role) {
        this.role = role;
    }

    public boolean getRole() {
        return role;
    }
}
