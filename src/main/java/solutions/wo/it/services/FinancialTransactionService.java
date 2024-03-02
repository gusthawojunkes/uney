package solutions.wo.it.services;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import solutions.wo.it.data.core.enums.FinancialInstitution;
import solutions.wo.it.database.entities.FinancialTransaction;
import solutions.wo.it.repositories.FinancialTransactionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinancialTransactionService {

    private final FinancialTransactionRepository repository;
    private final UserService userService;

    public FinancialTransactionService(FinancialTransactionRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
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
}
