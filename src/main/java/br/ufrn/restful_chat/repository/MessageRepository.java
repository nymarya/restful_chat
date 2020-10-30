package br.ufrn.restful_chat.repository;

import br.ufrn.restful_chat.model.Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
	
	List<Message> findFirst10ByOrderByDataDesc();
}
