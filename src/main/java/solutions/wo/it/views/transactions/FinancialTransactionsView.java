package solutions.wo.it.views.transactions;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import solutions.wo.it.components.FinancialTransactionComponentFactory;
import solutions.wo.it.components.FinancialTransactionsByDateComponent;
import solutions.wo.it.components.FinancialTransactionsGeneralComponent;
import solutions.wo.it.database.entities.FinancialTransaction;
import solutions.wo.it.services.FinancialTransactionService;
import solutions.wo.it.views.MainLayout;

@PageTitle("Transações")
@Route(value = "transactions", layout = MainLayout.class)
@RouteAlias(value = "/transactions", layout = MainLayout.class)
@AnonymousAllowed
public class FinancialTransactionsView extends VerticalLayout {

    Grid<FinancialTransaction> grid;
    TextField search;

    public FinancialTransactionsView(FinancialTransactionService service) {
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        FinancialTransactionsGeneralComponent generalComponent = new FinancialTransactionsGeneralComponent(service);
        FinancialTransactionsByDateComponent byDateComponent = new FinancialTransactionsByDateComponent(service);

        tabSheet.add("Geral", generalComponent);
        tabSheet.add("Por data", byDateComponent);
        tabSheet.addSelectedChangeListener(onChange -> {
            String label = onChange.getSelectedTab().getLabel();
            FinancialTransactionComponentFactory tab = null;
            if (label.equals("Geral")) {
                tab = generalComponent;
            } else if (label.equals("Por data")) {
                tab = byDateComponent;
            }

            if (tab != null) {
                tab.refresh();
            }
        });
        add(tabSheet);
        setSizeFull();
    }

}
