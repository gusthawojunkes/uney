package solutions.wo.it.services;

import org.springframework.stereotype.Service;
import solutions.wo.it.database.entities.User;
import solutions.wo.it.repositories.UserRepository;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void save(User user) {
        repository.save(user);
    }

    public User getLoggedUser() {
        return Objects.requireNonNull(this.repository.findById("1").orElse(null));
    }

}
