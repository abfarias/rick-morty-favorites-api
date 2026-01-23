## Visão geral do projeto

O projeto consiste no desenvolvimento de uma API RESTful em Java com Quarkus que consome a API pública **Rick and Morty** para complementar informações dos recursos da aplicação.

A aplicação mantém apenas os dados próprios do domínio, como personagens favoritos, notas e comentários, enquanto os dados oficiais dos personagens são obtidos da API externa e armazenados temporariamente em cache, reduzindo chamadas externas e melhorando o desempenho.

A estratégia de cache adotada é baseada em tempo de vida (**TTL**), aplicada exclusivamente aos dados externos, mantendo a aplicação *stateless* e respeitando os princípios REST.

## API pública utilizada

- **Rick and Morty API**  
  https://rickandmortyapi.com/api

## Recurso principal

### Personagem Favorito

Mantém dados próprios:

- ID do personagem externo
- Nota
- Comentário

Os dados oficiais do personagem (nome, status, espécie etc.) são obtidos da API externa.

## Estratégia global de tratamento de erros

A API trata explicitamente os erros provenientes de:

- Validações internas
- Banco de dados
- API pública externa

### Mapeamento geral de erros HTTP

| Situação                     | Código HTTP               |
|------------------------------|---------------------------|
| Recurso não encontrado       | 404 Not Found             |
| Erro de validação            | 400 Bad Request           |
| Recurso já favoritado        | 409 Conflict              |
| API externa indisponível     | 503 Service Unavailable   |
| Erro inesperado              | 500 Internal Server Error |

Os erros são retornados em formato JSON padronizado.

## Endpoints da API

### 1. Listar personagens *(consome API pública)*
Responsável: Arthur

**Rick and Morty API**

```http
GET https://rickandmortyapi.com/api/character?page=1
```

**Nossa API**

```http
GET /api/v1/personagens
```

**Request**

```http
GET /api/v1/personagens?page=1
```

**Response 200**

```json
{
  "page": 1,
  "results": [
    {
      "id": 1,
      "name": "Rick Sanchez",
      "status": "Alive",
      "species": "Human"
    },
    //...
  ]
}
```

#### Estratégia de cache

- Cache da lista de personagens por página
- Chave: `character_page_{page}`
- TTL: **5 minutos**

#### Fluxo

- Verifica se a página está no cache
- Se estiver, retorna do cache
- Caso contrário, chama a API pública, salva no cache e retorna

#### Possíveis erros

| Situação                 | Código |
|--------------------------|--------|
| Página inválida          | 400    |
| API externa indisponível | 503    |
| Erro inesperado          | 500    |

---

### 2. Buscar personagem por ID *(consome API pública)*

Responsável: Edgar

**Rick and Morty API**

```http
GET https://rickandmortyapi.com/api/character/{id}
```

**Nossa API**

```http
GET /api/v1/personagens/{id}
```

**Request**

```http
GET /api/v1/personagens/1
```

**Response 200**

```json
{
  "id": 1,
  "name": "Rick Sanchez",
  "status": "Alive",
  "species": "Human"
}
```

#### Estratégia de cache

- Cache por personagem individual
- Chave: `character_{id}`
- TTL: **10 minutos**

#### Fluxo

- Busca o personagem no cache
- Se estiver, retorna
- Caso contrário, chama a API pública, salva no cache e retorna

#### Possíveis erros

| Situação                   | Código |
|----------------------------|--------|
| Personagem não encontrado  | 404    |
| API externa indisponível   | 503    |
| Erro inesperado            | 500    |

---

### 3. Favoritar um personagem

Responsável: Ronaldo

```http
POST /api/v1/personagens/favorito/{id}
```

**Request**

```http
POST /api/v1/personagens/favorito/1
Content-Type: application/json
```

```json
{
  "nota": 5,
  "comentario": "Personagem icônico"
}
```

**Response 201**

```json
{
  "idPersonagem": 1,
  "nota": 5,
  "comentario": "Personagem icônico"
}
```

#### Estratégia de cache

- Reutiliza o cache do endpoint de busca por ID (`character_{id}`)

#### Fluxo

- Verifica se o personagem está no cache
- Se não estiver:
    - Chama a API pública
    - Valida se o personagem existe
    - Salva no cache
- Salva no banco:
    - ID externo
    - Nota
    - Comentário

#### Possíveis erros

| Situação                             | Código |
|-------------------------------------|--------|
| Personagem não existe na API externa | 404    |
| Personagem já favoritado            | 409    |
| Dados inválidos                     | 400    |
| API externa indisponível            | 503    |
| Erro inesperado                     | 500    |

---

### 4. Listar personagens favoritos

Responsável: Cintia

```http
GET /api/v1/personagens/favoritos
```

**Request**

```http
GET /api/v1/personagens/favoritos
```

**Response 200**

```json
[
  {
    "idPersonagem": 1,
    "nota": 5,
    "comentario": "Personagem icônico",
    "name": "Rick Sanchez",
    "status": "Alive"
  }
]
```

#### Estratégia de cache

- Para cada favorito:
    - Busca dados do personagem no cache
    - Só chama a API pública se não estiver cacheado

#### Fluxo

- Busca os favoritos no banco de dados
- Para cada favorito:
    - Se estiver no cache, agrega dados
    - Caso contrário:
        - Chama a API externa
        - Valida existência
        - Salva no cache
        - Agrega dados
- Retorna a lista final

#### Possíveis erros

| Situação                           | Código |
|-----------------------------------|--------|
| Nenhum favorito encontrado        | 200 (lista vazia) |
| Personagem externo não encontrado | 404    |
| API externa indisponível          | 503    |
| Erro inesperado                   | 500    |

---

### 5. Atualizar favorito

Responsável: Ariel

```http
PUT /api/v1/personagens/favorito/{id}
```

**Request**

```http
PUT /api/v1/personagens/favorito/1
Content-Type: application/json
```

```json
{
  "nota": 4,
  "comentario": "Continua excelente"
}
```

**Response 200**

```json
{
  "idPersonagem": 1,
  "nota": 4,
  "comentario": "Continua excelente"
}
```

#### Fluxo

- Atualiza nota e comentário no banco
- Não altera dados da API externa

#### Possíveis erros

| Situação                | Código |
|-------------------------|--------|
| Favorito não encontrado | 404    |
| Dados inválidos         | 400    |
| Erro inesperado         | 500    |

---

### 6. Remover favorito
Responsável: Leonardo

```http
DELETE /api/v1/personagens/favorito/{id}
```

**Request**

```http
DELETE /api/v1/personagens/favorito/1
```

**Response 204**

Sem conteúdo no corpo da resposta.

#### Fluxo

- Remove o favorito do banco de dados

#### Possíveis erros

| Situação                | Código |
|-------------------------|--------|
| Favorito não encontrado | 404    |
| Erro inesperado         | 500    |
