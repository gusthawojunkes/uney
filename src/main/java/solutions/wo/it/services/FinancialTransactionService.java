package solutions.wo.it.services;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import solutions.wo.it.data.core.enums.FinancialInstitution;
import solutions.wo.it.database.entities.FinancialTransaction;
import solutions.wo.it.database.entities.Tag;
import solutions.wo.it.database.entities.User;
import solutions.wo.it.repositories.FinancialTransactionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancialTransactionService {

    private final FinancialTransactionRepository repository;
    private final UserService userService;
    private final TagService tagService;

    public FinancialTransactionService(FinancialTransactionRepository repository, UserService userService, TagService tagService) {
        this.repository = repository;
        this.userService = userService;
        this.tagService = tagService;
    }

    public void bulkInsert(List<FinancialTransaction> transactions) {
        if (CollectionUtils.isEmpty(transactions)) return;
        repository.saveAll(transactions);
    }

    public List<FinancialTransaction> getAll() {
        var user = this.userService.getLoggedUser();
        if (user == null) {
            this.repository.findAll();
        }
        return this.repository.findByUser(user);
    }

    public Map<FinancialInstitution, List<String>> findAlreadyExistentTransactionsByFinancialInstitution() {
        List<FinancialTransaction> transactions = this.getAll();
        if (CollectionUtils.isEmpty(transactions)) return new HashMap<>();
        Map<FinancialInstitution, List<String>> transactionsByFinancialInstitution = new HashMap<>();
        for (FinancialTransaction transaction : transactions) {
            FinancialInstitution institution = transaction.getInstitution();
            if (!transactionsByFinancialInstitution.containsKey(institution)) {
                transactionsByFinancialInstitution.put(institution, new ArrayList<>());
            }
            transactionsByFinancialInstitution.get(institution).add(transaction.getInstitutionUuid());
        }
        return transactionsByFinancialInstitution;
    }

    public void updateTagsFromRegisters() {
        var transactions = this.getAll();
        this.defineTags(transactions);
        this.bulkInsert(transactions);
    }

    public void defineUser(List<FinancialTransaction> transactions) {
        if (CollectionUtils.isEmpty(transactions)) return;
        User user = userService.getLoggedUser();
        transactions.forEach(transaction -> transaction.setUser(user));
    }

    public void defineTags(List<FinancialTransaction> transactions) {
        if (CollectionUtils.isEmpty(transactions)) return;
        Map<String, List<Tag>> tagsByKeyword = tagService.createRelationshipMap();
        if (MapUtils.isEmpty(tagsByKeyword)) return;
        for (FinancialTransaction transaction : transactions) {
            String keyword = transaction.getDescription();
            if (tagsByKeyword.containsKey(keyword)) {
                var tags = tagsByKeyword.get(keyword);
                transaction.setTags(tags);
            }
        }
    }
}
