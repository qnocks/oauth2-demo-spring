package com.qnocks.clientserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequiredArgsConstructor
public class MessageController {

    /**
     * Indicate that the requested endpoint will require the OAuth2 client to be correctly authorized
     * This OAuth2 client contains the information about the scope and the authorization server
     * The current backend client will request the resource-server for the messages
     * In the request I must include the OAuth2 attributes
     * Finally return to the end user the information from resource-server
     */

    private final WebClient webClient;

    @GetMapping("/messages")
    public String getMessages(@RegisteredOAuth2AuthorizedClient("messages-client-authorization-code")
                              OAuth2AuthorizedClient authorizedClient) {
        return webClient.get()
                .uri("http://resource-server:8082/messages")
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
