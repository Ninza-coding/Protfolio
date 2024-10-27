package com.portfolio.service;

import java.util.List;

import com.portfolio.model.MessageForm;


public interface MessageFormService {

	public MessageForm saveMesagaeFromService(MessageForm messageForm);
	
	public List<MessageForm> readAllContactsServices();
	
	public void deleteMessageService(int id);
}
