package com.techprimers.springbootwebsocketexample.config;

import java.security.Principal;
import java.util.Map;
import java.util.Random;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/websocket-example").setAllowedOrigins("*").setHandshakeHandler(new RandomUserNameHandshakeHandler())
                .withSockJS();
       
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }
    
    
    private class RandomUserNameHandshakeHandler extends DefaultHandshakeHandler{

        @Override
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String,Object> attributes){
//        	Set<String> s=attributes.keySet();
//        	System.out.println(request.getHeaders().get("name"));
//        	for (String string : s) {
//				System.out.println(attributes.get(string));
//			}
            // Some Code that would give me the username set in Stomp Header
            // For now i am randomly generating a username and setting in principal.
            String username = "user"+new Random().nextInt(100);
            return new UsernamePasswordAuthenticationToken(username,null);

        }
    }
    
}
