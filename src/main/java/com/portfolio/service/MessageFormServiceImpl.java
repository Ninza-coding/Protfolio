package com.portfolio.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.dao.MessageFormDao;
import com.portfolio.model.MessageForm;

@Service
public class MessageFormServiceImpl implements MessageFormService{

	
	private MessageFormDao messageFormDao;
	
	@Autowired
	public void setMessageFormDao(MessageFormDao messageFormDao) {
		this.messageFormDao = messageFormDao;
	}

	@Override
	public MessageForm saveMesagaeFromService(MessageForm messageForm) {
		// TODO Auto-generated method stub
		return messageFormDao.save(messageForm);
	}


	@Override
	public List<MessageForm> readAllContactsServices() {
		// TODO Auto-generated method stub
		return messageFormDao.findAll();
	}

	@Override
	public void deleteMessageService(int id) {
		messageFormDao.deleteById(id);
		
	}
}
