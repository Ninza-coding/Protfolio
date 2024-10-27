package com.portfolio.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.model.MessageForm;

@Repository
public interface MessageFormDao extends JpaRepository<MessageForm, Integer> {

	@Override
	public <S extends MessageForm> S save(S entity);
	//

	@Override
	public List<MessageForm> findAll();

	@Override
	public void deleteById(Integer id);
}
