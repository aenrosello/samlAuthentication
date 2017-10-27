package com.example.saml.authentication.samlAuthentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/")
public class helloController {
    @GetMapping
    public String sayHello(){
        return "hello";
    }
}
