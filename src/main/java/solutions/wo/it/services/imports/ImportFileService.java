package solutions.wo.it.services.imports;

import solutions.wo.it.data.ofx.OFXFile;
import solutions.wo.it.database.entities.FinancialTransaction;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface ImportFileService {

    File findFileFromPath(String path);

    File organizeFile(File file);

    Optional<OFXFile> readOFXFile(File file);

    List<FinancialTransaction> createFinancialTransactions(OFXFile ofx);
    
}
