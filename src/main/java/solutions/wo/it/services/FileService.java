package solutions.wo.it.services;

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
    private FileService() {}
    public FileService(
        FinancialTransactionService financialTransactionService
    ) {
        this.financialTransactionService = financialTransactionService;
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
        String path,
        FinancialInstitution institution
    ) throws IOException, FinancialInstitutionNotSupportedException {
        var importService = this.getImportService(institution);
        File file = importService.findFileFromPath(path);
        importOFX(file, institution);
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
            if (data.isPresent()) {
                financialTransactions = importService.createFinancialTransactions(data.get());
            }
            financialTransactionService.bulkInsert(financialTransactions);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                Files.deleteIfExists(Path.of(file.getAbsolutePath()));
            }
        }
    }

}
