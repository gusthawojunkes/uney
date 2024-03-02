package solutions.wo.it.services;

import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;
import solutions.wo.it.data.core.enums.FinancialInstitution;
import solutions.wo.it.data.core.exceptions.FinancialInstitutionNotSupportedException;
import solutions.wo.it.data.ofx.OFXFile;

import solutions.wo.it.database.entities.FinancialTransaction;
import solutions.wo.it.database.entities.User;
import solutions.wo.it.services.imports.ImportFileService;
import solutions.wo.it.services.imports.NubankImportFileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    FinancialTransactionService financialTransactionService;
    UserService userService;
    TagService tagService;
    private FileService() {}
    public FileService(
        FinancialTransactionService financialTransactionService,
        UserService userService,
        TagService tagService
    ) {
        this.financialTransactionService = financialTransactionService;
        this.userService = userService;
        this.tagService = tagService;
    }

    private ImportFileService getImportService(FinancialInstitution institution) throws FinancialInstitutionNotSupportedException {
        switch (institution) {
            case NUBANK -> {
                return new NubankImportFileService();
            }
            default -> throw new FinancialInstitutionNotSupportedException();
        }

    }

    public void importOFX(
        File file,
        FinancialInstitution institution
    ) throws IOException, FinancialInstitutionNotSupportedException {
        var importService = this.getImportService(institution);

        try {
            List<FinancialTransaction> financialTransactions = new ArrayList<>();
            file = importService.organizeFile(file);
            Optional<OFXFile> data = importService.readOFXFile(file);
            if (data.isPresent()) financialTransactions = importService.createFinancialTransactions(data.get());

            financialTransactions = filterExistingRegisters(institution, financialTransactions);
            defineUser(financialTransactions);

            financialTransactionService.bulkInsert(financialTransactions);
            Notification.show(financialTransactions.size() + " registros foram importados com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Ocorreu um erro ao importar os dados de um arquivo!");
        } finally {
            if (file != null) {
                Files.deleteIfExists(Path.of(file.getAbsolutePath()));
            }
        }
    }

    private List<FinancialTransaction> filterExistingRegisters(FinancialInstitution institution, List<FinancialTransaction> financialTransactions) {
        var transactionsByFinancialInstitution = financialTransactionService.findAlreadyExistentTransactionsByFinancialInstitution();
        financialTransactions = financialTransactions.stream().filter(transaction -> {
            List<String> transactionsToIgnore = transactionsByFinancialInstitution.get(institution);
            if (transactionsToIgnore == null) transactionsToIgnore = new ArrayList<>();
            return !transactionsToIgnore.contains(transaction.getInstitutionUuid());
        }).toList();
        return financialTransactions;
    }

    private void defineUser(List<FinancialTransaction> financialTransactions) {
        User user = userService.getLoggedUser();
        financialTransactions.forEach(transaction -> transaction.setUser(user));
    }

}
