# Salutem desafio técnico - Backend

## 🔑 Endpoints

• Acessar ```customer```, ex:
```
• Endpoint: http://localhost:8080/customer

• Metodos: [GET, POST, PUT, DELETE]

• POST/PUT exemplo de corpo de requisição:
{
    "name": "customer_name",
    "address": "customer_address",
    "cell": "customer_cell"
}
```

• Acessar ```customer_order```, ex:
```
• Endpoint: http://localhost:8080/customer_order

• Metodos: [GET, POST, PUT, DELETE]

• POST/PUT exemplo de corpo de requisição:
{
	"code": "customer_order_code",
	"description": "customer_order_description",
	"created_at": "customer_order_created_at",
	"customer_id": "customer_id",
	"observations": ["customer_order_observation"],
	"hamburger_id": ["hamburger_id"],
	"drink_id": ["drink_id"],
	"additional": ["additional_id"]
}
```

• Acessar ```drink```, ex:
```
• Endpoint: http://localhost:8080/drink

• Metodos: [GET, POST, PUT, DELETE]

• POST/PUT exemplo de corpo de requisição:
{
	"code": "drink_code",
	"description": "drink_description",
	"unity_price": 00.00,
	"sugar_flag": "drink_sugar_flag"
}
```

• Acessar ```hamburger```, ex:
```
• Endpoint: http://localhost:8080/hamburger

• Metodos: [GET, POST, PUT, DELETE]

• POST/PUT exemplo de corpo de requisição:
{
  "code": "hamburger_code",
  "description": "hamburger_description",
  "unity_price": 00.00,
  "ingredients_id": [ "ingredients_id"]
}
```

• Acessar ```ingredient```, ex:
```
• Endpoint: http://localhost:8080/ingredient

• Metodos: [GET, POST, PUT, DELETE]

• POST/PUT exemplo de corpo de requisição:
{
	"code": "ingredient_code",
	"description": "ingredient_description",
	"unity_price": 00.00,
	"additional_flag": "ingredient_additional_flag("yes", "no")"
}
```
