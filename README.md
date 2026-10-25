# Client API

API for managing clients and their associated documents.

## 🚀 Getting Started

### Prerequisites
- Java 21
- Maven 3.9+
- Docker (optional)

### 🛠️ Running with Maven
1. Clone the repository.
2. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Access the API at `http://localhost:8080`

### 🐳 Running with Docker
1. Build and start the container:
   ```bash
   docker compose up --build -d
   ```
2. To stop the application:
   ```bash
   docker compose down
   ```

---

## 📖 API Documentation (Swagger)

The API is fully documented using Swagger UI.
- **URL:** `http://localhost:8080/swagger-ui/index.html`

### 🔐 Authentication Flow
All endpoints (except `/api/auth/login`) require a JWT Token.

1. **Get Token:** 
   - Go to `/api/auth/login` in Swagger.
   - Use credentials: `username: user`, `password: password`.
   - Copy the `token` from the response.
2. **Authorize:**
   - Click the **Authorize** button at the top of the Swagger UI.
   - Paste the token (without the "Bearer " prefix).
   - Click **Authorize** $\rightarrow$ **Close**.
3. **Use API:** You can now test any protected endpoint.

---

## 🧪 Testing
To run the automated integration tests:
```bash
./mvnw clean test
```
