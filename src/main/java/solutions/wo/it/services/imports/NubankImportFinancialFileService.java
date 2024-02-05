package solutions.wo.it.services.imports;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import solutions.wo.it.data.core.enums.FinancialInstitution;
import solutions.wo.it.data.core.enums.TransactionType;
import solutions.wo.it.data.ofx.OFXDataHelper;
import solutions.wo.it.data.ofx.OFXFile;
import solutions.wo.it.database.entities.FinancialTransaction;
import solutions.wo.it.data.ofx.StmtTrn;
import solutions.wo.it.services.UserService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;

@Service
public class NubankImportFinancialFileService implements ImportFinancialFileService {

    UserService userService;

    @Override
    public File findFileFromPath(String path) {
        System.out.println("Reading path: " + path + "...");
        return new File(path);
    }

    @Override
    public File organizeFile(File file) {
        try {
            return this.transformIntoXML(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Optional<OFXFile> readOFXFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(OFXFile.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            OFXFile data = (OFXFile) unmarshaller.unmarshal(file);
            return Optional.of(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<FinancialTransaction> createFinancialTransactions(OFXFile ofx) {
        List<FinancialTransaction> financialTransactions = new ArrayList<>();
        List<StmtTrn> transactionsFromFile = ofx.creditCardMsgsRsV1.ccStmtTrnRs.ccStmtRs.bankTranList.stmtTrnList;
        for (StmtTrn ofxTransaciton : transactionsFromFile) {
            FinancialTransaction transaction = new FinancialTransaction();
            transaction.setInstitution(FinancialInstitution.NUBANK);
            transaction.setTransactionType(TransactionType.CREDIT);
            transaction.setInstitutionUuid(ofxTransaciton.fitId);
            transaction.setValue(Double.valueOf(ofxTransaciton.trnAmt));
            transaction.setDescription(ofxTransaciton.memo);
            transaction.setTransactionTime(OFXDataHelper.getDate(ofxTransaciton.dtPosted));
            financialTransactions.add(transaction);
        }

        return financialTransactions;
    }

    public File transformIntoXML(File file) {
        String content = readAndTransform(file);
        writeNewFile(file, content);
        return file;
    }

    private static String readAndTransform(File file) {
        StringBuilder cleanedContent = new StringBuilder();
        Map<String, String> endingsByProperty = getEndingsByProperty();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                String cleanedLine = line.trim();
                if (StringUtils.isNotBlank(cleanedLine)) {
                    boolean needToReformat = cleanedLine.contains(":");
                    if (lineNumber < 10 && needToReformat) {
                        String[] lineProps = cleanedLine.split(":");
                        String property = lineProps[0];
                        String value = lineProps[1];
                        cleanedLine = getStartingFor(property) + value + getEndingFor(property);
                    } else {
                        if (StringUtils.equals(cleanedLine, "<OFX>")) continue;
                        int greaterThanSignIndex = cleanedLine.indexOf('>');
                        String property = cleanedLine.substring(1, greaterThanSignIndex);

                        if (StringUtils.equals(cleanedLine, "</BANKTRANLIST>")) {
                            cleanedLine = "</STMTTRN>" + System.lineSeparator() + cleanedLine;
                        }

                        boolean hasValue = !cleanedLine.replace(getStartingFor(property), "").trim().isEmpty();
                        if (!cleanedLine.endsWith(getEndingFor(property)) && hasValue && endingsByProperty.containsKey(property)) {
                            cleanedLine += endingsByProperty.get(property);
                        }
                        if (StringUtils.equals(property, "DTEND")) {
                            cleanedLine += System.lineSeparator() + "<STMTTRN>";
                        }

                    }
                }
                if (lineNumber == 1) cleanedContent.append("<OFX>").append(System.lineSeparator());
                cleanedContent.append(cleanedLine).append(System.lineSeparator());
                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de entrada: " + e.getMessage());
        }

        return cleanedContent.toString();
    }

    private static void writeNewFile(File file, String cleanedContent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(cleanedContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStartingFor(String property) {
        return "<" + property + ">";
    }

    public static String getEndingFor(String property) {
        return "</" + property + ">";
    }
    private static Map<String, String> getEndingsByProperty() {
        Map<String, String> endingsByProperty = new HashMap<>();
        endingsByProperty.put("CODE", "</CODE>");
        endingsByProperty.put("SEVERITY", "</SEVERITY>");
        endingsByProperty.put("LANGUAGE", "</LANGUAGE>");
        endingsByProperty.put("DTSERVER", "</DTSERVER>");
        endingsByProperty.put("CURDEF", "</CURDEF>");
        endingsByProperty.put("ACCTID", "</ACCTID>");
        endingsByProperty.put("DTSTART", "</DTSTART>");
        endingsByProperty.put("DTEND", "</DTEND>");
        endingsByProperty.put("TRNTYPE", "</TRNTYPE>");
        endingsByProperty.put("DTPOSTED", "</DTPOSTED>");
        endingsByProperty.put("TRNAMT", "</TRNAMT>");
        endingsByProperty.put("FITID", "</FITID>");
        endingsByProperty.put("MEMO", "</MEMO>");
        endingsByProperty.put("BALAMT", "</BALAMT>");
        endingsByProperty.put("DTASOF", "</DTASOF>");
        endingsByProperty.put("TRNUID", "</TRNUID>");
        return endingsByProperty;
    }

}
