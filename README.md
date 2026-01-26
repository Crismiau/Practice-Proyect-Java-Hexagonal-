# 🏥 MedicCenter - Sistema de Gestión de Citas Médicas

Este proyecto es un backend robusto diseñado para gestionar centros médicos, permitiendo la administración de pacientes, citas y seguridad. Está construido bajo los principios de la **Arquitectura Hexagonal (Puertos y Adaptadores)**, lo que garantiza un código desacoplado, testeable y fácil de mantener.

## 🏗️ Estructura del Proyecto

El proyecto sigue una **Arquitectura Hexagonal (Puertos y Adaptadores)**. Este patrón divide la aplicación en capas concéntricas, donde la dependencia siempre va hacia el centro (el Dominio).

```text
       +---------------------------------------------------------+
       |                  Infrastructure Layer                   |
       |    (Adapters: Controllers, Repositories, Clients)       |
       |                                                         |
       |       +-----------------------------------------+       |
       |       |           Application Layer             |       |
       |       |          (Services, Use Cases)          |       |
       |       |                                         |       |
       |       |       +-------------------------+       |       |
       |       |       |      Domain Layer       |       |       |
       |       |       |   (Models, Ports, Ex)   |       |       |
       |       |       +-------------------------+       |       |
       |       +-----------------------------------------+       |
       +---------------------------------------------------------+
```

### 1. 📂 Capa de Dominio (`domain`)
Es el corazón del sistema, independiente de cualquier tecnología o framework.
- **Modelos (`model`)**: Representan los objetos de negocio (`Patient`, `Appointment`). No son entidades de JPA, son objetos Java puros (POJOs).
- **Puertos de Entrada (`ports.in`)**: Interfaces que definen las operaciones permitidas por el dominio (`AppointmentUseCase`).
- **Puertos de Salida (`ports.out`)**: Interfaces que el dominio define para que la infraestructura las implemente (abstracción de base de datos, servicios externos).
- **Excepciones (`exception`)**: Define qué errores pueden ocurrir en el negocio.

### 2. 📂 Capa de Aplicación (`application`)
Coordina la ejecución de la lógica de negocio.
- **Servicios (`services`)**: Implementan los puertos de entrada. Orquestan el flujo de datos entre los puertos de salida y el dominio. No contienen lógica de bajo nivel (como SQL o HTTP).

### 3. 📂 Capa de Infraestructura (`infrastructure`)
Contiene los detalles de implementación tecnológica.
- **Inbound Adapters**: Controladores REST (`AuthController`, `PatientController`) que transforman JSON en llamadas al dominio.
- **Outbound Adapters**: 
    - **Persistencia**: Implementa los repositorios usando Spring Data JPA.
    - **Seguridad**: Implementaciones de JWT y BCrypt.
    - **External**: Clientes REST para conectarse a otros microservicios.
- **Configuración (`config`)**: Donde ocurre la magia de Spring; conexión de beans y configuración de filtros de seguridad.


---

## 🚀 Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.2.1**
- **Spring Security & JWT**: Para autenticación y autorización basada en roles.
- **Spring Data JPA**: Para la persistencia de datos.
- **Flyway**: Para el control de versiones y migraciones de la base de datos.
- **PostgreSQL / H2**: Base de datos de producción y de desarrollo/test.
- **Micrometer & Prometheus**: Para monitoreo y métricas de negocio.
- **Lombok**: Para reducir el código repetitivo (Boilerplate).
- **OpenAPI (Swagger)**: Para documentación interactiva de la API.

---

## 🛠️ Flujo de Trabajo y Características

### 🔐 Seguridad y Autenticación
El sistema utiliza **JSON Web Tokens (JWT)** para proteger los endpoints.
- **Registro y Login**: Gestionados por `AuthService`.
- **Roles**: Soporta `ROLE_PACIENTE`, `ROLE_MEDICO` y `ROLE_ADMIN`.
- **Métricas**: Se registran intentos de login exitosos y fallidos mediante `MeterRegistry`.

### 📅 Gestión de Citas (`Appointments`)
El flujo de creación de una cita incluye validación externa:
1. El sistema recibe una solicitud de cita.
2. Se comunica con un servicio externo de seguros (`insurance-validation-mock-service`).
3. Si el seguro es válido y la cobertura es suficiente, la cita se programa.
4. Las citas futuras y las validaciones de DNI son reglas de negocio estrictas.

### 👥 Gestión de Pacientes
- CRUD completo de pacientes con validaciones de unicidad de DNI.
- Integridad referencial asegurada en la persistencia.

---

## ⚙️ Configuración y Ejecución

### 🐳 Opción 1: Docker Compose (Recomendado)
Esta es la forma más sencilla de levantar todo el ecosistema (Base de datos, Mock de Seguros y el Backend de MedicCenter) con un solo comando.

1. Asegúrate de tener **Docker** y **Docker Compose** instalados.
2. Desde la raíz del proyecto, ejecuta:
   ```bash
   docker-compose up --build
   ```
3. Una vez que los contenedores estén listos, podrás acceder a:
    - **API MedicCenter**: `http://localhost:8080`
    - **Swagger UI**: `http://localhost:8080/swagger-ui.html`
    - **Mock de Seguros**: `http://localhost:8081`

### 💻 Opción 2: Ejecución Local (Manual)
Si prefieres correrlo localmente sin Docker:

1. **Prerrequisitos**: JDK 21, Maven y una instancia de PostgreSQL (o usar el perfil por defecto con H2).
2. **Levantar el Mock de Seguros**:
   ```bash
   cd insurance-validation-mock-service
   ./mvnw spring-boot:run
   ```
3. **Levantar el Backend Principal**:
   ```bash
   # En otra terminal, en la raíz del proyecto
   ./mvnw spring-boot:run
   ```

### Documentación de la API
Una vez iniciada la aplicación, puedes acceder a Swagger UI en:
`http://localhost:8080/swagger-ui.html`

### Monitoreo
Endpoints de Actuator disponibles:
- Métricas: `http://localhost:8080/actuator/metrics`
- Prometheus: `http://localhost:8080/actuator/prometheus`

---

## 🧪 Pruebas
El proyecto incluye pruebas unitarias y de integración:
- **Testcontainers**: Se utilizan contenedores Docker para probar la persistencia con PostgreSQL real durante los tests.
- **Mockito**: Se utiliza para mockear las dependencias en los tests de servicios.

Ejecutar tests:
```bash
./mvnw test
```

---

## 📦 Microservicios Relacionados
Este sistema interactúa con:
- **Insurance Validation Mock**: Un servicio mock para simular la validación de seguros médicos. Debe estar corriendo para que la programación de citas funcione correctamente.
