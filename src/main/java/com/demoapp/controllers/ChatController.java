package com.demoapp.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.demoapp.dto.MessageDTO;
import com.demoapp.services.ActiveUserService;
import com.demoapp.services.ChatService;

@RestController
public class ChatController {
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	
	@Autowired
	private ChatService chatService;
	
	
	@MessageMapping("/secured/room")
	public void sendToUser(SimpMessageHeaderAccessor simp, @Payload MessageDTO message,Principal user,  @Header("simpSessionId") String sessionId) throws Exception {
		
		System.out.println("Intercepted message"+message);
		chatService.saveMessage(message);
		String receipent = ActiveUserService.getPrincipal(message.getReceipent());
		System.out.println("receipent"+receipent);
		System.out.println("activeUsers "+ActiveUserService.getAllRegisteredUsers());
		
		if(receipent != null) {
			simpMessagingTemplate.convertAndSendToUser(receipent, "/queue/message", message); 
		}
		
	}
	
	@MessageMapping("/registerForChat")
	public void registerForChat(SimpMessageHeaderAccessor simp, @Payload MessageDTO message,Principal user,  @Header("simpSessionId") String sessionId) throws Exception {
		
		System.out.println("Intercepted register"+message);
		String sender = message.getSender();
		System.out.println("sender"+sender);
		if(sender != null) {
			ActiveUserService.addUser(sender, simp.getUser().getName());
			simpMessagingTemplate.convertAndSendToUser(simp.getUser().getName(), "/queue/message", "Registered"); 
		}
		
	}

	
	@MessageMapping("/chatEvent/type")
	public void onUserKeyup(SimpMessageHeaderAccessor simp, @Payload MessageDTO message,Principal user,  @Header("simpSessionId") String sessionId) throws Exception {
        String receipent = ActiveUserService.getPrincipal(message.getReceipent());
        if(receipent != null) {
        	simpMessagingTemplate.convertAndSendToUser(receipent, "queue/keyup", message); 
        }
	}
	
	@MessageMapping("/chatEvent/blur")
	public void onUserBlur(SimpMessageHeaderAccessor simp, @Payload MessageDTO message,Principal user,  @Header("simpSessionId") String sessionId) throws Exception {
		String receipent = ActiveUserService.getPrincipal(message.getReceipent());
        if(receipent != null) {
        	simpMessagingTemplate.convertAndSendToUser(ActiveUserService.getPrincipal(message.getReceipent()), "queue/blur", message);
        }
	}
	
	
	
	@SubscribeMapping("/spring-security-mvc-socket")
	   public String sendOneTimeMessage() {
	       return "server one-time message via the application";
	}
	
	
	@GetMapping(path= "/api/chat/{senderId}/{receiverId}/getAllMessages",  produces= MediaType.APPLICATION_JSON_VALUE)
	public Set<MessageDTO> getUserChats(
			@PathVariable("senderId") Integer senderId,
			@PathVariable("receiverId") Integer receiverId ) {
		return chatService.getChatMessages(senderId, receiverId);
	}
}
