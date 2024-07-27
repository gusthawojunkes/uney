package solutions.wo.it.components;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import solutions.wo.it.data.core.enums.FinancialInstitution;
import solutions.wo.it.database.entities.FinancialTransaction;
import solutions.wo.it.services.FinancialTransactionService;

public class FinancialTransactionsGeneralComponent extends VerticalLayout implements FinancialTransactionComponentFactory {

    Grid<FinancialTransaction> grid;
    TextField search;
    FinancialTransactionService service;

    public FinancialTransactionsGeneralComponent(FinancialTransactionService service) {
        this.service = service;
        grid = new Grid<>(FinancialTransaction.class, false);
        grid.addColumn(FinancialTransaction::getFormattedTransactionTime).setHeader("Data").setAutoWidth(true).setSortable(true);
        grid.addColumn(FinancialTransaction::getDescription).setHeader("Descrição").setAutoWidth(true);
        grid.addColumn(createTransactionValueComponentRenderer()).setHeader("Valor").setAutoWidth(true);
        grid.addColumn(createFinancialInstitutionComponentRenderer()).setHeader("Instituição Financeira").setAutoWidth(true).setSortable(true);
        grid.addColumn(FinancialTransaction::getTransactionTypeDescription).setHeader("Tipo de transação").setAutoWidth(true).setSortable(true);
        grid.setWidthFull();

        search = new TextField("Buscar");
        search.setPlaceholder("Ex: Ifood");
        search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        search.setValueChangeMode(ValueChangeMode.EAGER);

        add(search, grid);
        setSizeFull();

    }

    private static ComponentRenderer<Span, FinancialTransaction> createTransactionValueComponentRenderer() {
        return new ComponentRenderer<>(Span::new, (span, transaction) -> {
            boolean isPositive = transaction.getValue() > 0;
            String theme = String.format("badge %s", isPositive ? "success" : "error");
            span.getElement().setAttribute("theme", theme);
            span.setText("R$ " + transaction.getValue().toString());
        });
    }

    private static ComponentRenderer<Span, FinancialTransaction> createFinancialInstitutionComponentRenderer() {
        return new ComponentRenderer<>(Span::new, (span, transaction) -> {
            var institution = transaction.getInstitution();
            if (institution == FinancialInstitution.NUBANK) {
                span.getElement().setAttribute("theme", "badge light-purple");
            } else {
                span.getElement().setAttribute("theme", "badge warning");
            }
            span.setText(institution.getDescription());
        });
    }

    @Override
    public void refresh() {
        var transactions = this.service.getAll();

        transactions.sort((left, right) -> right.getTransactionTime().compareTo(left.getTransactionTime()));

        grid.setItems(transactions);
    }
}
