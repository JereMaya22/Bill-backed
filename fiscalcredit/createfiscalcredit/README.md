# Fiscal Credit Service

Servicio para la creación de créditos fiscales.

## Descripción

Este microservicio maneja la creación de créditos fiscales con toda la información requerida por las autoridades tributarias de El Salvador.

## Características

- ✅ Generación automática de código de generación (36 caracteres UUID)
- ✅ Generación automática de número de control (formato: DTE-03-yyyyMMdd-número)
- ✅ Datos completos del emisor (empresa)
- ✅ Datos completos del receptor (cliente)
- ✅ Manejo de productos con ventas gravadas, exentas y no sujetas
- ✅ **Integración con servicio de inventario (obtiene precios automáticamente)**
- ✅ Cálculo automático de IVA (13%)
- ✅ Validación de token de autenticación
- ✅ Uso de OpenFeign para comunicación entre microservicios

## Tecnologías

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Cloud OpenFeign
- PostgreSQL
- Lombok
- Maven

## Configuración

### Base de datos
Configurar en `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://host:puerto/database
spring.datasource.username=user
spring.datasource.password=password
```

### Puerto
```properties
server.port=8081
```

## Endpoints

### POST /api/fiscal-credit
Crea un nuevo crédito fiscal.

**Headers:**
```
Authorization: Bearer <token>
Content-Type: application/json
```

**Body:**
```json
{
  "companyName": "Empresa S.A.",
  "companyDocument": "0614-123456-001-2",
  "companyAddress": "San Salvador",
  "companyPhone": "2222-3333",
  "companyEmail": "info@empresa.com",
  "customerName": "Cliente",
  "customerDocument": "03456789-0",
  "customerAddress": "Santa Tecla",
  "customerPhone": "7777-8888",
  "customerEmail": "cliente@email.com",
  "paymentCondition": "Contado",
  "products": [
    {
      "productId": 1,
      "requestedQuantity": 2,
      "nonTaxableSales": 0.00,
      "exemptSales": 0.00,
      "taxableSales": 200.00
    }
  ]
}
```

**Nota:** Los campos `name` y `price` se obtienen automáticamente del servicio de inventario.

**Response:** 201 Created
```json
{
  "id": 1,
  "generationCode": "550e8400-e29b-41d4-a716-446655440000",
  "controlNumber": "DTE-03-20251003-123456789012345",
  "companyName": "Empresa S.A.",
  ...
}
```

## Ejecución

```bash
mvn spring-boot:run
```

## Estructura del proyecto

```
createfiscalcredit/
├── controller/      # Endpoints REST
├── service/         # Lógica de negocio
├── repository/      # Acceso a datos
├── model/           # Entidades JPA
├── modeldto/        # DTOs
├── mapper/          # Conversión DTO ↔ Entity
├── clients/         # Clientes HTTP externos
└── exception/       # Excepciones personalizadas
```
