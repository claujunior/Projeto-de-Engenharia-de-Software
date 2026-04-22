# Tutorial de testes Who is safe?

## Pré-requisitos
* Java 17+
* Postman (Para testar API)
* IntelliJ (Opcional)

## Passo a passo

1. Crie o arquivo .env com o seguinte conteúdo:
```shell 
DB_PASSWORD=123 #Para bater com o que está no docker-compose.yml 
JWT_SECRET=uma-chave-secreta-qualquer #Qualquer string longa
chaveApi=sua-chave-da-openrouter-aqui #Chave API do OpenRouter - Se não tiver, pode colocar qualquer valor e adicionar depois
```
2. Subir banco de dados com Docker
```shell
docker compose up -d
```
Vai criar um container chamado postgres-dev rodando na porta 5432 com:
→ Banco: meu_banco
→ Usuário: postgres
→ Senha: 123

Para verificar o que está rodando:
```shell
docker ps
```

Para derrubar o que está rodando:
```shell
docker compose down
```

3. Buildar e compilar o código com:
```shell
chmod +X mvnw
./mvnw spring-boot:run
```
4. Você receberá um token JWT na saída do terminal, adicionar o token recebido no Postman: Authorization → Bearer Token. Cole o token recebido pelo terminal

## Resumo
```shell
# 1. Entre na pasta do projeto
cd Projeto-de-Engenharia-de-Software

# 2. Crie o .env (edite com seus valores)
echo 'DB_PASSWORD=123
JWT_SECRET=minha-chave-super-secreta-2024
chaveApi=sua-chave-openrouter' > .env

# 3. Suba o banco
docker compose up -d

# 4. Rode a aplicação
chmod +x mvnw
export $(cat .env | xargs) && ./mvnw spring-boot:run
```
