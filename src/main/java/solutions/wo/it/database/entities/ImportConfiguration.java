package solutions.wo.it.database.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name = "import_configuration")
public class ImportConfiguration extends AbstractEntity {

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean ignoreInvoicePaymentRecords;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid", nullable = false)
    private User user;

}
