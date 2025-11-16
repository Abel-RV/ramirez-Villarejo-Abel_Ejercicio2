# 🏫 Sistema de Reservas de Aulas

API REST con Spring Boot para la gestión de reservas de aulas en un centro educativo, con autenticación JWT y roles de usuario.

## 📋 Tabla de Contenidos

- [Características](#características)
- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Entidades](#entidades)
- [Endpoints de la API](#endpoints-de-la-api)
- [DTOs](#dtos)
- [Roles y Permisos](#roles-y-permisos)
- [Validaciones de Negocio](#validaciones-de-negocio)
- [Despliegue](#despliegue)
- [Uso de la Interfaz Web](#uso-de-la-interfaz-web)

---

## ✨ Características

- ✅ Autenticación y autorización con **JWT**
- ✅ Gestión de usuarios con roles (**ADMIN**, **PROFESOR**)
- ✅ CRUD completo de **Aulas**, **Reservas** y **Horarios**
- ✅ Validaciones de lógica de negocio:
    - No permitir reservas solapadas
    - No permitir reservas en el pasado
    - Validar capacidad de asistentes
- ✅ Cifrado de contraseñas con **BCrypt**
- ✅ Manejo global de excepciones
- ✅ Documentación completa de endpoints
- ✅ Interfaz web moderna con React

---

## 🛠️ Tecnologías

### Backend
- **Java 17**
- **Spring Boot 3.x**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **MySQL** (desarrollo) / **PostgreSQL** (producción)
- **Lombok**
- **Bean Validation**

### Frontend
- **React 18**
- **Tailwind CSS**
- **Lucide React** (iconos)

---

## 📦 Requisitos Previos

- **Java 17** o superior
- **Maven 3.8+**
- **MySQL 8.0** (para desarrollo local)
- **Git**
- **IntelliJ IDEA** / Eclipse / VSCode (opcional)

---

## 🚀 Instalación y Configuración

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/TU_USUARIO/reservas-aulas-backend.git
cd reservas-aulas-backend
```

### 2️⃣ Configurar Base de Datos

Crea una base de datos MySQL:

```sql
CREATE DATABASE reservas;
```

### 3️⃣ Configurar `application.properties`

El archivo ya está configurado para desarrollo local con MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/reservas
spring.datasource.username=root
spring.datasource.password=
```

### 4️⃣ Compilar y ejecutar

```bash
mvn clean install
mvn spring-boot:run
```

La aplicación estará disponible en: **http://localhost:8080**

### 5️⃣ Usuario por defecto

Al iniciar la aplicación, se crea automáticamente un usuario administrador:

- **Email:** `admin@gmail.com`
- **Password:** `admin1234`
- **Rol:** `ROLE_ADMIN`

---

## 📁 Estructura del Proyecto

```
src/main/java/com/abel/ejercicio2/
├── config/
│   ├── DataInitializer.java          # Inicialización de datos
│   └── SecurityConfig.java            # Configuración de seguridad JWT
├── controllers/
│   ├── AuthController.java            # Login, registro, perfil
│   ├── AulaController.java            # CRUD de aulas
│   ├── ReservaController.java         # CRUD de reservas
│   ├── UsuarioController.java         # Gestión de usuarios
│   └── HorarioController.java         # CRUD de horarios
├── dto/
│   ├── request/
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   ├── AulaRequest.java
│   │   ├── ReservaRequest.java
│   │   ├── HorarioRequest.java
│   │   ├── UsuarioRequest.java
│   │   └── PasswordRequest.java
│   └── dtos/
│       ├── AulaDTO.java
│       ├── ReservaDTO.java
│       ├── HorarioDTO.java
│       └── UsuarioDto.java
├── entities/
│   ├── Usuario.java                   # Entidad Usuario (UserDetails)
│   ├── Aula.java                      # Entidad Aula
│   ├── Reserva.java                   # Entidad Reserva
│   └── Horario.java                   # Entidad Horario
├── enums/
│   ├── DiasSemana.java                # LUNES, MARTES, etc.
│   └── TramoHorario.java              # RECREO, LECTIVA, MEDIODIA
├── errors/
│   └── GlobalExceptionHandler.java    # Manejo global de excepciones
├── repositories/
│   ├── UsuarioRepository.java
│   ├── AulaRepository.java
│   ├── ReservaRepository.java
│   └── HorarioRepository.java
├── services/
│   ├── AuthService.java               # Lógica de autenticación
│   ├── JwtService.java                # Generación y validación JWT
│   ├── AulaService.java
│   ├── ReservaService.java
│   ├── HorarioService.java
│   ├── UsuarioService.java
│   └── CustomUserDetailService.java   # Carga de usuarios
└── Main.java                          # Clase principal
```

---

## 🗂️ Entidades

### **Usuario**
- `id` (Long) - PK
- `nombre` (String)
- `apellidos` (String)
- `email` (String, unique)
- `password` (String, cifrado)
- `roles` (String) - "ROLE_ADMIN" o "ROLE_PROFESOR"
- `reservas` (List<Reserva>)

### **Aula**
- `id` (Long) - PK
- `nombre` (String)
- `capacidad` (Integer)
- `esAulaOrdenadores` (Boolean)
- `numeroOrdenadores` (Integer)
- `reservas` (List<Reserva>)

### **Reserva**
- `id` (Long) - PK
- `fecha` (LocalDate)
- `motivo` (String)
- `numeroAsistentes` (Integer)
- `fechaCreacion` (LocalDate, auto)
- `aula` (Aula)
- `usuario` (Usuario)
- `horario` (List<Horario>)

### **Horario**
- `id` (Long) - PK
- `diasSemana` (DiasSemana enum)
- `sesionDia` (int)
- `horarioInicio` (LocalTime)
- `horarioFin` (LocalTime)
- `tramoHorario` (TramoHorario enum)
- `reserva` (Reserva)

---

## 🔌 Endpoints de la API

### 🔐 **Autenticación** (`/auth`)

| Método | Endpoint | Descripción | Autenticación | Rol |
|--------|----------|-------------|---------------|-----|
| POST | `/auth/register` | Registrar nuevo usuario | ❌ No | - |
| POST | `/auth/login` | Iniciar sesión (obtener JWT) | ❌ No | - |
| GET | `/auth/perfil` | Obtener datos del usuario autenticado | ✅ Sí | Cualquiera |

**Ejemplo de login:**
```json
POST /auth/login
{
  "email": "admin@gmail.com",
  "password": "admin1234"
}

Respuesta:
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### 🚪 **Aulas** (`/aula`)

| Método | Endpoint | Descripción | Rol Requerido |
|--------|----------|-------------|---------------|
| GET | `/aula` | Listar todas las aulas | Cualquiera |
| GET | `/aula/{id}` | Obtener aula por ID | Cualquiera |
| GET | `/aula/{id}/reservas` | Ver reservas de un aula | Cualquiera |
| POST | `/aula` | Crear nueva aula | ADMIN |
| PUT | `/aula/{id}` | Modificar aula | ADMIN |
| DELETE | `/aula/{id}` | Eliminar aula | ADMIN |

**Ejemplo crear aula:**
```json
POST /aula
Headers: Authorization: Bearer {token}
{
  "nombre": "Aula 101",
  "capacidad": 30,
  "esAulaOrdenadores": true,
  "numeroOrdenadores": 25
}
```

---

### 📅 **Reservas** (`/reserva`)

| Método | Endpoint | Descripción | Rol Requerido |
|--------|----------|-------------|---------------|
| GET | `/reserva` | Listar todas las reservas | Cualquiera |
| GET | `/reserva/{id}` | Obtener reserva por ID | Cualquiera |
| POST | `/reserva` | Crear nueva reserva | ADMIN |
| PUT | `/reserva/{id}` | Modificar reserva | ADMIN |
| DELETE | `/reserva/{id}` | Eliminar reserva | ADMIN o propietario |

**Ejemplo crear reserva:**
```json
POST /reserva
Headers: Authorization: Bearer {token}
{
  "fecha": "2025-12-20",
  "motivo": "Clase de matemáticas",
  "numeroAsistentes": 25,
  "aulaId": 1,
  "usuarioId": 1,
  "horarioId": [1, 2]
}
```

---

### ⏰ **Horarios** (`/tramo-horario`)

| Método | Endpoint | Descripción | Rol Requerido |
|--------|----------|-------------|---------------|
| GET | `/tramo-horario` | Listar todos los horarios | Cualquiera |
| GET | `/tramo-horario/{id}` | Obtener horario por ID | Cualquiera |
| POST | `/tramo-horario` | Crear nuevo horario | ADMIN |
| DELETE | `/tramo-horario/{id}` | Eliminar horario | ADMIN |

---

### 👤 **Usuarios** (`/usuario`)

| Método | Endpoint | Descripción | Rol Requerido |
|--------|----------|-------------|---------------|
| PUT | `/usuario/{id}` | Actualizar datos de usuario | ADMIN |
| DELETE | `/usuario/{id}` | Eliminar usuario | ADMIN |

---

## 📦 DTOs

### **Request DTOs** (Entrada)

- **LoginRequest**: `email`, `password`
- **RegisterRequest**: `email`, `password`, `nombre`, `apellidos`
- **AulaRequest**: `nombre`, `capacidad`, `esAulaOrdenadores`, `numeroOrdenadores`
- **ReservaRequest**: `fecha`, `motivo`, `numeroAsistentes`, `aulaId`, `usuarioId`, `horarioId[]`
- **HorarioRequest**: `diasSemana`, `sesionDia`, `horarioInicio`, `horarioFin`, `tramoHorario`
- **UsuarioRequest**: `nombre`, `apellidos`, `email`
- **PasswordRequest**: `password`, `passwordNueva`

### **Response DTOs** (Salida)

- **UsuarioDto**: `id`, `nombre`, `apellidos`, `email`, `roles` (sin password)
- **AulaDTO**: `id`, `nombre`, `capacidad`, `esAulaOrdenadores`, `numeroOrdenadores`
- **ReservaDTO**: `id`, `fecha`, `motivo`, `numeroAsistentes`, `fechaCreacion`, `aula`, `usuario`, `horarios`
- **HorarioDTO**: `id`, `diasSemana`, `sesionDia`, `horarioInicio`, `horarioFin`, `tramoHorario`

---

## 🔒 Roles y Permisos

### **ROLE_ADMIN**
- ✅ Acceso total a todos los endpoints
- ✅ Crear, modificar y eliminar aulas
- ✅ Crear, modificar y eliminar reservas
- ✅ Gestionar usuarios
- ✅ Gestionar horarios

### **ROLE_PROFESOR**
- ✅ Ver aulas, reservas y horarios
- ✅ Crear sus propias reservas
- ✅ Eliminar solo sus propias reservas
- ❌ No puede gestionar aulas
- ❌ No puede gestionar otros usuarios

---

## ✅ Validaciones de Negocio

### **Reservas:**
1. ❌ **No se permiten reservas en el pasado**
   ```
   Error: "No se pueden crear reservas en el pasado"
   ```

2. ❌ **No se permiten solapamientos** (misma aula, fecha y horario)
   ```
   Error: "Ya existe una reserva para esta aula en la fecha y horario seleccionados"
   ```

3. ❌ **Número de asistentes no puede superar capacidad del aula**
   ```
   Error: "El número de asistentes (35) supera la capacidad del aula (30)"
   ```

### **Usuarios:**
- Email único (no duplicados)
- Contraseña mínimo 3 caracteres
- Email con formato válido

### **Aulas:**
- Nombre obligatorio
- Capacidad mínimo 1 persona

---

## 🚀 Despliegue

### **Desarrollo Local**
```bash
mvn spring-boot:run
```

### **Producción (Render.com)**

1. **Crear cuenta en Render:** https://render.com

2. **Crear Web Service:**
    - Environment: Docker
    - Branch: main
    - Variables de entorno:
        - `SPRING_PROFILES_ACTIVE=prod`
        - `DATABASE_URL={url_postgres}`

3. **Crear PostgreSQL Database:**
    - Plan: Free
    - Conectar con el Web Service

4. **Desplegar:**
    - Push a GitHub
    - Render despliega automáticamente

**URL de producción:** `https://tu-app.onrender.com`

---

## 🖥️ Uso de la Interfaz Web

### **Acceder a la aplicación:**

**Desarrollo:** http://localhost:8080 (si sirves frontend desde Spring)
**Producción:** https://tu-frontend.netlify.app

### **Funcionalidades:**

1. **Login/Registro:**
    - Registrar nuevo usuario (rol PROFESOR por defecto)
    - Iniciar sesión con email y contraseña
    - Token JWT guardado automáticamente

2. **Gestión de Aulas:**
    - Ver todas las aulas disponibles
    - Crear nuevas aulas (solo ADMIN)
    - Eliminar aulas (solo ADMIN)

3. **Gestión de Reservas:**
    - Crear reservas seleccionando aula, fecha y horarios
    - Ver todas las reservas
    - Eliminar reservas propias o todas (ADMIN)

4. **Horarios:**
    - Visualizar todos los tramos horarios
    - Información de días, sesiones y tipo

---

## 📝 Notas Adicionales

### **Base de Datos**

El proyecto utiliza dos bases de datos según el entorno:

- **Desarrollo:** MySQL (localhost)
- **Producción:** PostgreSQL (Render)

Los archivos de configuración son:
- `application.properties` → Desarrollo
- `application-prod.properties` → Producción

### **Seguridad**

- Las contraseñas se cifran con **BCrypt**
- Los tokens JWT expiran en **24 horas**
- CORS configurado para permitir peticiones desde cualquier origen

### **CORS**

Configurado en `Main.java` para aceptar peticiones de cualquier origen en desarrollo. Para producción, se recomienda especificar dominios permitidos.

---

## 👨‍💻 Autor

**Abel Ramírez Villarejo**

---

## 📄 Licencia

Este proyecto es un ejercicio académico.

---

## 🐛 Problemas Conocidos

- Render Free se duerme después de 15 minutos de inactividad (primera petición tarda ~30 seg)
- Los tokens JWT no se renuevan automáticamente (hay que hacer login de nuevo)

---

## 📞 Soporte

Para reportar problemas o sugerencias, abre un issue en el repositorio de GitHub.