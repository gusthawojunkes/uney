package solutions.wo.it.data.core.enums;

public enum TransactionType {

    CREDIT("Crédito"),
    DEBIT("Débito"),
    PIX("Pix");

    private String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
