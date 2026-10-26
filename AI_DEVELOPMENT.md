# AI Development Evidence

## 1. AI Tools Used
- **OpenCode with Ollama**: Primary tool for code generation and project execution.
- **Claude Code**: Used as a reviewer for complex debugging and as a prompt engineering assistant to refine instructions before applying them to the project.

## 2. Model(s) Used
- **gemma4:31b-cloud**
- **Claude Opus 5.5**

## 3. Important Prompts
1. Create `AGENTS.md` and `AI_DEVELOPMENT.md`.
2. Create `Client` and `Document` entities.
3. Implement `ClientController`, `ClientService`, `ClientRepository`, and DTOs.
4. Configure Swagger/OpenAPI for the project.
5. Implement JWT authentication (Login and Token generation).
6. Create comprehensive integration tests with `MockMvc`.
7. Implement input validation and unique constraints (NIF/Email).
8. Configure Docker container and Docker Compose with environment variables.
9. Fix N+1 query problem in `findAll`.

**Real Prompt Examples:**
- "Completa os DTOs que faltam e liga as camadas (controller, service, DTOs, entities e repository). Quero criar e atualizar um cliente com os seus documentos numa só transação."
- "A propriedade security.enabled=false não existe no Spring e não quero desligar a segurança nos testes. Usa .with(jwt()) em cada pedido dos testes."
- "O GET /api/clients está a gerar o problema N+1: uma query para os clientes e mais uma por cada cliente para os documentos. Corrige com @EntityGraph."

## 4. How the Agent Contributed
- **Planning**: Defined the layered architecture (Controller → Service → Repository) and DTO strategy.
- **Implementation**: Wrote the JPA entities, REST controllers, business logic, and security configuration.
- **Testing**: Created integration tests covering success and failure scenarios.
- **Debugging**: Resolved compilation errors, dependency conflicts, and runtime exceptions.
- **Review**: Refactored code for Clean Code principles, including the introduction of a Mapper pattern.

## 5. Suggestions Handling
### Rejected
- **Mock JWT Token**: Initial suggestion to use a hardcoded fake token was replaced by a real HMAC HS256 implementation.
- **Security Disable**: Proposed `security.enabled=false` for tests; rejected in favor of using `.with(jwt())` to maintain real security validation.
- **Text-based Status Codes**: Initial logic used message content to decide HTTP status; replaced by a structured `GlobalExceptionHandler`.

### Modified/Corrected
- **Spring Boot Versions**: Initial use of Spring Boot 3 imports for `AutoConfigureMockMvc` was corrected to Spring Boot 4. The import reappeared after a fix, and the agent initially tried to resolve it by adding dependencies instead of fixing the import.
- **Entity Initialization**: Fixed `NullPointerException` in `Client.addDocument` by adding `@Builder.Default` to the documents list; previously, tests hid this bug by manually using `new ArrayList`.
- **Jackson Scope**: `jackson-datatype-jsr310` was initially marked as `test` scope; corrected to global scope to fix application startup.
- **Regressions**: Restored the `delete` method that was accidentally removed during the Mapper refactor.
- **N+1 Problem**: Detected via logs that `GET /api/clients` performed 6 queries instead of 1; resolved using `@EntityGraph`.

### Accepted
- **Layered Architecture**: Decoupling entities from the API via DTOs and a dedicated Mapper.
- **JWT Strategy**: Choice of symmetric HMAC-SHA256 over RSA for simplicity.
- **Docker**: Implementation of a multi-stage `Dockerfile` to reduce image size.

## 6. Reflection
### Strengths
- High speed in generating boilerplate code.
- Efficient creation of comprehensive test suites.
- Strong ability to resolve bugs when provided with the exact error log.
- Useful for explaining complex architectural concepts and patterns.

### Limitations
- Outdated knowledge regarding specific Spring Boot 4 changes.
- Tendency to introduce regressions by deleting existing code during refactoring.
- Occasional "hallucinated" solutions (e.g., non-existent properties like `security.enabled` or fake tokens).
- Tendency to mask problems (e.g., in tests) rather than solving the root cause in the entity.
- Failure to ensure the application actually starts despite all tests passing.
- Occasional repetition of errors previously corrected.

### Conclusion
The AI is an extremely powerful tool for acceleration and structure, but requires constant human supervision. It works best as a "co-pilot" where the developer validates logic and prevents regressions.

