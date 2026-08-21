# URL Shortener

A simple URL shortener REST API built with Java and Spring Boot.

## Features

* Generate short codes for URLs
* Redirect short URLs to their original destination
* Track the number of accesses
* Retrieve information about shortened URLs
* URL validation and error handling
* Unit tests with JUnit and Mockito

## Technologies

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Hibernate Validator
* H2 Database
* JUnit 5
* Mockito
* Maven

## API Endpoints

| Method | Endpoint           | Description                                 |
| ------ | ------------------ | ------------------------------------------- |
| `POST` | `/api/urls`        | Creates a shortened URL                     |
| `GET`  | `/api/urls/{code}` | Retrieves information about a shortened URL |
| `GET`  | `/{code}`          | Redirects to the original URL               |

### Create a short URL

```http
POST /api/urls
Content-Type: application/json
```

```json
{
  "longUrl": "https://github.com/"
}
```

Example response:

```json
{
  "code": "a8K2pQ",
  "longUrl": "https://github.com/",
  "accessCount": 0,
  "createdAt": "2026-08-21T00:00:00"
}
```

## Running

Clone the repository and run:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Tests

Run the automated tests with:

```bash
./mvnw test
```
