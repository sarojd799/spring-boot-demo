package com.demoapp.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demoapp.dto.ChatsDTO;
import com.demoapp.dto.MessageDTO;
import com.demoapp.repos.ChatMessageRepository;
import com.demoapp.repos.ChatRepository;

@Service
public class ChatService {

	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private ChatMessageRepository chatMessageRepository;
	
	public void saveChat(ChatsDTO c) {
		if(c != null) {
			chatRepository.save(c);
		}
	}
	
	public void saveMessage(MessageDTO m) {
		if(m != null) {
		     chatMessageRepository.save(m);	
		}
	}
	
	public Set<MessageDTO> getChatMessages(Integer senderId, Integer receiverId) {
		return chatMessageRepository.findBySenderIdAndReceipentId(senderId, receiverId);
	}
}
