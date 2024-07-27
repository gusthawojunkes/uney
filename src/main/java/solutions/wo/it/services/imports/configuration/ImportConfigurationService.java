package solutions.wo.it.services.imports.configuration;

import org.springframework.stereotype.Service;
import solutions.wo.it.data.core.exceptions.InvalidBeanException;
import solutions.wo.it.database.entities.ImportConfiguration;
import solutions.wo.it.repositories.ImportConfigurationRepository;
import solutions.wo.it.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class ImportConfigurationService {

    private final ImportConfigurationRepository repository;
    private final UserService userService;

    public ImportConfigurationService(ImportConfigurationRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public Optional<ImportConfiguration> getByLoggedUser() {
        var user = this.userService.getLoggedUser();
        return this.repository.findByUser(user);
    }

    public void save(ImportConfiguration config) throws InvalidBeanException {
        if (config == null) {
            throw new InvalidBeanException("O registro não pode ser vazio!");
        }

        config.setUser(this.userService.getLoggedUser());
        this.repository.save(config);
    }
}
