package solutions.wo.it.services;

import org.springframework.stereotype.Service;
import solutions.wo.it.data.core.enums.FinancialInstitution;
import solutions.wo.it.data.core.exceptions.FinancialInstitutuinNotSupportedException;
import solutions.wo.it.data.ofx.OFXFile;

import solutions.wo.it.database.entities.FinancialTransaction;
import solutions.wo.it.services.imports.ImportFinancialFileService;
import solutions.wo.it.services.imports.NubankImportFinancialFileService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FinancialFileService {

    public ImportFinancialFileService importService;
    public FinancialTransactionService financialTransactionService;
    private FinancialFileService() {}
    public FinancialFileService(FinancialInstitution institution) throws FinancialInstitutuinNotSupportedException {
        if (institution == FinancialInstitution.NUBANK) {
            importService = new NubankImportFinancialFileService();
        } else {
            throw new FinancialInstitutuinNotSupportedException();
        }
    }

    public void importOFX(String path) {
        List<FinancialTransaction> financialTransactions = new ArrayList<>();
        File file = importService.findFileFromPath(path);
        file = importService.organizeFile(file);
        Optional<OFXFile> data = importService.readOFXFile(file);
        if (data.isPresent()) {
            financialTransactions = importService.createFinancialTransactions(data.get());
        }
        financialTransactionService.bulkInsert(financialTransactions);
    }

    public static void main(String[] args) {
        try {
            FinancialFileService importService = new FinancialFileService(FinancialInstitution.NUBANK);
            importService.importOFX("C:\\Users\\WO\\Documents\\nubank\\nubank-2022-05 - Copy (2).ofx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
