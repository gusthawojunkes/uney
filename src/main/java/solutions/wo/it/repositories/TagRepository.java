package solutions.wo.it.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import solutions.wo.it.database.entities.Tag;
import solutions.wo.it.database.entities.User;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, String>, JpaSpecificationExecutor<Tag> {
    List<Tag> findByUser(User user);
}
