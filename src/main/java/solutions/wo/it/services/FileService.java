package solutions.wo.it.services;

import com.vaadin.flow.component.notification.Notification;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import solutions.wo.it.data.core.enums.FinancialInstitution;
import solutions.wo.it.data.core.exceptions.FinancialInstitutionNotSupportedException;
import solutions.wo.it.data.ofx.OFXFile;
import solutions.wo.it.database.entities.FinancialTransaction;
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

    public FileService(FinancialTransactionService financialTransactionService, UserService userService) {
        this.financialTransactionService = financialTransactionService;
        this.userService = userService;
    }

    private ImportFileService getImportService(FinancialInstitution institution) throws FinancialInstitutionNotSupportedException {
        if (institution == FinancialInstitution.NUBANK) {
            return new NubankImportFileService();
        }
        throw new FinancialInstitutionNotSupportedException();

    }

    public void importOFX(
        File file,
        FinancialInstitution institution
    ) throws IOException, FinancialInstitutionNotSupportedException {
        var importService = this.getImportService(institution);

        try {
            List<FinancialTransaction> transactions = new ArrayList<>();
            file = importService.organizeFile(file);
            Optional<OFXFile> data = importService.readOFXFile(file);
            if (data.isPresent()) transactions = importService.createFinancialTransactions(data.get());

            transactions = filterExistingRegisters(institution, transactions);
            financialTransactionService.defineUser(transactions);
            financialTransactionService.defineTags(transactions);
            financialTransactionService.bulkInsert(transactions);

            Notification.show(transactions.size() + " registros foram importados com sucesso!");
        } catch (Exception e) {
            Notification.show("Ocorreu um erro ao importar os dados de um arquivo!");
        } finally {
            if (file != null) {
                Files.deleteIfExists(Path.of(file.getAbsolutePath()));
            }
        }
    }

    private List<FinancialTransaction> filterExistingRegisters(FinancialInstitution institution, List<FinancialTransaction> transactions) {
        if (CollectionUtils.isEmpty(transactions)) return new ArrayList<>();
        var transactionsByFinancialInstitution = financialTransactionService.findAlreadyExistentTransactionsByFinancialInstitution();
        transactions = transactions.stream().filter(transaction -> {
            List<String> transactionsToIgnore = transactionsByFinancialInstitution.get(institution);
            if (transactionsToIgnore == null) transactionsToIgnore = new ArrayList<>();
            return !transactionsToIgnore.contains(transaction.getInstitutionUuid());
        }).toList();
        return transactions;
    }

}
