package com.example.saml.authentication.samlAuthentication.repository;

import java.util.List;

public interface UserRepository<ID, Entity> {
    List<Entity> findAll();

    Entity findOne(ID id);
}
