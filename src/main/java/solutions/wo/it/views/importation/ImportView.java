package solutions.wo.it.views.importation;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
import solutions.wo.it.components.ConfirmDialog;
import solutions.wo.it.data.core.enums.FinancialInstitution;
import solutions.wo.it.data.core.exceptions.FinancialInstitutionNotSupportedException;
import solutions.wo.it.services.FileService;
import solutions.wo.it.services.FinancialTransactionService;
import solutions.wo.it.services.TagService;
import solutions.wo.it.services.UserService;
import solutions.wo.it.views.MainLayout;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@PageTitle("Importação de arquivos")
@Route(value = "import", layout = MainLayout.class)
@RouteAlias(value = "/import", layout = MainLayout.class)
@AnonymousAllowed
public class ImportView extends VerticalLayout {

    private final FileBuffer buffer = new FileBuffer();
    private final Upload upload = new Upload(buffer);
    private final ComboBox<FinancialInstitution> comboboxFinancialInstitution;
    private final FileService fileService;
    private final FinancialTransactionService financialTransactionService;
    private ConfirmDialog updateTagsConfirmDialog = new ConfirmDialog();


    public ImportView(FileService fileService, FinancialTransactionService financialTransactionService) {
        this.fileService = fileService;
        this.financialTransactionService = financialTransactionService;

        HorizontalLayout horizontalLayoutImport = new HorizontalLayout();
        horizontalLayoutImport.setSizeFull();
        VerticalLayout fileImportLayout = new VerticalLayout();
        fileImportLayout.setMargin(true);

        HorizontalLayout paramsLayout = new HorizontalLayout();

        upload.setUploadButton(new Button("Escolher arquivos"));
        upload.setAcceptedFileTypes(".ofx");
        upload.setMaxFiles(1);
        paramsLayout.add(upload);

        comboboxFinancialInstitution = new ComboBox<>("Instituição financeira");
        comboboxFinancialInstitution.setItems(Arrays.asList(FinancialInstitution.NUBANK, FinancialInstitution.ITAU));
        comboboxFinancialInstitution.setItemLabelGenerator(FinancialInstitution::getDescription);
        comboboxFinancialInstitution.setRequiredIndicatorVisible(true);
        paramsLayout.add(comboboxFinancialInstitution);

        Button startImportButton = new Button("Importar");
        startImportButton.addClickListener(this::importFile);
        startImportButton.setIconAfterText(true);
        startImportButton.setIcon(LineAwesomeIcon.CHECK_CIRCLE_SOLID.create());

        fileImportLayout.add(paramsLayout, startImportButton);
        fileImportLayout.setAlignSelf(Alignment.START, startImportButton);

        horizontalLayoutImport.add(fileImportLayout);

        VerticalLayout updateButtonsLayout = new VerticalLayout();
        updateButtonsLayout.setSpacing(true);

        Button updateTagsRelationshipButton = new Button("Atualizar tags nos registros bancários");
        updateTagsRelationshipButton.setIcon(VaadinIcon.REFRESH.create());
        updateTagsRelationshipButton.addClickListener(event -> updateTagsConfirmDialog.open());
        updateButtonsLayout.add(updateTagsRelationshipButton);
        updateButtonsLayout.setAlignSelf(Alignment.END, updateTagsRelationshipButton);

        horizontalLayoutImport.add(updateButtonsLayout);

        updateTagsConfirmDialog = new ConfirmDialog(
            "Confirmação",
            "Deseja realmente atualizar todas as tags nos registros financeiros?",
            this::updateAllTags,
            null
        );

        add(horizontalLayoutImport, updateTagsConfirmDialog);
    }

    void updateAllTags(ClickEvent<Button> event) {
        try {
            this.financialTransactionService.updateTagsFromRegisters();
            Notification.show("Tags atualizadas com sucesso!");
        } catch (Exception e) {
            Notification.show("Erro ao atualizar as tags!");
        } finally {
            updateTagsConfirmDialog.close();
        }
    }

    void importFile(ClickEvent<Button> event) {
        File file = null;
        if (buffer.getFileData() == null) {
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
            this.fileService.importOFX(file, institution);
            upload.clearFileList();
            Notification.show("Arquivo importado com sucesso");
        } catch (IOException | FinancialInstitutionNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
