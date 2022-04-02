package com.demoapp.websocket;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.demoapp.jwtutils.JWTUtils;
import com.demoapp.services.ActiveUserService;

public class StompHandshakeHandler extends DefaultHandshakeHandler  {
	
	
	@Bean
	public JWTUtils getTokenManager() {
		return new JWTUtils();
	}
	

	 // Custom class for storing principal
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // Generate principal with UUID as name
    	return new StompPrincipal(UUID.randomUUID().toString());    	
    }
}
