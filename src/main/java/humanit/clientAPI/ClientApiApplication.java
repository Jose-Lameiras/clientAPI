package humanit.clientAPI;

import humanit.clientAPI.entity.Client;
import humanit.clientAPI.entity.Document;
import humanit.clientAPI.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class ClientApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Client c1 = createClient("Ana Silva", "111222333", "ana@email.com", "910000001", 
                    List.of(createDoc("D1", "Cartao Cidadao", 2030), createDoc("D2", "Passaporte", 2025)));
                
                Client c2 = createClient("Bruno Costa", "444555666", "bruno@email.com", "910000002", 
                    List.of(createDoc("D3", "Carta Conducao", 2028)));
                
                Client c3 = createClient("Carla Santos", "777888999", "carla@email.com", "910000003", 
                    List.of(createDoc("D4", "CC", 2029), createDoc("D5", "NIT", 2030), createDoc("D6", "Passaporte", 2024)));
                
                Client c4 = createClient("David Lima", "123123123", "david@email.com", "910000004", 
                    List.of(createDoc("D7", "CC", 2031), createDoc("D8", "Carta", 2026)));
                
                Client c5 = createClient("Elena Rocha", "987987987", "elena@email.com", "910000005", 
                    List.of(createDoc("D9", "Passaporte", 2027)));

                repository.saveAll(List.of(c1, c2, c3, c4, c5));
            }
        };
    }

    private Client createClient(String name, String taxId, String email, String phone, List<Document> docs) {
        Client client = Client.builder()
                .firstName(name.split(" ")[0])
                .lastName(name.split(" ")[1])
                .taxIdentifier(taxId)
                .email(email)
                .phoneNumber(phone)
                .build();
        docs.forEach(client::addDocument);
        return client;
    }

    private Document createDoc(String num, String desc, int year) {
        return Document.builder()
                .number(num)
                .description(desc)
                .expirationDate(LocalDate.of(year, 12, 31))
                .build();
    }
}

