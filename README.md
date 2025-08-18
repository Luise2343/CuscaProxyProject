# products-service

A minimal Spring Boot service that **proxies the Fake Store API** and exposes a stable internal catalog for other services (e.g., `shop-service`).
Endpoints are intentionally small: list all products and get product by id.

---

## Tech Stack

* Java 17, Maven 3.9+
* Spring Boot **3.5.4**
* springdoc-openapi **2.6.0** (Swagger UI)
* Spring Web (MVC)
* Jakarta Bean Validation (via `spring-boot-starter-validation`)
* (Optional) Lombok

> No database. No authentication. The service is read-only and stateless.

---

## Run locally

```bash
# from products-service/
mvn spring-boot:run
```

Default port: **8081**

* Swagger UI:        `http://localhost:8081/swagger`
* OpenAPI (JSON):    `http://localhost:8081/v3/api-docs`

If you prefer a packaged jar:

```bash
mvn clean package
java -jar target/products-service-0.0.1-SNAPSHOT.jar
```

---

## Configuration

`src/main/resources/application.properties` (defaults)

```
# Swagger / OpenAPI
springdoc.swagger-ui.path=/swagger
springdoc.api-docs.path=/v3/api-docs
```

No external configuration is required. The service calls `https://fakestoreapi.com` under the hood.

---

## API

### GET /api/products

Returns the full list from Fake Store.

**Request**

```
GET /api/products
```

**Response (200)**

```json
[
  {
    "id": 1,
    "title": "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
    "price": 109.95,
    "category": "men's clothing",
    "description": "...",
    "image": "https://..."
  },
  ...
]
```

---

### GET /api/products/{id}

Returns one product by id.

**Request**

```
GET /api/products/1
```

**Response (200)**

```json
{
  "id": 1,
  "title": "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
  "price": 109.95,
  "category": "men's clothing",
  "description": "...",
  "image": "https://..."
}
```

**Errors**

* `404` if the upstream returns not found (or an invalid id).

---

## Project structure (high level)

```
products-service
└── src/main/java/com/proxyproject/products_service
    ├── controllers
    │   └── ProductController.java     # /api/products, /api/products/{id}
    └── clients
        └── FakeStoreClient.java       # HTTP client to fakestoreapi.com
```

---

## Troubleshooting

* **OpenAPI / Swagger error (NoSuchMethodError / ControllerAdviceBean)**
  Ensure springdoc is pinned to **2.6.0** and you don’t have multiple conflicting springdoc artifacts.

* **Bean Validation warning (`NoProviderFoundException`)**
  Make sure `spring-boot-starter-validation` is on the classpath (the project already includes it).

* **Port in use**
  Change the port via `server.port=8082` in `application.properties`, then access Swagger at `http://localhost:8082/swagger`.

---

## Notes

* This service is intentionally thin: no auth, no persistence.
* Designed to be consumed by `shop-service` (via Feign) for **re-pricing** at order creation time.
* If you need CORS (e.g., calling from a browser directly), add a simple CORS config; server-to-server calls (Feign) don’t require it.

---

## License

MIT (or adapt as needed).
