📋  Pré-requisitos

    Para executar com Maven:

        Java 17 ou superior

        Maven 3.6+

        PostgreSQL

    Para executar com Docker:

        Docker

        Docker Compose

🚀 Execução do Projeto

Método 1: Execução com Maven
```
Configure o arquivo application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/compridb
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```
Metodo 2: Execute o backend:
1) Crie o .env na pasta: src/main/resources/;
2) Use o .env-example, como exemplo, e preencha com suas variáveis de ambiente;
3) Crie seu banco de dados no Postgres, com os dados correspondentes as suas variáveis de ambiente;
4) Na pasta "backend", rode: `mvn spring-boot:run`
5) Forma alternativa: use o botão de "Run" do arquivo principal: src/main/java/com/codifica/compti/ComptiApplication.java usando a IDE do Intellij
6) Pronto! Seu projeto estará rodando em: http://localhost:8080