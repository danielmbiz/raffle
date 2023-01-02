# API Rifa Online

API que simula o cadastro de sorteio de rifas.
É utilizado o OpenFeign para buscar informações de endereço do cliente a partir do CEP na API do ViaCep https://viacep.com.br/

# Seções

[Clientes](#Clientes)

# Tecnologia utilizada
* Java 17
* Spring Boot 2.7.7
* Spring Cloud OpenFeign
* JUnit
* Mockito
* API REST
* PostgreSQL (Container)
* Docker
* docker-compose

# Clientes

### POST - Incluir Novo Cliente

* /clients

 #### Body Request
```
{
    "name": "Francisco",
    "cpf": "88888888888",
    "email":"francisco@gmail.com",
    "cel": "99 9 9999-9999",
    "postCode": "88840000"
}
```

#### Body Response
```
{
  "id": 10,
  "name": "Francisco",
  "cpf": "88888888888",
  "email": "francisco@gmail.com",
  "cel": "99 9 9999-9999",
  "postCode": "88840-000",
  "city": "Urussanga",
  "ibgeCity": "4219002",
  "state": "SC"
}
```

### GET - Lista Cliente por ID

* /clients/{id}

#### Body Response
```
{
  "id": 1,
  "name": "Daniel",
  "cpf": "05386026941",
  "email": "daniel@daniel.com",
  "cel": "99999",
  "postCode": "88840-000",
  "city": "Urussanga",
  "ibgeCity": "4219002",
  "state": "SC"
}
```

### GET - Listar Todos os Clientes

* /clients

#### Body Response
```
[
  {
    "id": 1,
    "name": "Daniel",
    "cpf": "99999999999",
    "email": "daniel@gmail.com",
    "cel": "99999",
    "postCode": "88840-000",
    "city": "Urussanga",
    "ibgeCity": "4219002",
    "state": "SC"
  },
  {
    "id": 3,
    "name": "João",
    "cpf": "99999999999",
    "email": "joao@gmail.com",
    "cel": "99999",
    "postCode": "88840-000",
    "city": "Urussanga",
    "ibgeCity": "4219002",
    "state": "SC"
  },
  {
    "id": 4,
    "name": "Pedro",
    "cpf": "99999999999",
    "email": "pedro@gmail.com",
    "cel": "99999",
    "postCode": "88840-000",
    "city": "Urussanga",
    "ibgeCity": "4219002",
    "state": "SC"
  },
  {
    "id": 5,
    "name": "Maria",
    "cpf": "99999999999",
    "email": "maria@gmail.com",
    "cel": "99999",
    "postCode": "88840-000",
    "city": "Urussanga",
    "ibgeCity": "4219002",
    "state": "SC"
  },
  {
    "id": 6,
    "name": "Daniel",
    "cpf": "99999999999",
    "email": "daniel@gmail.com",
    "cel": "(99) 9 - 9999-9999",
    "postCode": "88840-000",
    "city": "Urussanga",
    "ibgeCity": "4219002",
    "state": "SC"
  }
]
```

### PUT - Alterar Cliente

* /clients/{id}

#### Body Request
```
{
    "name": "Francisco",
    "cpf": "99999999999",
    "email":"francisco@gmail.com",
    "cel": "99 9 9999-9999",
    "postCode": "88840000"
}
```

#### Body Response
```
{
  "id": 10,
  "name": "Francisco",
  "cpf": "99999999999",
  "email": "francisco@gmail.com",
  "cel": "99 9 9999-9999",
  "postCode": "88840-000",
  "city": "Urussanga",
  "ibgeCity": "4219002",
  "state": "SC"
}
```

### DELETE - Excluir Cliente

* /clients/{id}

#### Body Response
```
Status: 204 - No content
```