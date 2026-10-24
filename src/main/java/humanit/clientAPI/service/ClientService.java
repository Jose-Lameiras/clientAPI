package humanit.clientAPI.service;

import humanit.clientAPI.dto.*;
import humanit.clientAPI.entity.Client;
import humanit.clientAPI.entity.Document;
import humanit.clientAPI.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<ClientResponseDTO> findAll() {
        return clientRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ClientResponseDTO findById(Long id) {
        return clientRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }

    @Transactional
    public ClientResponseDTO create(ClientRequestDTO dto) {
        Client client = Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .taxIdentifier(dto.getTaxIdentifier())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .build();

        if (dto.getDocuments() != null) {
            dto.getDocuments().forEach(docDto -> {
                Document doc = Document.builder()
                        .number(docDto.getNumber())
                        .description(docDto.getDescription())
                        .expirationDate(docDto.getExpirationDate())
                        .build();
                client.addDocument(doc);
            });
        }

        return convertToResponseDTO(clientRepository.save(client));
    }

    @Transactional
    public ClientResponseDTO update(Long id, ClientRequestDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setTaxIdentifier(dto.getTaxIdentifier());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        
        if (dto.getDocuments() != null) {
            client.getDocuments().clear();
            dto.getDocuments().forEach(docDto -> {
                Document doc = Document.builder()
                        .number(docDto.getNumber())
                        .description(docDto.getDescription())
                        .expirationDate(docDto.getExpirationDate())
                        .build();
                client.addDocument(doc);
            });
        }
        
        return convertToResponseDTO(clientRepository.save(client));
    }

    @Transactional
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }

    private ClientResponseDTO convertToResponseDTO(Client client) {
        return ClientResponseDTO.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .taxIdentifier(client.getTaxIdentifier())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .documents(client.getDocuments() == null ? null : client.getDocuments().stream()
                        .map(doc -> DocumentResponseDTO.builder()
                                .id(doc.getId())
                                .number(doc.getNumber())
                                .description(doc.getDescription())
                                .expirationDate(doc.getExpirationDate())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
