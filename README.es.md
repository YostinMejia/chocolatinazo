# Chocolatinazo 🍫

API REST que simula "El Chocolatinazo", un juego en el que un grupo de amigos saca al azar un sticker numerado (1–320) de una chocolatina, y el jugador con el número mayor o menor — según la regla de la ronda — paga las chocolatinas de todos los demás.

---

## Tabla de Contenido

- [Tecnologías](#tecnologías)
- [Arquitectura](#arquitectura)
- [Autenticación y Autorización](#autenticación-y-autorización)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Endpoints](#endpoints)
- [Variables de Entorno](#variables-de-entorno)
- [Cómo Ejecutar](#cómo-ejecutar)
- [Uso de Inteligencia Artificial](#uso-de-inteligencia-artificial)

---

## Tecnologías

| Tecnología           | Propósito |
|----------------------|---|
| Java 21              | Lenguaje de programación |
| Spring Boot 4.0.4   | Framework backend |
| Spring Security      | Cadena de filtros de seguridad |
| AWS Cognito          | Autenticación y autorización |
| AWS RDS (PostgreSQL) | Base de datos relacional |
| MapStruct            | Mapeo de objetos entre capas |
| Lombok               | Reducción de código repetitivo |
| Maven                | Gestión de dependencias |

---

## Arquitectura

El proyecto sigue una **Arquitectura Hexagonal** (también conocida como Puertos y Adaptadores), dividiendo el código en tres capas claramente separadas:

```
src/main/java/com/yostin/evolucioncb/chocolatinazo/
├── domain/                  # Lógica de negocio pura — sin dependencias de frameworks
│   ├── auth/                # Modelos, puertos y excepciones de autenticación
│   ├── game/                # Modelos, puertos y excepciones del juego
│   ├── chocolatinauser/     # Modelos y puertos de ChocolatinaUser
│   └── finishedgame/        # Modelos y puertos de FinishedGame
│
├── application/             # Orquesta el dominio — sin conocimiento de HTTP ni BD
│   └── service/
│       ├── AuthService
│       ├── GameService
│       └── ChocolatinaUserService
│
└── infrastructure/          # Todas las dependencias externas viven aquí
    ├── auth/                # Adaptador Cognito, configuración de seguridad, DTOs de auth
    ├── entry/               # Controladores REST y DTOs de request/response
    ├── mappers/             # Mappers de MapStruct
    └── persistence/         # Entidades JPA, repositorios y adaptadores
```

### Regla de dependencias

Las dependencias siempre apuntan hacia adentro:

```
Infraestructura → Aplicación → Dominio
```

La capa de dominio nunca importa de aplicación ni de infraestructura. La capa de aplicación nunca importa de infraestructura. Esto significa que toda la lógica de negocio puede probarse sin Spring, Cognito ni base de datos.

---

## Autenticación y Autorización

### Autenticación — AWS Cognito

La autenticación está completamente delegada a **AWS Cognito User Pools**. El sistema no almacena contraseñas localmente.

**Flujo:**

```
POST /auth/signup     → Crea el usuario en Cognito con el rol asignado
POST /auth/confirm-email    → El usuario envía el código de verificación de 6 dígitos
POST /auth/login      → Cognito valida las credenciales y retorna los tokens JWT
```

Después del login, Cognito retorna tres tokens:

| Token | Propósito | Expira |
|---|---|---|
| `idToken` | Contiene los claims del usuario incluyendo el rol — se envía en cada request | 1 hora |
| `accessToken` | Usado para operaciones de gestión de Cognito | 1 hora |
| `refreshToken` | Usado para renovar los otros dos tokens silenciosamente | 30 días |

El cliente debe enviar el `idToken` como Bearer token en cada request protegido:

```
Authorization: Bearer <idToken>
```

### Confirmación de correo electrónico

Después del registro, Cognito envía automáticamente un **código de verificación de 6 dígitos** al correo del usuario. El usuario debe confirmar su cuenta antes de poder iniciar sesión. Esto garantiza la propiedad de la dirección de correo electrónico.

### Autorización — RBAC (Control de Acceso Basado en Roles)

El sistema utiliza **RBAC** para proteger los endpoints. A cada usuario se le asigna exactamente un rol durante el registro, almacenado como atributo personalizado de Cognito (`custom:role`).

| Rol | Permisos |
|---|---|
| `PLAYER` | Unirse a un juego, tomar una chocolatina |
| `AUDITOR` | Ver movimientos del juego actual, ver juegos finalizados |
| `ADMIN` | Crear juego, actualizar precio de la chocolatina, calcular perdedor |

### Validación del JWT

Spring Security valida los tokens JWT usando las **claves públicas JWKS de Cognito**. Las claves se obtienen una sola vez al arrancar la aplicación y se cachean — no se hace ninguna llamada a AWS en cada request:

```
https://cognito-idp.{region}.amazonaws.com/{userPoolId}/.well-known/jwks.json
```

Un `JwtAuthenticationConverter` personalizado lee el claim `custom:role` y lo mapea a un `GrantedAuthority` de Spring con el prefijo `ROLE_`, habilitando los checks `hasRole()` en la configuración de seguridad.

---

## Endpoints

### Auth — `/api/v1/auth`

| Método | Ruta | Acceso | Descripción |
|---|---|---|---|
| POST | `/signup` | Público | Registrar un nuevo usuario con un rol |
| POST | `/confirm-email` | Público | Confirmar correo con el código de verificación |
| POST | `/login` | Público | Iniciar sesión y recibir los tokens JWT |

**Body de registro:**
```json
{
  "email": "jugador@ejemplo.com",
  "password": "Password1@",
  "username": "jugador1",
  "role": "PLAYER"
}
```

**Respuesta del login:**
```json
{
  "idToken": "eyJ...",
  "accessToken": "eyJ...",
  "refreshToken": "eyJ..."
}
```

---

### Juego — `/api/v1/game`

| Método | Ruta | Acceso | Descripción |
|---|---|---|---|
| POST | `/create` | ADMIN | Crear un nuevo juego |
| POST | `/join` | PLAYER | Unirse al juego actual y obtener número de sticker |
| GET | `/audit` | AUDITOR | Ver todos los movimientos del juego actual |
| PATCH | `/admin/update-price/{gameId}` | ADMIN | Actualizar precio unitario de la chocolatina |
| POST | `/admin/calculate-loser` | ADMIN | Calcular perdedor y finalizar el juego |
| GET | `/audit/finished-games` | AUDITOR | Ver todos los juegos finalizados |

**Body para crear juego:**
```json
{
  "unitPrice": 2500.00,
  "rule": "HIGHEST"
}
```

**No se necesita Body para calcular perdedor:**
```json
{}
```

---

## Esquema de Base de Datos

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

## Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto basado en esta plantilla:

```bash
# Base de datos — AWS RDS PostgreSQL
SPRING_DATASOURCE_URL=jdbc:postgresql://<rds-endpoint>:5432/<database>
SPRING_DATASOURCE_USERNAME=tu_usuario_db
SPRING_DATASOURCE_PASSWORD=tu_contraseña_db

# AWS Cognito
AWS_COGNITO_REGION=us-east-1
AWS_COGNITO_USER_POOL_ID=us-east-1_XXXXXXXX
AWS_COGNITO_CLIENT_ID=tu_client_id
AWS_COGNITO_CLIENT_SECRET=tu_client_secret
SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI=https://cognito-idp.us-east-1.amazonaws.com/us-east-1_XXXXXXXX/.well-known/jwks.json

# Spring
SPRING_PROFILES_ACTIVE=prod
```

> Nunca subas el archivo `.env` al repositorio. Está incluido en `.gitignore`.

---

## Cómo Ejecutar

**Requisitos previos:** Java 17, Maven, archivo `.env` configurado.

```bash
# Clonar el repositorio
git clone https://github.com/YostinMejia/chocolatinazo.git
cd chocolatinazo

# Ejecutar la aplicación
./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

---


## Uso de Inteligencia Artificial

Se utilizó Claude (Anthropic) como asistente de desarrollo a lo largo de este proyecto en las siguientes áreas:

- **Investigación**: Comprensión de los modelos de control de acceso RBAC, ABAC y PBAC.
- **Diseño**: Definición de la estructura de capas de la arquitectura hexagonal, puertos de dominio y patrón adaptador.
- **Desarrollo**: Generación de código repetitivo para entidades JPA, mappers de MapStruct, adaptadores de repositorio e integración con Cognito. Todo el código generado fue revisado, comprendido y adaptado antes de ser utilizado.
- **Documentación**: Asistencia en la redacción de Javadoc para métodos clave y la estructura del README.
