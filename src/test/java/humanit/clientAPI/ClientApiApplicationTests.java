package humanit.clientAPI;

import humanit.clientAPI.entity.Client;
import humanit.clientAPI.entity.Document;
import humanit.clientAPI.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ClientApiApplicationTests {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void testCreateClientsWithDocuments() {
        // Client 1
        Client client1 = Client.builder()
                .firstName("John")
                .lastName("Doe")
                .taxIdentifier("123456789")
                .email("john.doe@example.com")
                .phoneNumber("1234567890")
                .documents(new ArrayList<>())
                .build();

        Document doc1 = Document.builder()
                .number("DOC1")
                .description("ID Card")
                .expirationDate(LocalDate.now().plusYears(1))
                .build();
        
        client1.addDocument(doc1);

        // Client 2
        Client client2 = Client.builder()
                .firstName("Jane")
                .lastName("Smith")
                .taxIdentifier("987654321")
                .email("jane.smith@example.com")
                .phoneNumber("0987654321")
                .documents(new ArrayList<>())
                .build();

        Document doc2 = Document.builder()
                .number("DOC2")
                .description("Passport")
                .expirationDate(LocalDate.now().plusYears(5))
                .build();
        
        client2.addDocument(doc2);

        clientRepository.save(client1);
        clientRepository.save(client2);

        assertNotNull(client1.getId());
        assertNotNull(client2.getId());
        assertEquals(1, client1.getDocuments().size());
        assertEquals(1, client2.getDocuments().size());
        assertEquals(client1, client1.getDocuments().get(0).getClient());
    }
}
