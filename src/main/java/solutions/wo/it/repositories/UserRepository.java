package solutions.wo.it.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import solutions.wo.it.database.entities.User;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

}
