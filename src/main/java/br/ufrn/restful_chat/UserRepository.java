package br.ufrn.restful_chat;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	List<User> findByName(@Param("name") String name);

}
