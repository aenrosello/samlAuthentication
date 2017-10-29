package com.example.saml.authentication.samlAuthentication.controller;

import com.example.saml.authentication.samlAuthentication.configuration.AuthenticationProviderImpl;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    private Logger log = Logger.getLogger(AuthenticationController.class);

    @GetMapping
    public ResponseEntity<String> login() throws JOSEException {
        log.info("Authenticating new user");
        LocalDateTime expiration = LocalDateTime.now(ZoneId.systemDefault());
        expiration = expiration.plusMinutes(15);
        //build claims
        JWTClaimsSet.Builder jwtClaimsSetBuilder = new JWTClaimsSet.Builder();
        jwtClaimsSetBuilder.expirationTime(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()));
        jwtClaimsSetBuilder.claim("APP", "SAMPLE");

        //signature
        SignedJWT token = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtClaimsSetBuilder.build());
        token.sign(new MACSigner(AuthenticationProviderImpl.JWT_SECRET));
        log.info("Sending token " + token.serialize());
        return new ResponseEntity<>(token.serialize(), HttpStatus.OK);
    }
}

