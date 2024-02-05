package solutions.wo.it.database.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import solutions.wo.it.data.core.enums.Status;

@Entity
@Table(name = "application_user")
@Getter
@Setter
public class User extends AbstractEntity {

    @Column(length = 60, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private Status status;

}
