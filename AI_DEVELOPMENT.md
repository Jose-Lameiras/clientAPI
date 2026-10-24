# AI Development Evidence

## AI Tools Used
- opencode CLI

## Models Used
- gemma4:31b-cloud

## Important Prompts/Instructions
- "cria me um agents.md e um AI Development Evidence"
- "cria me as entidades client e document"
- "Apartir de agora vou te pedir para criar components,entitys etc, preciso que vás escrevendo o que te peço no AI_DEVELOPMENT, para ficar documentado e eu depois alterar"

## Agent Contributions
- **Planning**: Organized the structure of the `AGENTS.md` and `AI_DEVELOPMENT.md` files.
- **Implementation**: 
    - Created the `Client` and `Document` JPA entities based on specific requirements.
    - Implemented helper methods `addDocument` and `removeDocument` in the `Client` entity to manage bidirectional relationships.
    - Created `ClientRepository` and a Spring Boot integration test to verify client creation with documents.
    - Implemented `ClientController` to provide REST endpoints for client management.
    - Configured Swagger/OpenAPI 3 using `springdoc-openapi` and adjusted Security configuration to permit access to the documentation UI.
- **Review**: Ensured compliance with the workflow rules defined in `AGENTS.md`.

## AI Suggestions Handling
- **Example**: The agent suggested a general project structure; I modified it to strictly follow the requested entity fields (Id, FirstName, LastName, etc.) to ensure precision.

## Reflection
- **Strengths**: 
- **Limitations**: 
