# IBM (Server - Backend)

Este projeto foi criado utilizando SpringBoot (3.2.5) e Java 17.

# Banco de Dados

Foi utilizado o banco PostgreSQL (16.3)
Deve atualizar o arquivo 'application.properties' no diretório 'src/main/resources' para que o mesmo reflita as informações utilizadas na instalação/criação da nova base de dados.

```
spring.datasource.url=jdbc:postgresql://localhost:5432/nome_sua_base
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

# Executar

'Rodar' o arquivo 'ServerApplication.java'.
'Rodar' o projeto em conjunto com o projeto 'FE' (https://github.com/guilucon/IBMBank-FE);
