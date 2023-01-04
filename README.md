# API Rifa Online

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

API que simula o cadastro de sorteio de rifas.
É utilizado o OpenFeign para buscar informações de endereço do cliente a partir do CEP na API do ViaCep https://viacep.com.br/

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

# Seções

* [Clientes](#Clientes)
* * [Incluir Novo Clientes](#POST---Incluir-Novo-Cliente)
* * [GET - Lista Cliente por ID](#GET---Lista-Cliente-por-ID)
* * [GET - Lista Todos os Clientes](#GET---Listar-Todos-os-Clientes)
* * [PUT - ALterar Cliente](#PUT---Alterar-Cliente)
* * [DELETE - Excluir Cliente](#DELETE---Excluir-Cliente)
* [Rifa](#Rifa)
* * [POST - Incluir Nova Rifa](#POST---Incluir-Nova-Rifa)
* * [GET - Lista Rifa por ID](#GET---Lista-Rifa-por-ID)
* * [GET - Listar Todas as Rifas](#GET---Listar-Todas-as-Rifas)
* * [PUT - Alterar Rifa](#PUT---Alterar-Rifa)
* [DELETE - Excluir Rifa](#DELETE---Excluir-Rifa)
* [Prêmios da Rifa](#Prêmios-da-Rifa)
* * [POST - Incluir Novo Prêmio da Rifa](#POST---Incluir-Novo-Prêmio-da-Rifa)
* * [GET - Listar Prêmio da Rifa por ID](#GET---Lista-Prêmio-da-Rifa-por-ID)
* * [GET - Listar Prêmio da Rifa pela Rifa](#GET---Listar-Prêmios-da-Rifas-pela-Rifa)
* * [GET - Listar Todos os Prêmios da Rifa](#GET---Listar-Todos-os-Prêmios-da-Rifas)
* * [PUT - Alterar Prêmio da Rifa](#PUT---Alterar-Prêmio-da-Rifa)
* * [DELETE - Excluir Prêmio da Rifa](#DELETE---Excluir-Prêmio-da-Rifa)
* [Itens da Rifa](#Itens-da-Rifa)
* * [POST - Incluir Novo Item da Rifa](#POST---Incluir-Novo-Item-da-Rifa)
* * [GET - Lista Item da Rifa por ID](#GET---Lista-Item-da-Rifa-por-ID)
* * [GET - Listar Itens da Rifa pela Rifa](#GET---Listar-Itens-da-Rifas-pela-Rifa)
* * [GET - Listar Todos os Itens da Rifa](#GET---Listar-Todos-os-Itens-da-Rifas)
* * [DELETE - Excluir Item da Rifa](#DELETE---Excluir-Item-da-Rifa)
* [Ganhadores da Rifa](#Ganhadores-da-Rifa)
* * [POST - Novo Sorteio da Rifa](#POST---Novo-Sorteio-da-Rifa)
* * [GET - Listar Ganhadores da Rifa pela Rifa](#GET---Listar-Ganhadores-da-Rifas-pela-Rifa)
* * [DELETE - Excluir Ganhadores da Rifa](#DELETE---Excluir-Ganhador-da-Rifa)

# Clientes

### POST - Incluir Novo Cliente

* /clients

 #### Body Request
```
{
    "name": "Francisco",
    "cpf": "653.764.650-20",
    "email":"francisco@gmail.com",
    "cel": "99 9 9999-9999",
    "postCode": "88840000"
}
```

#### Body Response
```
{
    "id": 1,
    "name": "Francisco",
    "cpf": "653.764.650-20",
    "email": "francisco@gmail.com",
    "cel": "99 9 9999-9999",
    "postCode": "88840-000",
    "city": "Urussanga",
    "ibgeCity": "4219002",
    "state": "SC"
}
```
#### Status Response
```
Status: 201 - Created
Status: 400 - Bad Request
```

### GET - Lista Cliente por ID

* /clients/{id}

#### Body Response
```
{
    "id": 1,
    "name": "Francisco",
    "cpf": "653.764.650-20",
    "email": "francisco@gmail.com",
    "cel": "99 9 9999-9999",
    "postCode": "88840-000",
    "city": "Urussanga",
    "ibgeCity": "4219002",
    "state": "SC"
}
```
#### Status Response
```
Status: 200 - OK
Status: 404 - Not Found
```

### GET - Listar Todos os Clientes

* /clients

#### Body Response
```
[
    {
        "id": 1,
        "name": "Francisco",
        "cpf": "653.764.650-20",
        "email": "francisco@gmail.com",
        "cel": "99 9 9999-9999",
        "postCode": "88840-000",
        "city": "Urussanga",
        "ibgeCity": "4219002",
        "state": "SC"
    },
    {
        "id": 3,
        "name": "Daniel",
        "cpf": "80047164077",
        "email": "daniel@gmail.com",
        "cel": "99 9 9999-9998",
        "postCode": "88840-000",
        "city": "Urussanga",
        "ibgeCity": "4219002",
        "state": "SC"
    }
]
```
#### Status Response
```
Status: 200 - OK
```

### PUT - Alterar Cliente

* /clients/{id}

#### Body Request
```
{
    "name": "Francisco",
    "cpf": "37500426089",
    "email":"francisco@gmail.com",
    "cel": "99 9 9999-9999",
    "postCode": "88840000"
}
```

#### Body Response
```
{
    "id": 1,
    "name": "Francisco",
    "cpf": "37500426089",
    "email": "francisco@gmail.com",
    "cel": "99 9 9999-9999",
    "postCode": "88840-000",
    "city": "Urussanga",
    "ibgeCity": "4219002",
    "state": "SC"
}
```
#### Status Response
```
Status: 201 - Created
Status: 400 - Bad Request
```

### DELETE - Excluir Cliente

* /clients/{id}

#### Status Response
```
Status: 204 - No content
```

# Rifa

### POST - Incluir Nova Rifa

* /raffles

#### Body Request
```
{
    "description" : "Rifa solidaria ajudar pessoas necessitadas",
    "dateAward" : "2023-04-20",
    "type" : "SOLD",
    "tickets" : 50,
    "price" : 10.0,
    "status" : "OPEN"
}
```

#### Body Response
```
{
    "id": 1,
    "description": "Rifa solidaria ajudar pessoas necessitadas",
    "dateAward": "2023-04-20",
    "type": "SOLD",
    "tickets": 50,
    "price": 10.0,
    "status": "OPEN"
}
```
#### Status Response
```
Status: 201 - Created
Status: 400 - Bad Request
```

### GET - Lista Rifa por ID

* /raffles/{id}

#### Body Response
```
{
    "id": 1,
    "description": "Rifa solidaria ajudar pessoas necessitadas",
    "dateAward": "2023-04-20",
    "type": "SOLD",
    "tickets": 50,
    "price": 10.0,
    "status": "OPEN"
}
```
#### Status Response
```
Status: 200 - OK
Status: 404 - Not Found
```

### GET - Listar Todas as Rifas

* /raffles

#### Body Response
```
[
    {
        "id": 1,
        "description": "Rifa solidaria ajudar pessoas necessitadas",
        "dateAward": "2023-04-20",
        "type": "SOLD",
        "tickets": 50,
        "price": 10.0,
        "status": "OPEN"
    },
    {
        "id": 2,
        "description": "Rifa solidaria ajudar pessoas sem teto",
        "dateAward": "2023-06-01",
        "type": "SOLD",
        "tickets": 500,
        "price": 25.0,
        "status": "OPEN"
    }
]
```
#### Status Response
```
Status: 20 - OK
```

### PUT - Alterar Rifa

* /raffles/{id}

#### Body Request
```
{
    "description" : "Rifa solidaria ajudar pessoas necessitadas",
    "dateAward" : "2023-04-20",
    "type" : "SOLD",
    "tickets" : 50,
    "price" : 20.0,
    "status" : "OPEN"
}
```

#### Body Response
```
{
    "id": 1,
    "description": "Rifa solidaria ajudar pessoas necessitadas",
    "dateAward": "2023-04-20",
    "type": "SOLD",
    "tickets": 50,
    "price": 20.0,
    "status": "OPEN"
}
```
#### Status Response
```
Status: 201 - Created
Status: 400 - Bad Request
```

### DELETE - Excluir Rifa 

* /raffle/{id}

#### Status Response
```
Status: 204 - No content
Status: 404 - Not Found
```

# Prêmios da Rifa

### POST - Incluir Novo Prêmio da Rifa

* /raffles/awards

#### Body Request
```
{
    "raffleId" : 1,
    "description" : "Prêmio de R$500 em dinheiro",
    "cost" : 250.0
}
```

#### Body Response
```
{
    "id": 1,
    "raffle": {
        "id": 1,
        "description": "Rifa solidaria ajudar pessoas necessitadas",
        "dateAward": "2023-04-20",
        "type": "SOLD",
        "tickets": 50,
        "price": 20.0,
        "status": "OPEN"
    },
    "description": "Prêmio de R$500 em dinheiro",
    "cost": 250.0
}
```
#### Status Response
```
Status: 201 - Created
Status: 400 - Bad Request
```

### GET - Lista Prêmio da Rifa por ID

* /raffles/awards/{id}

#### Body Response
```
{
    "id": 1,
    "raffle": {
        "id": 1,
        "description": "Rifa solidaria ajudar pessoas necessitadas",
        "dateAward": "2023-04-20",
        "type": "SOLD",
        "tickets": 50,
        "price": 20.0,
        "status": "OPEN"
    },
    "description": "Prêmio de R$500 em dinheiro",
    "cost": 250.0
}
```
#### Status Response
```
Status: 200 - OK
Status: 404 - Not Found
```

### GET - Listar Prêmios da Rifas pela Rifa

* /raffles/awards/raffle/{idRaffle}

#### Body Response
```
[
    {
        "id": 1,
        "raffle": {
            "id": 1,
            "description": "Rifa solidaria ajudar pessoas necessitadas",
            "dateAward": "2023-04-20",
            "type": "SOLD",
            "tickets": 50,
            "price": 20.0,
            "status": "OPEN"
        },
        "description": "Prêmio de R$500 em dinheiro",
        "cost": 250.0
    },
    {
        "id": 2,
        "raffle": {
            "id": 1,
            "description": "Rifa solidaria ajudar pessoas necessitadas",
            "dateAward": "2023-04-20",
            "type": "SOLD",
            "tickets": 50,
            "price": 20.0,
            "status": "OPEN"
        },
        "description": "Prêmio de R$1.000 em dinheiro",
        "cost": 500.0
    }
]
```
#### Status Response
```
Status: 200 - OK
Status: 400 - Bad Request
```

### GET - Listar Todos os Prêmios da Rifas

* /raffles/awards

#### Body Response
```
[
    {
        "id": 1,
        "raffle": {
            "id": 1,
            "description": "Rifa solidaria ajudar pessoas necessitadas",
            "dateAward": "2023-04-20",
            "type": "SOLD",
            "tickets": 50,
            "price": 20.0,
            "status": "OPEN"
        },
        "description": "Prêmio de R$500 em dinheiro",
        "cost": 250.0
    },
    {
        "id": 2,
        "raffle": {
            "id": 1,
            "description": "Rifa solidaria ajudar pessoas necessitadas",
            "dateAward": "2023-04-20",
            "type": "SOLD",
            "tickets": 50,
            "price": 20.0,
            "status": "OPEN"
        },
        "description": "Prêmio de R$1.000 em dinheiro",
        "cost": 500.0
    }
]
```
#### Status Response
```
Status: 200 - OK
```

### PUT - Alterar Prêmio da Rifa

* /raffles/awards/{id}

#### Body Request
```
{
    "raffleId" : 3,
    "description" : "Prêmio de R$500 em dinheiro",
    "cost" : 400.0
}
```

#### Body Response
```
{
  "id": 11,
  "raffle": {
    "id": 3,
    "description": "Rifa para ajudar pessoas sem teto",
    "dateAward": "2023-04-20",
    "type": "SOLD",
    "tickets": 500,
    "price": 10,
    "status": "OPEN"
  },
  "description": "Prêmio de R$500 em dinheiro",
  "cost": 400
}
```

### DELETE - Excluir Prêmio da Rifa

* /raffle/awards/{id}

#### Body Response
```
Status: 204 - No content
```

# Itens da Rifa

### POST - Incluir Novo Item da Rifa

* /raffles/items

#### Body Request
```
{
    "raffleId" : 3,
    "clientId" : 1,
    "ticket" : 50
}
```

#### Body Response
```
{
  "id": 11,
  "raffle": {
    "id": 3,
    "description": "Rifa para ajudar pessoas sem teto",
    "dateAward": "2023-04-20",
    "type": "SOLD",
    "tickets": 500,
    "price": 10,
    "status": "OPEN"
  },
  "client": {
    "id": 1,
    "name": "Daniel",
    "cpf": "99999999999",
    "email": "daniel@daniel.com",
    "cel": "99999",
    "postCode": "88840-000",
    "city": "Urussanga",
    "ibgeCity": "4219002",
    "state": "SC"
  },
  "ticket": 50
}
```

### GET - Lista Item da Rifa por ID

* /raffles/items/{id}

#### Body Response
```
{
  "id": 7,
  "raffle": {
    "id": 1,
    "description": "Rifa para ajudar pessoas sem teto",
    "dateAward": "2023-06-20",
    "type": "SOLD",
    "tickets": 500,
    "price": 10,
    "status": "OPEN"
  },
  "client": {
    "id": 3,
    "name": "João",
    "cpf": "42342342",
    "email": "joao@daniel.com",
    "cel": "99999",
    "postCode": "88840-000",
    "city": "Urussanga",
    "ibgeCity": "4219002",
    "state": "SC"
  },
  "ticket": 26
}
```

### GET - Listar Itens da Rifas pela Rifa

* /raffles/items/raffle/{idRaffle}

#### Body Response
```
[
  {
    "id": 1,
    "raffle": {
      "id": 1,
      "description": "Rifa para ajudar pessoas sem teto",
      "dateAward": "2023-06-20",
      "type": "SOLD",
      "tickets": 500,
      "price": 10,
      "status": "OPEN"
    },
    "client": {
      "id": 1,
      "name": "Daniel",
      "cpf": "99999999999",
      "email": "daniel@daniel.com",
      "cel": "99999",
      "postCode": "88840-000",
      "city": "Urussanga",
      "ibgeCity": "4219002",
      "state": "SC"
    },
    "ticket": 42
  },
  {
    "id": 6,
    "raffle": {
      "id": 1,
      "description": "Rifa para ajudar pessoas sem teto",
      "dateAward": "2023-06-20",
      "type": "SOLD",
      "tickets": 500,
      "price": 10,
      "status": "OPEN"
    },
    "client": {
      "id": 1,
      "name": "Daniel",
      "cpf": "99999999999",
      "email": "daniel@daniel.com",
      "cel": "99999",
      "postCode": "88840-000",
      "city": "Urussanga",
      "ibgeCity": "4219002",
      "state": "SC"
    },
    "ticket": 48
  },
  {
    "id": 7,
    "raffle": {
      "id": 1,
      "description": "Rifa para ajudar pessoas sem teto",
      "dateAward": "2023-06-20",
      "type": "SOLD",
      "tickets": 500,
      "price": 10,
      "status": "OPEN"
    },
    "client": {
      "id": 3,
      "name": "João",
      "cpf": "42342342",
      "email": "joao@daniel.com",
      "cel": "99999",
      "postCode": "88840-000",
      "city": "Urussanga",
      "ibgeCity": "4219002",
      "state": "SC"
    },
    "ticket": 26
  },
  {
    "id": 8,
    "raffle": {
      "id": 1,
      "description": "Rifa para ajudar pessoas sem teto",
      "dateAward": "2023-06-20",
      "type": "SOLD",
      "tickets": 500,
      "price": 10,
      "status": "OPEN"
    },
    "client": {
      "id": 3,
      "name": "João",
      "cpf": "42342342",
      "email": "joao@daniel.com",
      "cel": "99999",
      "postCode": "88840-000",
      "city": "Urussanga",
      "ibgeCity": "4219002",
      "state": "SC"
    },
    "ticket": 14
  },
  {
    "id": 10,
    "raffle": {
      "id": 1,
      "description": "Rifa para ajudar pessoas sem teto",
      "dateAward": "2023-06-20",
      "type": "SOLD",
      "tickets": 500,
      "price": 10,
      "status": "OPEN"
    },
    "client": {
      "id": 3,
      "name": "João",
      "cpf": "42342342",
      "email": "joao@daniel.com",
      "cel": "99999",
      "postCode": "88840-000",
      "city": "Urussanga",
      "ibgeCity": "4219002",
      "state": "SC"
    },
    "ticket": 50
  }
]
```

### GET - Listar Todos os Itens da Rifas

* /raffles/items

#### Body Response
```
[
  {
    "id": 1,
    "raffle": {
      "id": 1,
      "description": "Rifa para ajudar pessoas sem teto",
      "dateAward": "2023-06-20",
      "type": "SOLD",
      "tickets": 500,
      "price": 10,
      "status": "OPEN"
    },
    "client": {
      "id": 1,
      "name": "Daniel",
      "cpf": "99999999999",
      "email": "daniel@daniel.com",
      "cel": "99999",
      "postCode": "88840-000",
      "city": "Urussanga",
      "ibgeCity": "4219002",
      "state": "SC"
    },
    "ticket": 42
  },
  {
    "id": 6,
    "raffle": {
      "id": 1,
      "description": "Rifa para ajudar pessoas sem teto",
      "dateAward": "2023-06-20",
      "type": "SOLD",
      "tickets": 500,
      "price": 10,
      "status": "OPEN"
    },
    "client": {
      "id": 1,
      "name": "Daniel",
      "cpf": "99999999999",
      "email": "daniel@daniel.com",
      "cel": "99999",
      "postCode": "88840-000",
      "city": "Urussanga",
      "ibgeCity": "4219002",
      "state": "SC"
    },
    "ticket": 48
  },
  {
    "id": 7,
    "raffle": {
      "id": 1,
      "description": "Rifa para ajudar pessoas sem teto",
      "dateAward": "2023-06-20",
      "type": "SOLD",
      "tickets": 500,
      "price": 10,
      "status": "OPEN"
    },
    "client": {
      "id": 3,
      "name": "João",
      "cpf": "42342342",
      "email": "joao@daniel.com",
      "cel": "99999",
      "postCode": "88840-000",
      "city": "Urussanga",
      "ibgeCity": "4219002",
      "state": "SC"
    },
    "ticket": 26
  },
  {
    "id": 8,
    "raffle": {
      "id": 1,
      "description": "Rifa para ajudar pessoas sem teto",
      "dateAward": "2023-06-20",
      "type": "SOLD",
      "tickets": 500,
      "price": 10,
      "status": "OPEN"
    },
    "client": {
      "id": 3,
      "name": "João",
      "cpf": "42342342",
      "email": "joao@daniel.com",
      "cel": "99999",
      "postCode": "88840-000",
      "city": "Urussanga",
      "ibgeCity": "4219002",
      "state": "SC"
    },
    "ticket": 14
  },
  {
    "id": 10,
    "raffle": {
      "id": 1,
      "description": "Rifa para ajudar pessoas sem teto",
      "dateAward": "2023-06-20",
      "type": "SOLD",
      "tickets": 500,
      "price": 10,
      "status": "OPEN"
    },
    "client": {
      "id": 3,
      "name": "João",
      "cpf": "42342342",
      "email": "joao@daniel.com",
      "cel": "99999",
      "postCode": "88840-000",
      "city": "Urussanga",
      "ibgeCity": "4219002",
      "state": "SC"
    },
    "ticket": 50
  },
  {
    "id": 11,
    "raffle": {
      "id": 3,
      "description": "Rifa para ajudar pessoas sem teto",
      "dateAward": "2023-04-20",
      "type": "SOLD",
      "tickets": 500,
      "price": 10,
      "status": "OPEN"
    },
    "client": {
      "id": 1,
      "name": "Daniel",
      "cpf": "99999999999",
      "email": "daniel@daniel.com",
      "cel": "99999",
      "postCode": "88840-000",
      "city": "Urussanga",
      "ibgeCity": "4219002",
      "state": "SC"
    },
    "ticket": 50
  }
]
```

### DELETE - Excluir Item da Rifa

* /raffle/items/{id}

#### Body Response
```
Status: 204 - No content
```

# Ganhadores da Rifa

### POST - Novo Sorteio da Rifa

O sorteio é realizado de acordo com a quantidade de prêmio cadastrados na Rifa.

* /raffles/winners/{id}

#### Body Request
```
[
  {
    "id": 7,
    "raffleItem": {
      "id": 6,
      "raffle": {
        "id": 1,
        "description": "Rifa para ajudar pessoas sem teto",
        "dateAward": "2023-06-20",
        "type": "SOLD",
        "tickets": 500,
        "price": 10,
        "status": "CLOSE"
      },
      "client": {
        "id": 1,
        "name": "Daniel",
        "cpf": "99999999999",
        "email": "daniel@daniel.com",
        "cel": "99999",
        "postCode": "88840-000",
        "city": "Urussanga",
        "ibgeCity": "4219002",
        "state": "SC"
      },
      "ticket": 48
    },
    "raffleAward": {
      "id": 2,
      "description": "R$800 em dinheiro",
      "cost": 400
    }
  },
  {
    "id": 8,
    "raffleItem": {
      "id": 8,
      "raffle": {
        "id": 1,
        "description": "Rifa para ajudar pessoas sem teto",
        "dateAward": "2023-06-20",
        "type": "SOLD",
        "tickets": 500,
        "price": 10,
        "status": "CLOSE"
      },
      "client": {
        "id": 3,
        "name": "João",
        "cpf": "42342342",
        "email": "joao@daniel.com",
        "cel": "99999",
        "postCode": "88840-000",
        "city": "Urussanga",
        "ibgeCity": "4219002",
        "state": "SC"
      },
      "ticket": 14
    },
    "raffleAward": {
      "id": 12,
      "description": "Prêmio de R$500 em dinheiro",
      "cost": 250
    }
  },
  {
    "id": 9,
    "raffleItem": {
      "id": 10,
      "raffle": {
        "id": 1,
        "description": "Rifa para ajudar pessoas sem teto",
        "dateAward": "2023-06-20",
        "type": "SOLD",
        "tickets": 500,
        "price": 10,
        "status": "CLOSE"
      },
      "client": {
        "id": 3,
        "name": "João",
        "cpf": "42342342",
        "email": "joao@daniel.com",
        "cel": "99999",
        "postCode": "88840-000",
        "city": "Urussanga",
        "ibgeCity": "4219002",
        "state": "SC"
      },
      "ticket": 50
    },
    "raffleAward": {
      "id": 13,
      "description": "Prêmio de R$200 em dinheiro",
      "cost": 250
    }
  }
]
```

#### Body Response
```
{
  "id": 11,
  "raffle": {
    "id": 3,
    "description": "Rifa para ajudar pessoas sem teto",
    "dateAward": "2023-04-20",
    "type": "SOLD",
    "tickets": 500,
    "price": 10,
    "status": "OPEN"
  },
  "client": {
    "id": 1,
    "name": "Daniel",
    "cpf": "99999999999",
    "email": "daniel@daniel.com",
    "cel": "99999",
    "postCode": "88840-000",
    "city": "Urussanga",
    "ibgeCity": "4219002",
    "state": "SC"
  },
  "ticket": 50
}
```

### GET - Listar Ganhadores da Rifas pela Rifa

* /raffles/winners/raffle/{idRaffle}

#### Body Response
```
{
  "id": 7,
  "raffle": {
    "id": 1,
    "description": "Rifa para ajudar pessoas sem teto",
    "dateAward": "2023-06-20",
    "type": "SOLD",
    "tickets": 500,
    "price": 10,
    "status": "OPEN"
  },
  "client": {
    "id": 3,
    "name": "João",
    "cpf": "42342342",
    "email": "joao@daniel.com",
    "cel": "99999",
    "postCode": "88840-000",
    "city": "Urussanga",
    "ibgeCity": "4219002",
    "state": "SC"
  },
  "ticket": 26
}
```

### DELETE - Excluir Ganhador da Rifa

* /raffle/items/{id}

#### Body Response
```
Status: 204 - No content
```