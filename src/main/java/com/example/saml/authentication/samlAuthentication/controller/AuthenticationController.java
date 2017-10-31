package com.example.saml.authentication.samlAuthentication.controller;

import com.example.saml.authentication.samlAuthentication.configuration.jwt.AuthenticationProviderImpl;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    private Logger log = Logger.getLogger(AuthenticationController.class);

    @GetMapping
    public void login(@RequestParam(required = false) String relayState,
                      HttpServletResponse response,
                      HttpServletRequest request) throws
            JOSEException, IOException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else {
            log.info("Authenticating new user");
            log.info("Relay State = " + relayState);
            //prepare signer
            JWSSigner signer = new MACSigner(AuthenticationProviderImpl.JWT_SECRET);
            //build claims - it contains extra information that is going to be included into the token
            LocalDateTime expiration = LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(15);
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .expirationTime(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
                    .issuer("samlAuthenticationAPI")
                    .subject("<userId>")
                    .claim("anotherProperty", "value")
                    .build();
            //create token
            SignedJWT token = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
            //sign token with signer
            token.sign(signer);

            log.info("Sending token " + token.serialize());
            //todo redirect to relayState instead of just simple send the response
            response.setStatus(HttpStatus.OK.value());
            StringBuilder target = new StringBuilder();
            target.append("?token=");
            target.append(token.serialize());
            if (relayState != null && !relayState.isEmpty()) {
                target.insert(0, relayState);
            } else {
                target.insert(0, request.getRequestURL().toString());
            }
            response.sendRedirect(target.toString());
        }
    }
}

