
# Sobre a aplicação
Esta aplicação é um exemplo de API para cadastro, consulta e manutenção dos dados de clientes.


## Tecnologias utilizadas
Java 11, Spring Boot, Swagger-UI, MySQL e Docker.


## Sobre o modelo de dados
Seria interessante dividirmos as responsabilidades entre as entidades (Pessoa e Cliente) - mais, por hora, resolvi manter apenas uma tabela (Cliente).

Detalhes da tabela "cliente":
| campo | tipo |
| ------ | ------ |
| id | bigint not null primary key auto_increment |
| documento | varchar(14) not null |
| nome | varchar(255) not null |
| email | varchar(255) |
| endereco | varchar(255) |
| tipo_documento | varchar(4) not null  |


## Sobre o diretório "/mysql"
O diretório "/mysql" presente na raiz do projeto possui 2 sub-diretórios.
* (initdb) Contém um arquivo "dbclientes.sql" para criação da tabela e inserção da carga inicial dos dados.
* (storage) Poderá ser referenciado como "volume" pelo container do MySQL.

Obs: Os dados referentes a carga inicial do MySQL foram gerados no site [4Devs - Ferramentas online](https://www.4devs.com.br/gerador_de_pessoas).

## Sobre o arquivo "Dockerfile"
O arquivo "Dockerfile" presente na raiz do projeto é responsável pela criação da "imagem" a ser utilizada pelo docker para instanciar o container da aplicação (Java/Spring-boot). Este arquivo está sendo referenciado pelo "docker-compose.yml".


## Sobre o arquivo "docker-compose.yml"
O arquivo "docker-compose.yml" contém as configurações necessárias para construir e orquestrar os 2 containers (mysql_db, sboot-clientes) necessários para o funcionamento desta aplicação.


## Instalação

OS X & Linux & Windows:

```sh
mvn clean install
docker-compose up -d
```

## O end-point de consultas
Esta API expõe um endpoint dedicado às consultas relacionadas aos Clientes. Dentre as diversas possibilidades, podemos combinar critérios para filtragem por vários campos, ajustar a paginação e a ordenação dos dados.

### Parâmetros disponíveis
| parâmetro | obrigatório | valor padrão | descrição |
| ------ | ------ | ------ | ------ |
| search | Não | --- | Parâmetro de consulta personalizada. |
| page | Não | 0 (zero) | Número da página (zero para a primeira página). |
| size | Não | 10 (dez) | Quantidade de registros por página. |
| sort | Não | id,asc | Valor combinado para ordenação (campo/ direção). Ex: "nome,desc". |

### Operadores
| operador | descrição |
| ------ | ------ |
| : | Testa a "presença do valor" em campos do tipo String (LIKE %valor%) e igualdade para os demais tipos. |
| < | Testa valores "menor que" o informado. |
| > | Testa valores "maior que" o informado. |

### Exemplos de consulta
| consulta | descrição |
| ------ | ------ |
| /v1/clientes | Sem critérios de filtragem - Retorna os dados com paginação e ordenação padrão. |
| /v1/clientes?search=nome:ana | Retorna os registros contendo a palavra "ana" em qualquer parte do nome. |
| /v1/clientes?search=id>5,id<10 | Retorna os registros contendo ID maior que 5 (cinco) e menor que 10 (dez). |
| /v1/clientes?page=0&size=10 | Retorna a 1ª página contendo os 10 primeiros registros. |
| /v1/clientes?sort=id,desc | Realiza a ordenação descendente dos registros através do ID. |
| /v1/clientes?search=nome:pizzaria,email:calebe,id>10&page=0&size=10&sort=id,asc | Exemplo de combinação entre filtros, paginação e ordenação. |

### Exemplo de retorno da consulta
```json
{
  "_embedded": {
    "clienteVOList": [
      {
        "id": 21,
        "nome": "Calebe e Guilherme Pizzaria Delivery ME",
        "tipoDocumento": "CNPJ",
        "documento": "51492658000107",
        "email": "diretoria@calebepizzaria.com.br",
        "endereco": "Praça Júlio Rodrigues, 588 - Araras-SP",
        "_links": {
          "self": {
            "href": "http://localhost:8080/v1/clientes/21"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/v1/clientes?search=nome%3Apizzaria%2Cemail%3Acalebe%2Cid%3E10&page=0&size=10&sort=id,asc"
    }
  },
  "page": {
    "size": 10,
    "totalElements": 1,
    "totalPages": 1,
    "number": 0
  }
}
```

## Testando a aplicação através do Swagger-UI
Esta aplicação poderá ser testada a partir da interface (Swagger UI): [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


## Testando a aplicação através do Postman
Existe um diretório "/postman" na raiz do projeto, contendo uma coleção do Postman com a configuração das chamadas aos end-points expostos pela API.

