package solutions.wo.it.database.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass

@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String uuid;

    @Column(name = "inserted_at")
    protected LocalDateTime insertedAt;

    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        insertedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
