package com.demoapp.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration; 

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigurer implements  WebSocketMessageBrokerConfigurer {

	 @Override
	   public void configureMessageBroker(MessageBrokerRegistry config) {
	     
		 /*-topic that client subscribes to-*/ 
		 config.enableSimpleBroker("/topic","/queue");
	     
		 /*-prefix that client adds to path before sending message-*/
		 config.setApplicationDestinationPrefixes("/spring-security-mvc-socket");
	     
		 /*-configures a unique id for recipient that doesn't mix match with any other user-*/
		 config.setUserDestinationPrefix("/user");
		 
	   }
	 
	 
	   @Override
	   public void registerStompEndpoints(StompEndpointRegistry registry) {
		  /*-The path onto which servers waits for connection-*/
	      registry.addEndpoint("/secured/room")
	      .setHandshakeHandler(new StompHandshakeHandler())
	      .setAllowedOriginPatterns("*")
	      .withSockJS();
	      registry.addEndpoint("/secured/chat").setAllowedOriginPatterns("*").withSockJS();
	   }
	   
	   @Override
	   public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
	     registration.addDecoratorFactory(CustomWebsocketDecoratorHandler::new);
	   }
	   
	   
	   @Override
	    public void configureClientInboundChannel(ChannelRegistration registration) {
	        registration.interceptors(getChannelInterceptor());
	    }
	 
	   @Bean
	   public WSChannelInterceptor getChannelInterceptor() {
		   return new WSChannelInterceptor();
	   }
	   
	 
	 
}
