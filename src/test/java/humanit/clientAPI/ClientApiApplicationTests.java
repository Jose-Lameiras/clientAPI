package humanit.clientAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import humanit.clientAPI.dto.*;
import humanit.clientAPI.entity.Client;
import humanit.clientAPI.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClientApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());



    private Long savedClientId;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
        Client client = Client.builder()
                .firstName("Test")
                .lastName("User")
                .taxIdentifier("123456")
                .email("test@test.com")
                .phoneNumber("123456789")
                .build();
        savedClientId = clientRepository.save(client).getId();
    }

    @Test
    public void testGetAllClients() throws Exception {
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Test"));
    }

    @Test
    public void testGetClientById() throws Exception {
        mockMvc.perform(get("/api/clients/" + savedClientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Test"));
    }

    @Test
    public void testGetClientByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/clients/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateClientWithDocuments() throws Exception {
        DocumentRequestDTO docDto = DocumentRequestDTO.builder()
                .number("DOC123")
                .description("ID Card")
                .expirationDate(LocalDate.now().plusYears(1))
                .build();

        ClientRequestDTO requestDto = ClientRequestDTO.builder()
                .firstName("New")
                .lastName("Client")
                .taxIdentifier("99999")
                .email("new@client.com")
                .phoneNumber("000000")
                .documents(List.of(docDto))
                .build();

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("New"))
                .andExpect(jsonPath("$.documents[0].number").value("DOC123"));
    }

    @Test
    public void testUpdateClient() throws Exception {
        ClientRequestDTO updateDto = ClientRequestDTO.builder()
                .firstName("Updated")
                .lastName("Name")
                .taxIdentifier("123456")
                .email("updated@test.com")
                .phoneNumber("123456789")
                .build();

        mockMvc.perform(put("/api/clients/" + savedClientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));
    }

    @Test
    public void testDeleteClient() throws Exception {
        mockMvc.perform(delete("/api/clients/" + savedClientId))
                .andExpect(status().isNoContent());
        
        mockMvc.perform(get("/api/clients/" + savedClientId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteClientNotFound() throws Exception {
        mockMvc.perform(delete("/api/clients/999"))
                .andExpect(status().isNotFound());
    }
}
