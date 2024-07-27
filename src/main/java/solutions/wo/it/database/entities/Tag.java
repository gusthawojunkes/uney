package solutions.wo.it.database.entities;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter @Setter
@Entity @Table(name = "tag")
public class Tag extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @Column(columnDefinition = "text")
    private String keywords;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "tags")
    private Set<FinancialTransaction> transactions = new HashSet<>();

    public Set<String> getKeywordsSet() {
        if (StringUtils.isEmpty(this.keywords)) return new HashSet<>();
        String[] keywordsArray = this.keywords.split("[,]");
        return Stream.of(keywordsArray).map(String::trim).collect(Collectors.toSet());
    }

}
