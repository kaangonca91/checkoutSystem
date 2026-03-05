# Supermarket Checkout (Spring Boot)

## Run

```bash
mvn spring-boot:run
```

## Checkout API

`POST /api/checkout`

Request:

```json
{
  "items": ["APPLE", "BANANA", "APPLE", "ORANGE"]
}
```

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

## Default Products and Offer

- APPLE: 0.30 EUR (offer: 2 for 0.45 EUR)
- BANANA: 0.20 EUR
- ORANGE: 0.50 EUR
