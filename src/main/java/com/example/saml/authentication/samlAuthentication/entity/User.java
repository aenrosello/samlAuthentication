package com.example.saml.authentication.samlAuthentication.entity;

public class User {
    private static final long serialVersionUID = 5158387267043607956L;

    private Integer id;
    private String name;
    private String email;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
