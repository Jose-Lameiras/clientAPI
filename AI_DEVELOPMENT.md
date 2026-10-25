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

- "A propriedade security.enabled=false não existe no Spring e não quero desligar a segurança nos testes. Apaga o application-test.properties e o @ActiveProfiles. Usa .with(jwt()) do SecurityMockMvcRequestPostProcessors em cada pedido dos testes. Adiciona também um teste que confirma 401 sem token e um teste de login que devolve um token. Corre ./mvnw clean test até passar."

- "Voltaste a usar o import do Spring Boot 3. O AutoConfigureMockMvc está em org.springframework.boot.webmvc.test.autoconfigure. Corrige e remove o spring-boot-starter-test do pom.xml."

- "vamos fazer um cleanCode, analisa os codigos e diz me onde achas que devemos mudar"

- "implementa o mapper e a sincronização de documentos no update"

- "implementa a validação de campos e a unicidade de NIF e Email"

- "configura o botão de autenticação JWT no Swagger"

- "cria DTO de Login para evitar generic props no Swagger"

- "configura docker container e docker-compose com variáveis de ambiente"

- "cria dados iniciais (seed) para arrancar a aplicação"

- "corrige erros de compilação e imports de Optional"

## Agent Contributions
- **Planning**: Organized the structure of the `AGENTS.md` and `AI_DEVELOPMENT.md` files.
- **Implementation**: \n     - Created the `Client` and `Document` JPA entities based on specific requirements.\n     - Implemented helper methods `addDocument` and `removeDocument` in the `Client` entity to manage bidirectional relationships.\n     - Created `ClientRepository` and a Spring Boot integration test to verify client creation with documents.\n     - Implemented a full layered architecture: `ClientController` -> `ClientService` -> `ClientRepository`.\n     - Implemented DTOs (`ClientRequestDTO`, `ClientResponseDTO`, etc.) to decouple entities from the API layer and remove sensitive/unnecessary fields (like `id`) from requests.\n     - Configured Swagger/OpenAPI 3 using `springdoc-openapi` and adjusted Security configuration to permit access to the documentation UI.\n     - Added a global exception handling mechanism using `@ControllerAdvice` for consistent REST error responses.\n     - Created `ClientNotFoundException` and integrated it into `ClientService`.\n     - Implemented a `CommandLineRunner` in `ClientApiApplication` to seed the database with initial test data (5 clients with 1-3 documents each), including a check to prevent duplicate insertions on restart.\n     - Corrected the logic to ensure documents are correctly persisted when creating/updating a client.

    - Refactored controllers to use proper REST status codes (e.g., `201 Created`, `204 No Content`).
    - Implemented comprehensive integration tests for all API endpoints using `MockMvc`.
    - Resolved `NoSuchBeanDefinitionException` for `ObjectMapper` in tests by switching to manual instantiation.
    - Fixed `InvalidDefinitionException` related to `LocalDate` serialization by registering `JavaTimeModule`.
    - Fixed `NullPointerException` in `Client.addDocument` by adding `@Builder.Default` to the documents list to ensure initialization when using Lombok builders.

- **Review**: Ensured compliance with the workflow rules defined in `AGENTS.md`.

## AI Suggestions Handling
- **Rejected**: The agent initially proposed using a `test` profile to disable security (`security.enabled=false`). This was rejected because security must remain active during integration tests to ensure real-world validation.
- **Corrected**: The agent initially used Spring Boot 3 imports for `AutoConfigureMockMvc`. This was corrected to the Spring Boot 4 package: `org.springframework.boot.webmvc.test.autoconfigure`.
- **Accepted**: The use of symmetric HMAC-SHA256 for JWT instead of RSA to simplify configuration and avoid complex key pair management.
- **Corrected**: The agent missed the `JavaTimeModule` registration for the manually instantiated `ObjectMapper` in tests, causing `LocalDate` serialization errors. This was fixed by explicitly registering the module.
- **Accepted**: The implementation of a layered architecture (Controller $\rightarrow$ Service $\rightarrow$ Repository) and the use of DTOs to decouple the API from the database entities.

## Reflection
- **Strengths**: 
- **Limitations**: 
- **Knowledge Transfer**: Explained the concept of the Mapper Design Pattern to the user, detailing its importance for security, decoupling, and maintaining Clean Code by separating Entities from DTOs.
