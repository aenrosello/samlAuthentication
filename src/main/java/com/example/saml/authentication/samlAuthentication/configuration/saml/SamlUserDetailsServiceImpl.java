package com.example.saml.authentication.samlAuthentication.configuration.saml;

import com.example.saml.authentication.samlAuthentication.entity.User;
import com.example.saml.authentication.samlAuthentication.repository.impl.UserRepositoryImpl;
import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

/**
 * @author aenrosello
 */
public class SamlUserDetailsServiceImpl implements SAMLUserDetailsService {
    private Logger log = Logger.getLogger(SamlUserDetailsServiceImpl.class);

    private UserRepositoryImpl userRepository;

    public SamlUserDetailsServiceImpl() {
        userRepository = new UserRepositoryImpl();
    }

    @Override
    public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
        log.info("get user information");
        // NOTE: load necessary information from your user
        String username = credential.getAttributeAsString("UserID");
        String email = credential.getAttributeAsString("EmailAddress");
        String firstName = credential.getAttributeAsString("FirstName");
        String lastName = credential.getAttributeAsString("LastName");
        log.info("username= " + username);
        log.info("email= " + email);
        log.info(String.format("User name = %s %s", firstName, lastName));

        User user = userRepository.findAll().get(0);
        SamlUserDetails samlUser = new SamlUserDetails();
        samlUser.setId(user.getId());
        samlUser.setName(user.getEmail());
        samlUser.setEmail(user.getEmail());
        return samlUser;
    }
}