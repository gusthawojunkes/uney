package solutions.wo.it.views.importation.configuration;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import solutions.wo.it.database.entities.ImportConfiguration;
import solutions.wo.it.services.imports.configuration.ImportConfigurationService;

public class ImportConfigurationFlagsView extends VerticalLayout {

    private final ImportConfigurationService service;
    private final Binder<ImportConfiguration> binder = new Binder<>(ImportConfiguration.class);

    public ImportConfigurationFlagsView(ImportConfigurationService service) {
        this.service = service;
        Checkbox ignoreInvoicePaymentRecords = new Checkbox("Ignorar registros de pagamento de faturas na hora da importção");
        binder.forField(ignoreInvoicePaymentRecords).bind(ImportConfiguration::isIgnoreInvoicePaymentRecords, ImportConfiguration::setIgnoreInvoicePaymentRecords);

        add(ignoreInvoicePaymentRecords);

        Button saveButton = new Button("Salvar", event -> save());
        add(saveButton);

        ImportConfiguration bean = this.service.getByLoggedUser().orElseGet(ImportConfiguration::new);
        this.binder.setBean(bean);
        this.binder.bindInstanceFields(this);
    }

    private void save() {
        try {
            ImportConfiguration importConfiguration = binder.getBean();
            service.save(importConfiguration);
            Notification.show("Registro cadastrado com sucesso!");
        } catch (Exception exception) {
            Notification.show("Ocorreu um erro ao salvar o registro!");
        }
    }

}
