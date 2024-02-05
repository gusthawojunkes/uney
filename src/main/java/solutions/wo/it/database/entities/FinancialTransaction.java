package solutions.wo.it.database.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import solutions.wo.it.data.core.enums.FinancialInstitution;
import solutions.wo.it.data.core.enums.TransactionType;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private User user;

}
