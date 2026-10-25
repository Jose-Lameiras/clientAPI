# 🚀 Guia Rápido - Client API

## 🛠️ 1. Instalação (precisa do docker e do git)

- **Git:** [https://git-scm.com/downloads](https://git-scm.com/downloads)



- **Docker:** [https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)

## ⚙️ 2. Execução
```bash
git clone https://github.com/Jose-Lameiras/clientAPI
cd clientAPI
docker compose up --build -d
```

## 📖 3. Swagger & Autenticação
**URL:** `http://localhost:8080/swagger-ui/index.html`

**Como autenticar:**
1. Faz login em `/api/auth/login` (`user` / `password`).
2. Copia o `token` da resposta.
3. Clica no botão **Authorize** no topo do Swagger.
4. Cola o token e clica em **Authorize**.

## 🛑 4. Desligar Servidor
```bash
docker compose down
```
