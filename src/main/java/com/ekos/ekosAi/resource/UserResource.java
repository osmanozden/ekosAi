package com.ekos.ekosAi.resource;

import java.util.List;

public class UserResource {
    public UserResource(String name, String surName, String email, List<RoleResource> roles) {
        this.firstName = name;
        this.lastName = surName;
        this.email = email;
        this.roles = roles;
    }

    public UserResource(String email, String pass) {
        this.email = email;
        this.password = pass;
    }
}
