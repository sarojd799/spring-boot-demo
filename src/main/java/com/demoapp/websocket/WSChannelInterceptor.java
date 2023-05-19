package com.demoapp.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

import com.demoapp.services.ActiveUserService;

public class WSChannelInterceptor implements ChannelInterceptor {

	@Override
	public Message<?> postReceive(Message<?> message, MessageChannel channel) {
		System.out.println("intercepted"+message);
	 	return message;
	}	
}
