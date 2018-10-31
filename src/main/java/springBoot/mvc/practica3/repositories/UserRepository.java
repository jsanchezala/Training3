package springBoot.mvc.practica3.repositories;

import org.springframework.data.repository.CrudRepository;
import springBoot.mvc.practica3.model.User;

public interface UserRepository extends CrudRepository<User, String> {

}
