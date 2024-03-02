package solutions.wo.it.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import solutions.wo.it.database.entities.FinancialTransaction;
import solutions.wo.it.database.entities.User;

import java.util.List;

public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, String>, JpaSpecificationExecutor<FinancialTransaction> {
    List<FinancialTransaction> findByUser(User user);
}
