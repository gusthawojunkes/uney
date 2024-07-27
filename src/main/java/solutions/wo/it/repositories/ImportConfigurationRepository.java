package solutions.wo.it.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import solutions.wo.it.database.entities.ImportConfiguration;
import solutions.wo.it.database.entities.User;

import java.util.List;
import java.util.Optional;

public interface ImportConfigurationRepository extends JpaRepository<ImportConfiguration, String>, JpaSpecificationExecutor<ImportConfiguration> {
    Optional<ImportConfiguration> findByUser(User user);
}
