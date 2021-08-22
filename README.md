
# Sobre a aplicação - sboot-clientes (REST API)
Esta aplicação é responsável pelo cadastro, consulta e manutenção dos dados do cliente.


## Tecnologias utilizadas
Este projeto foi desenvolvido com Java 11 e Spring Boot 2 - também foi utilizado o MySQL para persistência dos dados.


## O modelo

Detalhes da tabela "cliente":
| campo | tipo |
| ------ | ------ |
| id | bigint not null primary key auto_increment |
| documento | varchar(14) not null |
| nome | varchar(255) not null |
| email | varchar(255) |
| endereco | varchar(255) |
| tipo_documento | varchar(4) not null  |


## Sobre o diretório /mysql
O diretório "/mysql" presente na raiz do projeto possui 2 sub-diretórios.
* (initdb) Contém um arquivo "dbclientes.sql" para criação da tabela e inserção da carga inicial dos dados.
* (storage) Poderá ser referenciado como "volume" pelo container do MySQL.


## Sobre o Dockerfile
O arquivo Dockerfile presente na raiz do projeto é responsável pela criação da "imagem" a ser utilizada pelo docker para instanciar o container da aplicação (Java/Spring-boot). Este arquivo está sendo referenciado pelo "docker-compose.yml".


## Sobre o docker-compose.yml
O arquivo docker-compose.yml contém as configurações necessárias para construir e orquestrar os 2 containers (mysql_db, sboot-clientes) necessários para o funcionamento desta aplicação.


## Instalação

OS X & Linux & Windows:

```sh
mvn clean install
docker-compose up -d
```

## O end-point de consultas
Esta API expõe um endpoint dedicado às consultas relacionadas ao Cliente. Dentre as diversas possibilidades, podemos combinar critérios para filtragem por vários campos, ajustar a paginação e a ordenação dos dados.


Operadores utilizados nas consultas:
| operador | descrição |
| ------ | ------ |
| : | Testa a "presença do valor" em campos do tipo Strings (LIKE %valor%) e igualdade para os demais tipos |
| < | Testa valores "menor que" o informado |
| > | Testa valores "maior que" o informado |


Exemplo de consultas:
| query | descrição |
| ------ | ------ |
| /v1/clientes | Sem critérios de filtragem - Retorna dados com paginação e ordenação padrão |
| /v1/clientes?search=nome:carvalho | Retorna os registros contendo a palavra "carvalho" em qualquer parte do nome |
| /v1/clientes?search=id>1,id<3 | Retorna os registros contendo ID maior que 1 (um) e menor que 3 (três) |
| /v1/clientes?page=0&size=10 | Realiza a 1ª página contendo os 10 primeiros registros |
| /v1/clientes?sort=id,desc | Realiza a ordenação descendente dos registros através do ID |
| ------ | ------ |
| /v1/clientes?search?nome:ME,id<5,email:gmail&page=1&size=5&sort=id,desc | Exemplo de combinação entre filtros, paginação e ordenação |


## Testando a aplicação através do Swagger-UI
Esta aplicação pode ser testada a partir da interface (Swagger UI): [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


## Testando a aplicação através do Postman
Existe um diretório "/postman" na raiz do projeto, contendo uma coleção do Postman com as chamadas aos end-points da API.

