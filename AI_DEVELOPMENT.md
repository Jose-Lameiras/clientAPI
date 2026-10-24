# AI Development Evidence

## AI Tools Used
- opencode CLI

## Models Used
- gemma4:31b-cloud

## Important Prompts/Instructions
- "cria me um agents.md e um AI Development Evidence"
- "cria me as entidades client e document"
- "Apartir de agora vou te pedir para criar components,entitys etc, preciso que vás escrevendo o que te peço no AI_DEVELOPMENT, para ficar documentado e eu depois alterar"
- "Adiciona addDocument() e removeDocument() no Client"
- "Cria um ClientRepository e faz me já um codigo de teste a criar 2 clientes com o addDocument"
- "cria me o ClientController"
- "ajuda me a configurar o swagger para este projeto"
- "Revê o que fizeste. Os documentos ainda não são gravados com o cliente, e os status codes e o tratamento de erros não seguem as convenções REST. Corrige isso e cria um tratamento global de exceções."
- "O springdoc 2.6.0 é para Spring Boot 3. Atualiza o pom.xml para uma versão 3.x do springdoc, compatível com Spring Boot 4."

## Agent Contributions
- **Planning**: Organized the structure of the `AGENTS.md` and `AI_DEVELOPMENT.md` files.
- **Implementation**: 
    - Created the `Client` and `Document` JPA entities based on specific requirements.
    - Implemented helper methods `addDocument` and `removeDocument` in the `Client` entity to manage bidirectional relationships.
    - Created `ClientRepository` and a Spring Boot integration test to verify client creation with documents.
    - Implemented a full layered architecture: `ClientController` -> `ClientService` -> `ClientRepository`.
    - Implemented DTOs (`ClientRequestDTO`, `ClientResponseDTO`, etc.) to decouple entities from the API layer and remove sensitive/unnecessary fields (like `id`) from requests.
    - Configured Swagger/OpenAPI 3 using `springdoc-openapi` and adjusted Security configuration to permit access to the documentation UI.
    - Added a global exception handling mechanism using `@ControllerAdvice` for consistent REST error responses.
    - Corrected the logic to ensure documents are correctly persisted when creating/updating a client.
    - Refactored controllers to use proper REST status codes (e.g., `201 Created`, `204 No Content`).
    - Implemented comprehensive integration tests for all API endpoints using `MockMvc`.
    - Resolved `NoSuchBeanDefinitionException` for `ObjectMapper` in tests by switching to manual instantiation.
    - Fixed `InvalidDefinitionException` related to `LocalDate` serialization by registering `JavaTimeModule`.
    - Fixed `NullPointerException` in `Client.addDocument` by adding `@Builder.Default` to the documents list to ensure initialization when using Lombok builders.

- **Review**: Ensured compliance with the workflow rules defined in `AGENTS.md`.

## AI Suggestions Handling
- **Example**: The agent suggested a general project structure; I modified it to strictly follow the requested entity fields (Id, FirstName, LastName, etc.) to ensure precision.

- **Example**: Encountered a dependency version mismatch with `springdoc-openapi`. I used Claude Code to generate the correct prompt and version requirement to resolve the compatibility issue with Spring Boot 4.

## Reflection
- **Strengths**: 
- **Limitations**: 
