package solutions.wo.it.views.importation;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.lineawesome.LineAwesomeIcon;
import solutions.wo.it.data.core.enums.FinancialInstitution;
import solutions.wo.it.data.core.exceptions.FinancialInstitutionNotSupportedException;
import solutions.wo.it.services.FileService;
import solutions.wo.it.services.FinancialTransactionService;
import solutions.wo.it.views.MainLayout;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@PageTitle("Importação de arquivos")
@Route(value = "import", layout = MainLayout.class)
@RouteAlias(value = "/import", layout = MainLayout.class)
@AnonymousAllowed
public class ImportView extends VerticalLayout {

    private FileBuffer buffer = new FileBuffer();
    private Upload upload = new Upload(buffer);
    private Button startImportButton;

    private ComboBox<FinancialInstitution> comboboxFinancialInstitution;

    private FileService financialFileService;


    public ImportView(
        FinancialTransactionService financialTransactionService
    ) {
        financialFileService = new FileService(financialTransactionService);

        HorizontalLayout layoutImportFile = new HorizontalLayout();

        upload.setUploadButton(new Button("Escolher arquivos"));
        upload.setAcceptedFileTypes(".ofx");
        upload.setMaxFiles(1);

        comboboxFinancialInstitution = new ComboBox<>("Instituição financeira");
        comboboxFinancialInstitution.setItems(Arrays.asList(FinancialInstitution.NUBANK, FinancialInstitution.ITAU));
        comboboxFinancialInstitution.setItemLabelGenerator(FinancialInstitution::getDescription);
        comboboxFinancialInstitution.setRequiredIndicatorVisible(true);

        layoutImportFile.add(upload, comboboxFinancialInstitution);
        add(layoutImportFile);

        startImportButton = new Button("Importar");
        startImportButton.addClickListener(this::importFile);
        startImportButton.setIconAfterText(true);
        startImportButton.setIcon(LineAwesomeIcon.CHECK_CIRCLE_SOLID.create());
        add(startImportButton);
    }

    void importFile(ClickEvent<Button> event) {
        File file = null;
        if (buffer == null || buffer.getFileData() == null) {
            Notification.show("Escolha um arquivo para realizar a importação");
            return;
        } else {
            file = buffer.getFileData().getFile();
            if (file == null) {
                Notification.show("Escolha um arquivo para realizar a importação");
                return;
            }
        }

        FinancialInstitution institution = comboboxFinancialInstitution.getValue();
        if (institution == null) {
            Notification.show("Selecione uma instituição financeira para realizar a importação");
            return;
        }
        try {
            financialFileService.importOFX(file, institution);
            upload.clearFileList();
            Notification.show("Arquivo importado com sucesso");
        } catch (IOException | FinancialInstitutionNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
