package solutions.wo.it.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Financial File Service test cases")
class FinancialFileServiceTest {

    @Test
    @DisplayName("The chosen import service must be nubank if the chosen financial institution is nubank")
    public void theChosenImportServiceMustBeNubankIfTheChosenFinancialInstitutionIsNubankWhenTheConstructorIsCalled() {
        FileService financialFileService = new FileService(null); //MOCK
//        assertEquals(NubankImportFinancialFileService.class, financialFileService.importService.getClass());
    }

}