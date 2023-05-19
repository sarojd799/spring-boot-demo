package com.demoapp.websocket;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

import com.demoapp.services.ActiveUserService;


public class CustomWebsocketDecoratorHandler extends WebSocketHandlerDecorator {
	


  
  public CustomWebsocketDecoratorHandler(WebSocketHandler delegate) {
    super(delegate);
  }
  
 

  @Override
  public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message)
    throws Exception {
	  String payload = null;
    if (message instanceof TextMessage) {
      TextMessage msg = (TextMessage) message;
      payload = msg.getPayload();
    }
    
    Principal p = session.getPrincipal();
    if(p !=null && payload != null && (payload.contains("DISCONNECT") || payload.contains("UNSUBSCRIBE"))) {
    	System.out.println("principal in decorator handler:"+p.getName());
    	ActiveUserService.removeUserByPrincipal(p.getName());
    } else {
    	System.out.println("principal is null");	
    }
    
    super.handleMessage(session, message);
  }
}
