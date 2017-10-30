package com.example.saml.authentication.samlAuthentication.configuration;

import com.example.saml.authentication.samlAuthentication.configuration.saml.SamlSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by alberto.navarro on 10/30/2017.
 */
@Configuration
@Import(SamlSecurityConfig.class)
public class SecurityConfigSAML {
}
