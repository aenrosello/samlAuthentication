package com.example.saml.authentication.samlAuthentication.configuration;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AuthenticationProviderImpl implements AuthenticationProvider {
    private Logger log = Logger.getLogger(AuthenticationProviderImpl.class);
    public static final String JWT_SECRET = "yeWAgVDfb$!MFn@MCJVN7uqkznHbDLR#";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "Authentication is missing");
        Assert.isInstanceOf(AuthenticationToken.class, authentication, "This method only accepts AuthenticationToken");
        String jwtToken = authentication.getName();

        if (authentication.getPrincipal() == null || jwtToken == null || jwtToken.isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Authentication token is missing");
        }

        try {
            SignedJWT signedJWT = SignedJWT.parse(jwtToken);
            boolean isVerified = signedJWT.verify(new MACVerifier(JWT_SECRET.getBytes()));

            if (!isVerified) {
                throw new BadCredentialsException("Invalid token signature");
            }

            //is token expired ?
            LocalDateTime expirationTime = LocalDateTime.ofInstant(
                    signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(), ZoneId.systemDefault());

            if (LocalDateTime.now(ZoneId.systemDefault()).isAfter(expirationTime)) {
                throw new CredentialsExpiredException("Token expired");
            }

            return new AuthenticationToken(signedJWT, null, null);

        } catch (ParseException e) {
            throw new InternalAuthenticationServiceException("Unreadable token");
        } catch (JOSEException e) {
            throw new InternalAuthenticationServiceException("Unreadable signature");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
