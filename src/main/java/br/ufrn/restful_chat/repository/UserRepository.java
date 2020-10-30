package br.ufrn.restful_chat.repository;

import br.ufrn.restful_chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);

}