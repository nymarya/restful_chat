package br.ufrn.restful_chat.repository;

import br.ufrn.restful_chat.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
