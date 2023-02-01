package com.qnocks.authorizationserver.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.UUID;

@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * Need separate the security filter chain from authorization server from the default security filter chain
     * Maybe in future versions of the authorization server
     *
     * In authorizationServerSecurityFilterChain indicate that whenever I have an exception I redirect to the login page
     * In defaultSecurityFilterChain protect all the endpoints abd use default login form used by Spring
     *
     * Same information as in client-server
     * Client secret is encoded using previously configured password encoder
     * Then apply authentication which will be used to communicate between client-server and authentication-server
     */

    @SneakyThrows
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(
                new LoginUrlAuthenticationEntryPoint("/login")));

        return http.build();
    }

    @SneakyThrows
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
        http.authorizeRequests(authorize -> authorize.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        var client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messages-client")
                .clientSecret("$2a$12$/xdT4GByOtITcHq7SGtV.ORBMc.Vh3gu3nWz1IDuKxCiBBmG9aiLG")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://client-server:8083/login/oauth2/code/messages-client-oidc")
                .redirectUri("http://client-server:8083/authorized")
                .scope(OidcScopes.OPENID)
                .scope("message.read")
                .scope("message.write")
                .build();

        return new InMemoryRegisteredClientRepository(client);
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                .issuer("http://authorization-server:8081")
                .build();
    }
}
