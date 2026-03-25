# Chocolatinazo 🍫

A REST API that simulates "El Chocolatinazo", a game where a group of friends each draw a randomly numbered chocolate bar sticker (1–320), and the player with the highest or lowest number — depending on the round rule — pays for everyone else's chocolates.

---

## Table of Contents

- [Technologies](#technologies)
- [Architecture](#architecture)
- [Authentication & Authorization](#authentication--authorization)
- [Endpoints](#endpoints)
- [Database Schema](#database-schema)
- [Environment Variables](#environment-variables)
- [How to Run](#how-to-run)
- [AI Usage](#ai-usage)

---

## Technologies

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 4.0.4 | Backend framework |
| Spring Security | Security filter chain |
| AWS Cognito | Authentication & authorization |
| AWS RDS (PostgreSQL) | Relational database |
| MapStruct | Object mapping between layers |
| Lombok | Boilerplate reduction |
| Maven | Dependency management |

---

## Architecture

The project follows **Hexagonal Architecture** (also known as Ports and Adapters), dividing the codebase into three clearly separated layers:

```
src/main/java/com/yostin/evolucioncb/chocolatinazo/
├── domain/                  # Pure business logic — no framework dependencies
│   ├── auth/                # Auth models, ports and exceptions
│   ├── game/                # Game models, ports and exceptions
│   ├── chocolatinauser/     # ChocolatinaUser models and ports
│   └── finishedgame/        # FinishedGame models and ports
│
├── application/             # Orchestrates domain — no HTTP or DB knowledge
│   └── service/
│       ├── AuthService
│       ├── GameService
│       └── ChocolatinaUserService
│
└── infrastructure/          # All external dependencies live here
    ├── auth/                # Cognito adapter, Security config, Auth DTOs
    ├── entry/               # REST controllers and request/response DTOs
    ├── mappers/             # MapStruct mappers
    └── persistence/         # JPA entities, repositories and adapters
```

### Dependency rule

Dependencies always point inward:

```
Infrastructure → Application → Domain
```

The domain layer never imports from application or infrastructure. The application layer never imports from infrastructure. This means the entire business logic can be tested without Spring, Cognito, or a database.

---

## Authentication & Authorization

### Authentication — AWS Cognito

Authentication is fully delegated to **AWS Cognito User Pools**. The system does not store passwords locally.

**Flow:**

```
POST /auth/signup          → Creates user in Cognito with assigned role
POST /auth/confirm-email   → User submits 6-digit email verification code
POST /auth/login           → Cognito validates credentials and returns JWT tokens
```

After login, Cognito returns three tokens:

| Token | Purpose | Expires |
|---|---|---|
| `idToken` | Contains user claims including role — sent on every request | 1 hour |
| `accessToken` | Used for Cognito management operations | 1 hour |
| `refreshToken` | Used to renew the other two tokens silently | 30 days |

The client must send the `idToken` as a Bearer token on every protected request:

```
Authorization: Bearer <idToken>
```

### Email Confirmation

After signup, Cognito automatically sends a **6-digit verification code** to the user's email. The user must confirm their account before they can log in. This guarantees ownership of the email address.

### Authorization — RBAC (Role-Based Access Control)

The system uses **RBAC** to protect endpoints. Each user is assigned exactly one role during registration, stored as a custom Cognito attribute (`custom:role`).

| Role | Permissions |
|---|---|
| `PLAYER` | Join a game, pick a chocolatina |
| `AUDITOR` | View current game movements, view finished games |
| `ADMIN` | Create game, update chocolatina price, calculate loser |

### JWT Validation

Spring Security validates JWT tokens using Cognito's **JWKS public keys**. The keys are fetched once on startup and cached — no AWS call is made on every request:

```
https://cognito-idp.{region}.amazonaws.com/{userPoolId}/.well-known/jwks.json
```

A custom `JwtAuthenticationConverter` reads the `custom:role` claim and maps it to a Spring `GrantedAuthority` with the `ROLE_` prefix, enabling `hasRole()` checks in the security config.

---

## Endpoints

### Auth — `/api/v1/auth`

| Method | Path | Access | Description |
|---|---|---|---|
| POST | `/signup` | Public | Register a new user with a role |
| POST | `/confirm-email` | Public | Confirm email with verification code |
| POST | `/login` | Public | Login and receive JWT tokens |

**Signup request body:**
```json
{
  "email": "player@example.com",
  "password": "Password1@",
  "username": "player1",
  "role": "PLAYER"
}
```

**Login response:**
```json
{
  "idToken": "eyJ...",
  "accessToken": "eyJ...",
  "refreshToken": "eyJ..."
}
```

---

### Game — `/api/v1/game`

| Method | Path | Access | Description |
|---|---|---|---|
| POST | `/create` | ADMIN | Create a new game |
| POST | `/join` | PLAYER | Join current game and get sticker number |
| GET | `/audit` | AUDITOR | View all current game movements |
| PATCH | `/admin/update-price/{gameId}` | ADMIN | Update chocolatina unit price |
| POST | `/admin/calculate-loser` | ADMIN | Calculate loser and finish the game |
| GET | `/audit/finished-games` | AUDITOR | View all finished games |

**Create game request:**
```json
{
  "unitPrice": 2500.00,
  "rule": "HIGHEST"
}
```

**No body needed to calculate loser:**
```json
{}
```

---

## Database Schema

```
game
├── id (PK)
├── created_at
├── chocolatina_price
├── rule               (HIGHEST | LOWEST)
└── status             (WAITING | PLAYING | FINISHED)

chocolatina_user
├── id (PK)
├── user_email
├── sticker_number
└── created_at

finished_game
├── id (PK)
├── loser_email
├── losing_number
├── total_players
├── unit_price
├── total_lost
└── finished_at
```

---

## Environment Variables

Create a `.env` file at the root of the project based on this template:

```bash
# Database — AWS RDS PostgreSQL
SPRING_DATASOURCE_URL=jdbc:postgresql://<rds-endpoint>:5432/<database>
SPRING_DATASOURCE_USERNAME=your_db_username
SPRING_DATASOURCE_PASSWORD=your_db_password

# AWS Cognito
AWS_COGNITO_REGION=us-east-1
AWS_COGNITO_USER_POOL_ID=us-east-1_XXXXXXXX
AWS_COGNITO_CLIENT_ID=your_client_id
AWS_COGNITO_CLIENT_SECRET=your_client_secret
SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI=https://cognito-idp.us-east-1.amazonaws.com/us-east-1_XXXXXXXX/.well-known/jwks.json

# Spring
SPRING_PROFILES_ACTIVE=prod
```

> Never commit the `.env` file. It is included in `.gitignore`.

---

## How to Run

**Prerequisites:** Java 21, Maven, a configured `.env` file.

```bash
# Clone the repository
git clone https://github.com/YostinMejia/chocolatinazo.git
cd chocolatinazo

# Run the application
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## AI Usage

Claude (Anthropic) was used as a development assistant throughout this project in the following areas:

- **Research**: Understanding RBAC, ABAC and PBAC access control models.
- **Design**: Defining the hexagonal architecture layer structure, domain ports, and adapter pattern.
- **Development**: Generating boilerplate code for JPA entities, MapStruct mappers, repository adapters, and Cognito integration. All generated code was reviewed, understood, and adapted before being used.
- **Documentation**: Assisting with Javadoc for key methods and the README structure.
