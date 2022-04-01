package com.demoapp.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="chats")
@Table(name="chats_details")
public class ChatsDTO {
	
	
	/**
	 * MessageDTO will contain all the messages but the conversion needs to be mapped somewhere where this comes into picture
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int chatId;
   
//	@ElementCollection
//	private Set<MessageDTO> chats = new LinkedHashSet<MessageDTO>();
//
//	public Set<MessageDTO> getChats() {
//		return chats;
//	}
//
//	public void setChats(Set<MessageDTO> chats) {
//		this.chats =   chats;
//	}

	
	
	
}
