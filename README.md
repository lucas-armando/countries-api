# Countries API

API RESTful em **Java 17 + Spring Boot 3** para gerir países
(`id` gerado automaticamente, `name`, `capital`, `region`, `subRegion`, `area`).

Base de dados relacional: **H2** (modo ficheiro, em `./data/`). Para usar PostgreSQL/MySQL
basta trocar a dependência e as propriedades `spring.datasource.*` em `application.properties`.

## Executar
```bash
mvn spring-boot:run      # servidor em http://localhost:8080
mvn test                 # testes de integração
```

## Endpoints
| Método | URL | Descrição | Resposta |
|-------:|-----|-----------|----------|
| POST   | `/api/countries` | Criar país | 201 + `Location` |
| GET    | `/api/countries` | Listar (ordenável) | 200 |
| GET    | `/api/countries/{id}` | Obter um país | 200 / 404 |
| PUT    | `/api/countries/{id}` | Modificar país | 200 / 404 |
| DELETE | `/api/countries/{id}` | Eliminar país | 204 / 404 |

### Ordenação
`GET /api/countries?sortBy=<campo>&direction=<asc|desc>`
- `sortBy`: `id` (default), `name`, `capital`, `region`, `subRegion`, `area`
- `direction`: `asc` (default) ou `desc`
- Valores inválidos devolvem **400**.

## Exemplos
```bash
curl -X POST localhost:8080/api/countries -H "Content-Type: application/json" \
  -d '{"name":"Moçambique","capital":"Maputo","region":"Africa","subRegion":"Eastern Africa","area":801590}'

curl "localhost:8080/api/countries?sortBy=area&direction=desc"

curl -X PUT localhost:8080/api/countries/1 -H "Content-Type: application/json" \
  -d '{"name":"Moçambique","capital":"Maputo","region":"Africa","subRegion":"Eastern Africa","area":801590}'

curl -X DELETE localhost:8080/api/countries/1
```

## Estrutura
```
controller/  -> endpoints REST
service/     -> regras de negócio e ordenação
repository/  -> Spring Data JPA
model/       -> entidade Country
dto/         -> CountryRequest (validação) / CountryResponse
exception/   -> erros tratados globalmente (400/404)
```
