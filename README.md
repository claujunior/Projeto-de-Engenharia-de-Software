# Tutorial de testar Who is safe?

## Requisitos

* Docker e Docker Compose
* Java 17+
* Postman
* IntelliJ (Opcional)

## Passo a passo

### 1. Criar o arquivo `.env`

Na raiz do projeto, crie um arquivo chamado `.env` com o seguinte conteúdo:

```env
DB_PASSWORD=123
JWT_SECRET=sua-chave-secreta-longa
chaveApi=sua-chave-da-openrouter
```

> `DB_PASSWORD` deve ser `123` para bater com o `docker-compose.yml`.  
> `JWT_SECRET` pode ser qualquer string longa.  
> `chaveApi` é a sua chave da API do OpenRouter.

---

### 2. Subir o banco de dados com Docker

```shell
sudo docker compose up -d
```

Para verificar que o container está rodando:

```shell
sudo docker ps
```

Para derrubar o container:
```shell
sudo docker compose down
```

---

### 3. Compilar e rodar a aplicação

```shell
sudo chmod +x mvnw
sudo export $(cat .env | xargs) && ./mvnw spring-boot:run
```

A aplicação vai iniciar na porta **8080**.

---

### 4. Registrar um usuário (Postman)

**POST** `http://localhost:8080/auth/register`

- Em **Body → raw → JSON**:

```json
{
  "login": "seu_email@exemplo.com",
  "senha": "sua_senha",
  "cpf": "12345678900"
}
```

Resposta esperada: `Resgistrado com sucesso`

---

### 5. Fazer login e obter o token (Postman)

**POST** `http://localhost:8080/auth/login`

- Em **Body → raw → JSON**:

```json
{
  "login": "seu_email@exemplo.com",
  "senha": "sua_senha"
}
```

A resposta será o **token JWT**. Copie esse token.

---

### 6. Usar o token nas requisições

Em qualquer outra requisição no Postman, vá em:

**Authorization → Bearer Token** → cole o token recebido no login.

---

### 7. Acessar o frontend

Abra o arquivo `index.html` na raiz do projeto diretamente no navegador:

```shell
sudo xdg-open index.html
```

Ou dê dois cliques no arquivo pelo gerenciador de arquivos.
