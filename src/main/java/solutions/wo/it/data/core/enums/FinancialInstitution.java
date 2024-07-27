package solutions.wo.it.data.core.enums;

import lombok.Getter;

@Getter
public enum FinancialInstitution {

    NUBANK("Nubank"),
    ITAU("Itaú"),
    C6BANK("C6 Bank"),
    CAIXA("Caixa Econômica Federal");

    private final String description;

    FinancialInstitution(String description) {
        this.description = description;
    }

}
