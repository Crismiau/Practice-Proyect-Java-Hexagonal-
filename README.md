# 🏥 MedicCenter - Sistema de Gestión de Citas Médicas

Este proyecto es una solución integral para la gestión de centros médicos, permitiendo la administración eficiente de pacientes, citas y seguridad. Diseñado bajo los principios de **Arquitectura Hexagonal (Puertos y Adaptadores)**, garantiza un sistema altamente desacoplado, mantenible y orientado a pruebas.

---

## 🏗️ Arquitectura del Sistema

El proyecto implementa una **Arquitectura Hexagonal**, organizando la lógica de negocio en el núcleo y dejando los detalles tecnológicos en la periferia.

```text
       +---------------------------------------------------------+
       |                  Infrastructure Layer                   |
       |    (Adapters: Controllers, Repositories, Clients)       |
       |                                                         |
       |       +-----------------------------------------+       |
       |       |           Application Layer             |       |
       |       |          (Services, Use Cases)          |       |
       |       |                                         |       |
       |       +-------------------------+               |       |
       |       |      Domain Layer       |               |       |
       |       |   (Models, Ports, Ex)   |               |       |
       |       +-------------------------+               |       |
       |       +-----------------------------------------+       |
       +---------------------------------------------------------+
```

### Capas del Proyecto

1. **Domain (Núcleo):** Contiene la lógica pura del negocio (POJOs), interfaces de puertos de entrada/salida y excepciones personalizadas. Sin dependencias de frameworks.
2. **Application:** Implementa los casos de uso definidos en el dominio, orquestando la comunicación entre la infraestructura y el núcleo.
3. **Infrastructure:** Implementaciones técnicas (Spring Data JPA, JWT, Controladores REST, Clientes para microservicios externos).

---

## 🚀 Stack Tecnológico

- **Backend:** Java 21 & Spring Boot 3.2.1
- **Seguridad:** Spring Security & JWT (JSON Web Tokens)
- **Base de Datos:** PostgreSQL 15 & H2 (para tests)
- **Persistencia:** Spring Data JPA & Flyway (Migraciones)
- **Monitoreo:** Micrometer, Prometheus & Actuator
- **Documentación:** SpringDoc OpenAPI (Swagger UI)
- **Containerización:** Docker & Docker Compose
- **Calidad:** JUnit 5, Mockito & Testcontainers

---

## 🛠️ Instalación y Configuración

### Prerrequisitos

- **Docker** y **Docker Compose** (Altamente recomendado)
- **JDK 21** (Si deseas ejecutarlo de forma manual)
- **Maven 3.9+** (Si deseas compilar manualmente)

---

### Opción 1: Ejecución con Docker (Recomendado) 🐳

Este método levanta automáticamente la base de datos, el servicio de seguros y la aplicación principal.

1. Clona el repositorio.
2. Sitúate en la carpeta del proyecto:
   ```bash
   cd MedicCenter
   ```
3. Ejecuta el comando:
   ```bash
   docker-compose up --build
   ```
4. **Servicios levantados:**
   - **App Principal:** [http://localhost:8080](http://localhost:8080)
   - **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - **Mock de Seguros:** [http://localhost:8081](http://localhost:8081)
   - **PostgreSQL:** `localhost:5433` (Credenciales: `user_medic`/`password_medic`)

---

### Opción 2: Ejecución Manual (Desarrollo) 💻

Si deseas correr los servicios por separado para desarrollo:

#### 1. Iniciar la Base de Datos

Puedes usar Docker solo para la BD:

```bash
docker run --name mediccenter-db -e POSTGRES_DB=medic_center -e POSTGRES_USER=user_medic -e POSTGRES_PASSWORD=password_medic -p 5433:5432 -d postgres:15-alpine
```

#### 2. Iniciar el Servicio de Seguros (Mock)

Este servicio es obligatorio para la validación de coberturas.

```bash
cd insurance-validation-mock-service
./mvnw spring-boot:run
```

#### 3. Iniciar la Aplicación Principal

```bash
cd ..
./mvnw spring-boot:run
```

---

## 🧪 Pruebas y Monitoreo

### Ejecución de Tests

El proyecto utiliza **Testcontainers** para levantar un PostgreSQL real durante los tests de integración.

```bash
./mvnw test
```

### Monitoreo y Métricas

Accede a los endpoints de Actuator para observar el estado del sistema:

- **Salud:** [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)
- **Métricas:** [http://localhost:8080/actuator/metrics](http://localhost:8080/actuator/metrics)
- **Prometheus:** [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)

---

## 🔐 Seguridad y Roles

El acceso a la API está protegido mediante JWT. Debes obtener un token en `/auth/login` o `/auth/register`.

**Roles soportados:**

- `ROLE_PACIENTE`: Usuarios finales del sistema.
- `ROLE_MEDICO`: Personal médico.
- `ROLE_ADMIN`: Administración total del sistema.

**Credenciales de prueba (Post-migración):**

| Usuario | Contraseña | Rol |
| :--- | :--- | :--- |
| `admin` | `admin123` | `ROLE_ADMIN` |
| `medico` | `medico123` | `ROLE_MEDICO` |
| `paciente` | `paciente123` | `ROLE_PACIENTE` |

---

## 📮 API & Documentación

### Postman

En la raíz del proyecto encontrarás el archivo `MedicCenter.postman_collection.json`.

1. Abre Postman.
2. Haz clic en **Import**.
3. Selecciona el archivo mencionado para cargar todos los endpoints, ambientes y ejemplos de peticiones pre-configurados.

### Swagger

Accede a la documentación interactiva en:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 📦 Microservicios

- **MedicCenter:** API Core para gestión de citas y pacientes.
- **Insurance Validation Mock:** Simula la validación de seguros externos para determinar la cobertura del paciente antes de agendar una cita.
