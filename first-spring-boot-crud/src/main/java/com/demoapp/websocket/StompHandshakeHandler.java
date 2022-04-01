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
	public ActiveUserService getActiveUserService() {
		return new ActiveUserService();
	}
	
	@Bean
	public JWTUtils getTokenManager() {
		return new JWTUtils();
	}
	

	 // Custom class for storing principal
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // Generate principal with UUID as name
    	String userId = UUID.randomUUID().toString();
    	try {
        	ActiveUserService service = getActiveUserService();
        	String[] cookies = request.getHeaders().getFirst("Cookie").split(";");
        	System.out.println("cookie:"+request.getHeaders().getFirst("Cookie"));
        	String sessionCookie = cookies[1];
        	String sessionId = sessionCookie.substring(12, sessionCookie.length());
        	String userName = getTokenManager().getUsernameFromToken(sessionId);
        	service.addUser(userName, userId);
        	System.out.println("user name "+userName);
    	} catch(Exception e) {
    		System.out.println("Exception caught :"+e.getLocalizedMessage());
    	}
    	System.out.println("random-id"+userId);
    	
    	return new StompPrincipal(userId);    	
    }
}
