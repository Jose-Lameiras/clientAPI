package humanit.clientAPI.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String taxIdentifier;
    private String email;
    private String phoneNumber;

    @Builder.Default
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new java.util.ArrayList<>();


    public void addDocument(Document document) {
        documents.add(document);
        document.setClient(this);
    }

    public void removeDocument(Document document) {
        documents.remove(document);
        document.setClient(null);
    }
}
