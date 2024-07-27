package solutions.wo.it.services;

import org.springframework.stereotype.Service;
import solutions.wo.it.data.core.exceptions.InvalidBeanException;
import solutions.wo.it.database.entities.Tag;
import solutions.wo.it.repositories.TagRepository;

import java.util.*;

@Service
public class TagService {

    private final TagRepository repository;
    private final UserService userService;

    public TagService(TagRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public List<Tag> getAll() {
        var user = this.userService.getLoggedUser();
        if (user == null) {
            this.repository.findAll();
        }
        return this.repository.findByUser(user);
    }

    public void save(Tag tag) throws InvalidBeanException {
        if (tag == null || tag.getName() == null) {
            throw new InvalidBeanException("O registro não pode ser vazio!");
        }

        tag.setUser(this.userService.getLoggedUser());
        this.repository.save(tag);
    }

    public void delete(Tag tag) {
        this.repository.delete(tag);
    }

    public Map<String, List<Tag>> createRelationshipMap() {
        Map<String, List<Tag>> relationshipMap = new HashMap<>();
        List<Tag> tags = this.getAll();
        for (Tag tag : tags) {
            Set<String> keywords = tag.getKeywordsSet();
            for (String keyword : keywords) {
                if (!relationshipMap.containsKey(keyword)) {
                    relationshipMap.put(keyword, new ArrayList<>());
                }
                relationshipMap.get(keyword).add(tag);
            }
        }
        return relationshipMap;
    }
}
