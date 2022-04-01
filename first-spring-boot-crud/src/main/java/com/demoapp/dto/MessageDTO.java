package com.demoapp.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name="message_entity")
@Table(name="message")
@Embeddable
public class MessageDTO {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="message_id")
	private int messageId;
	
	@Column(name="chat_id")
	private int sessionId;
	
	@Column(name="message_content")
	private String messageContent;
	
	@Transient
	@JsonProperty
	private String sender;
	
	@Transient
	@JsonProperty
	private String receipent;
	
	@Column(name="senderId")
	private Integer senderId;
	
	@Column(name="receipentId")
	private Integer receipentId;
	
	@Column(name="sent_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp;
	
	@Column(name="seen")
	@Temporal(TemporalType.TIMESTAMP)
	private Date seen;
	
	public MessageDTO() {
		super();
	}

	public MessageDTO(String messageContent, String sender, String receipent, Date timeStamp) {
		super();
		this.messageContent = messageContent;
		this.sender = sender;
		this.receipent = receipent;
		this.timeStamp = timeStamp;
	}
	
	

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceipent() {
		return receipent;
	}

	public void setReceipent(String receipent) {
		this.receipent = receipent;
	}
	
	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public Integer getReceipentId() {
		return receipentId;
	}

	public void setReceipentId(Integer receipentId) {
		this.receipentId = receipentId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public Date getSeen() {
		return seen;
	}

	public void setSeen(Date seen) {
		this.seen = seen;
	}

	@Override
	public String toString() {
		return "MessageDTO [messageId=" + messageId + ", sessionId=" + sessionId + ", messageContent=" + messageContent
				+ ", sender=" + sender + ", receipent=" + receipent + ", senderId=" + senderId + ", receipentId="
				+ receipentId + ", timeStamp=" + timeStamp + "]";
	}


	
	
}
