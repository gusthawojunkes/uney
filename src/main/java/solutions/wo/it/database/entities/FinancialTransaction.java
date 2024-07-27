package solutions.wo.it.database.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import solutions.wo.it.data.core.enums.FinancialInstitution;
import solutions.wo.it.data.core.enums.TransactionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
@Entity @Table(name = "financial_transaction")
public class FinancialTransaction extends AbstractEntity {

    @Column(nullable = false)
    private Double value;

    private String description;

    @Column(nullable = false)
    private LocalDateTime transactionTime;

    private String institutionUuid;

    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private FinancialInstitution institution;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
        name = "financial_transaction_tag",
        joinColumns = @JoinColumn(name = "financial_transaction_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    public String getFormattedTransactionTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.transactionTime.format(formatter);
    }

    public String getTransactionTypeDescription() {
        return this.getTransactionType().getDescription();
    }

}
