package solutions.wo.it.components;

import solutions.wo.it.services.FinancialTransactionService;

public interface FinancialTransactionComponentFactory {

    FinancialTransactionService service = null;

    void refresh();

}
