# Airline Backend Simulator

This project is a simple Spring Boot application that demonstrates the core pieces of an airline booking platform. It exposes REST endpoints to search for flights, register and login users, and create or cancel bookings. The codebase is intentionally compact so it can be extended in phases as described in [ARCHITECTURE.md](ARCHITECTURE.md).

## Features

- Search for flights by origin and destination.
- Book or cancel flights for existing customers.
- JWT-based user registration and authentication.
- View or update a registered user's profile.
- In-memory H2 database with sample data loaded on startup.

## Prerequisites

- JDK 17+
- Maven 3.x

## Running the Application

1. Clone this repository and change into the project directory.
2. Build and start the service:

```bash
mvn spring-boot:run
```

The application listens on port `8080`. A sample flight and user are created automatically. You can login with `john` / `password`.

The H2 database console is available at `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:airline` and user `sa`).

## API Endpoints

| Method | Path | Description |
|-------|------|-------------|
|`GET`|`/api/flights?origin=XXX&destination=YYY`|Search for flights.|
|`POST`|`/api/bookings`|Create a booking `{\"flightId\":1, \"customerId\":1}`|
|`DELETE`|`/api/bookings/{id}`|Cancel an existing booking|
|`POST`|`/api/auth/register`|Register a new user|
|`POST`|`/api/auth/login`|Login and receive a JWT token|
|`GET`|`/api/user/profile`|View current user's profile (requires JWT)|
|`PUT`|`/api/user/profile`|Update current user's profile (requires JWT)|

Include the JWT token returned by the login endpoint in the `Authorization` header: `Bearer <token>`.

## Building

To build a runnable jar:

```bash
mvn clean package
```

## Testing

There are currently no automated tests, but you can execute the Maven test lifecycle:

```bash
mvn test
```

## Documentation

A detailed development plan and architecture overview can be found in [ARCHITECTURE.md](ARCHITECTURE.md).
