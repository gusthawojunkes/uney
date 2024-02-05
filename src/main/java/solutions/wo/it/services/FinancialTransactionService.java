package solutions.wo.it.services;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import solutions.wo.it.database.entities.FinancialTransaction;
import solutions.wo.it.repositories.FinancialTransactionRepository;

import java.util.List;

@Service
public class FinancialTransactionService {

    private FinancialTransactionRepository repository;

    public FinancialTransactionService(FinancialTransactionRepository repository) {
        this.repository = repository;
    }

    public void bulkInsert(List<FinancialTransaction> transactions) {
        if (CollectionUtils.isEmpty(transactions)) return;
        repository.saveAll(transactions);
    }
}
