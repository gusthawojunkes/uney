package solutions.wo.it.components;

import solutions.wo.it.services.FinancialTransactionService;

public class FinancialTransactionsByDateComponent extends FinancialTransactionsGeneralComponent implements FinancialTransactionComponentFactory{


    public FinancialTransactionsByDateComponent(FinancialTransactionService service) {
        super(service);
    }

    @Override
    public void refresh() {
        super.refresh();
    }
}
