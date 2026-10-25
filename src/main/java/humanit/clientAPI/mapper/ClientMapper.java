package humanit.clientAPI.mapper;

import humanit.clientAPI.dto.*;
import humanit.clientAPI.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {

    public ClientResponseDTO toResponseDTO(Client client) {
        if (client == null) return null;

        return ClientResponseDTO.builder()
                .id(client.getId())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .taxIdentifier(client.getTaxIdentifier())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .documents(mapDocumentsToResponse(client.getDocuments()))
                .build();
    }

    public Client toEntity(ClientRequestDTO dto) {
        if (dto == null) return null;

        return Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .taxIdentifier(dto.getTaxIdentifier())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }

    public Document toDocumentEntity(DocumentRequestDTO docDto) {
        if (docDto == null) return null;

        return Document.builder()
                .number(docDto.getNumber())
                .description(docDto.getDescription())
                .expirationDate(docDto.getExpirationDate())
                .build();
    }

    private List<DocumentResponseDTO> mapDocumentsToResponse(List<Document> documents) {
        if (documents == null) return null;
        return documents.stream()
                .map(doc -> DocumentResponseDTO.builder()
                        .id(doc.getId())
                        .number(doc.getNumber())
                        .description(doc.getDescription())
                        .expirationDate(doc.getExpirationDate())
                        .build())
                .collect(Collectors.toList());
    }
}
