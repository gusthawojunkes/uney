package solutions.wo.it.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import solutions.wo.it.data.core.enums.FinancialInstitution;
import solutions.wo.it.data.core.exceptions.FinancialInstitutuinNotSupportedException;
import solutions.wo.it.services.imports.NubankImportFinancialFileService;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Financial File Service test cases")
class FinancialFileServiceTest {

    @Test
    @DisplayName("The chosen import service must be nubank if the chosen financial institution is nubank")
    public void theChosenImportServiceMustBeNubankIfTheChosenFinancialInstitutionIsNubankWhenTheConstructorIsCalled() throws FinancialInstitutuinNotSupportedException {
        FinancialFileService financialFileService = new FinancialFileService(FinancialInstitution.NUBANK);
        assertEquals(NubankImportFinancialFileService.class, financialFileService.importService.getClass());
    }

}