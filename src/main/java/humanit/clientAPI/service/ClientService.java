package humanit.clientAPI.service;

import humanit.clientAPI.dto.*;
import humanit.clientAPI.entity.Client;
import humanit.clientAPI.entity.Document;
import humanit.clientAPI.exception.ClientNotFoundException;
import humanit.clientAPI.mapper.ClientMapper;
import humanit.clientAPI.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public List<ClientResponseDTO> findAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ClientResponseDTO findById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toResponseDTO)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Transactional
    public ClientResponseDTO create(ClientRequestDTO dto) {
        if (clientRepository.findByTaxIdentifier(dto.getTaxIdentifier()).isPresent()) {
            throw new IllegalArgumentException("Client with tax identifier " + dto.getTaxIdentifier() + " already exists");
        }
        if (clientRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Client with email " + dto.getEmail() + " already exists");
        }

        Client client = clientMapper.toEntity(dto);

        if (dto.getDocuments() != null) {
            dto.getDocuments().forEach(docDto -> {
                Document doc = clientMapper.toDocumentEntity(docDto);
                client.addDocument(doc);
            });
        }

        return clientMapper.toResponseDTO(clientRepository.save(client));
    }

    @Transactional
    public ClientResponseDTO update(Long id, ClientRequestDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        
        Optional<Client> existingTaxId = clientRepository.findByTaxIdentifier(dto.getTaxIdentifier());
        if (existingTaxId.isPresent() && !existingTaxId.get().getId().equals(id)) {
            throw new IllegalArgumentException("Tax identifier " + dto.getTaxIdentifier() + " is already taken by another client");
        }

        Optional<Client> existingEmail = clientRepository.findByEmail(dto.getEmail());
        if (existingEmail.isPresent() && !existingEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("Email " + dto.getEmail() + " is already taken by another client");
        }

        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setTaxIdentifier(dto.getTaxIdentifier());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        
        if (dto.getDocuments() != null) {
            syncDocuments(client, dto.getDocuments());
        }
        
        return clientMapper.toResponseDTO(clientRepository.save(client));
    }

    private void syncDocuments(Client client, List<DocumentRequestDTO> docDtos) {
        // 1. Remover documentos que não estão no DTO
        client.getDocuments().removeIf(doc -> 
            docDtos.stream().noneMatch(dto -> dto.getNumber() != null && dto.getNumber().equals(doc.getNumber()))
        );

        // 2. Atualizar existentes ou adicionar novos
        for (DocumentRequestDTO docDto : docDtos) {
            client.getDocuments().stream()
                .filter(doc -> docDto.getNumber() != null && docDto.getNumber().equals(doc.getNumber()))
                .findFirst()
                .ifPresentOrElse(
                    doc -> {
                        doc.setDescription(docDto.getDescription());
                        doc.setExpirationDate(docDto.getExpirationDate());
                    },
                    () -> {
                        Document newDoc = clientMapper.toDocumentEntity(docDto);
                        client.addDocument(newDoc);
                    }
                );
        }
    }
    @Transactional
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException(id);
        }
        clientRepository.deleteById(id);
    }
}

