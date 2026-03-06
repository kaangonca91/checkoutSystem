# Supermarket Checkout (Spring Boot)

## Run

```bash
mvn spring-boot:run
```

## Cart API

`POST /api/carts`

Request (preferred):

```json
{
  "lines": [
    { "productCode": "APPLE", "quantity": 2 },
    { "productCode": "BANANA", "quantity": 1 },
    { "productCode": "ORANGE", "quantity": 1 }
  ]
}
```

Legacy request format is also supported:

```json
{
  "items": ["APPLE", "BANANA", "APPLE", "ORANGE"]
}
```

Response:

```json
{
  "cartId": "c5f0bbef-3ff4-4f2f-a4dd-1f0cc3dd6bd4"
}
```

## Checkout API

`GET /api/checkout/{cartId}`

Response example:

```json
{
  "lines": [
    {
      "productCode": "APPLE",
      "productName": "Apple",
      "quantity": 2,
      "unitPrice": 0.30,
      "offerBundleCount": 1,
      "offerQuantity": 2,
      "offerPrice": 0.45,
      "lineTotal": 0.45
    },
    {
      "productCode": "BANANA",
      "productName": "Banana",
      "quantity": 1,
      "unitPrice": 0.20,
      "offerBundleCount": null,
      "offerQuantity": null,
      "offerPrice": null,
      "lineTotal": 0.20
    },
    {
      "productCode": "ORANGE",
      "productName": "Orange",
      "quantity": 1,
      "unitPrice": 0.50,
      "offerBundleCount": null,
      "offerQuantity": null,
      "offerPrice": null,
      "lineTotal": 0.50
    }
  ],
  "total": 1.15,
  "currency": "EUR"
}
```

## Default Products

- APPLE: 0.30 EUR
- BANANA: 0.20 EUR
- ORANGE: 0.50 EUR
- PEAR: 0.45 EUR
- KIWI: 0.80 EUR
- MILK: 1.10 EUR
- BREAD: 1.30 EUR
- EKMEK: 1.25 EUR
- SUCUK: 3.90 EUR

## Weekly Offers

- APPLE: 2 for 0.45 EUR
- PEAR: 3 for 1.20 EUR
- KIWI: 2 for 1.40 EUR
- BREAD: 2 for 2.30 EUR
