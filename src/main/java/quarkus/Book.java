package quarkus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String genre;
    private int numPages;
    private String description;
    @CreationTimestamp
    private LocalDate pubDate;
    @UpdateTimestamp
    private LocalDate updateDate;

}
