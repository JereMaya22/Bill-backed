# Ejemplos de peticiones para Crédito Fiscal

## Crear Crédito Fiscal

### Request
```http
POST http://localhost:8081/api/fiscal-credit
Content-Type: application/json
Authorization: Bearer tu_token_aqui
```

### Body - Ejemplo 1: Ventas mixtas
```json
{
  "companyName": "Becky's Florist S.A. de C.V.",
  "companyDocument": "0614-123456-001-2",
  "companyAddress": "Calle Principal #123, San Salvador, El Salvador",
  "companyPhone": "2222-3333",
  "companyEmail": "info@beckysflorist.com",
  
  "customerName": "Juan Carlos Pérez López",
  "customerDocument": "03456789-0",
  "customerAddress": "Colonia Escalón, Santa Tecla, La Libertad",
  "customerPhone": "7777-8888",
  "customerEmail": "juan.perez@example.com",
  
  "paymentCondition": "Contado",
  
  "products": [
    {
      "productId": 1,
      "requestedQuantity": 2,
      "nonTaxableSales": 0.00,
      "exemptSales": 0.00,
      "taxableSales": 1600.00
    },
    {
      "productId": 5,
      "requestedQuantity": 10,
      "nonTaxableSales": 0.00,
      "exemptSales": 50.00,
      "taxableSales": 0.00
    },
    {
      "productId": 8,
      "requestedQuantity": 5,
      "nonTaxableSales": 0.00,
      "exemptSales": 0.00,
      "taxableSales": 75.00
    }
  ]
}
```

**Nota:** Los campos `name` y `price` se obtienen automáticamente del servicio de inventario usando el `productId`.

### Body - Ejemplo 2: Solo ventas gravadas
```json
{
  "companyName": "Tecnología El Salvador S.A.",
  "companyDocument": "0614-987654-001-8",
  "companyAddress": "Centro Comercial La Gran Vía, San Salvador",
  "companyPhone": "2555-4444",
  "companyEmail": "ventas@tecnosv.com",
  
  "customerName": "María Fernanda Gómez",
  "customerDocument": "06141234567012",
  "customerAddress": "Residencial Los Pinos, Antiguo Cuscatlán",
  "customerPhone": "7123-4567",
  "customerEmail": "maria.gomez@empresa.com",
  
  "paymentCondition": "Crédito 30 días",
  
  "products": [
    {
      "productId": 101,
      "requestedQuantity": 3,
      "nonTaxableSales": 0.00,
      "exemptSales": 0.00,
      "taxableSales": 3600.00
    },
    {
      "productId": 102,
      "requestedQuantity": 3,
      "nonTaxableSales": 0.00,
      "exemptSales": 0.00,
      "taxableSales": 750.00
    }
  ]
}
```

**Nota:** El backend consulta automáticamente los detalles (nombre y precio) de los productos desde el inventario.

### Response esperada (201 Created)
```json
{
  "id": 1,
  "generationCode": "550e8400-e29b-41d4-a716-446655440000",
  "controlNumber": "DTE-03-20251003-123456789012345",
  "companyName": "Becky's Florist S.A. de C.V.",
  "companyDocument": "0614-123456-001-2",
  "companyAddress": "Calle Principal #123, San Salvador, El Salvador",
  "companyPhone": "2222-3333",
  "companyEmail": "info@beckysflorist.com",
  "customerName": "Juan Carlos Pérez López",
  "customerDocument": "03456789-0",
  "customerAddress": "Colonia Escalón, Santa Tecla, La Libertad",
  "customerPhone": "7777-8888",
  "customerEmail": "juan.perez@example.com",
  "billGenerationDate": "2025-10-03T14:30:00",
  "account": "admin@beckysflorist.com",
  "paymentCondition": "Contado",
  "nonTaxedSales": 0.00,
  "exemptSales": 50.00,
  "taxedSales": 1675.00,
  "subtotal": 1725.00,
  "iva": 217.75,
  "perceivedIva": 217.75,
  "withheldIva": 0.00,
  "totalWithIva": 1942.75,
  "products": [
    {
      "id": 1,
      "productId": 1,
      "name": "Laptop Dell Inspiron 15",
      "requestedQuantity": 2,
      "price": 800.00,
      "subTotal": 1600.00,
      "nonTaxableSales": 0.00,
      "exemptSales": 0.00,
      "taxableSales": 1600.00
    },
    {
      "id": 2,
      "productId": 5,
      "name": "Medicamento Ibuprofeno 400mg",
      "requestedQuantity": 10,
      "price": 5.00,
      "subTotal": 50.00,
      "nonTaxableSales": 0.00,
      "exemptSales": 50.00,
      "taxableSales": 0.00
    },
    {
      "id": 3,
      "productId": 8,
      "name": "Mouse inalámbrico Logitech",
      "requestedQuantity": 5,
      "price": 15.00,
      "subTotal": 75.00,
      "nonTaxableSales": 0.00,
      "exemptSales": 0.00,
      "taxableSales": 75.00
    }
  ]
}
```

## Notas importantes

### Integración con Inventario
- ✅ **El backend consulta automáticamente** los productos desde `https://bill.beckysflorist.site/api/inventory/products/by-ids`
- ✅ Solo necesitas enviar el `productId` y `requestedQuantity`
- ✅ Los campos `name` y `price` se obtienen del servicio de inventario
- ✅ Esto garantiza que los precios siempre estén actualizados

### Cálculo de IVA
- El IVA (13%) se calcula SOLO sobre las **ventas gravadas** (`taxableSales`)
- Las ventas exentas y no sujetas NO pagan IVA
- Ejemplo: Si `taxableSales = $1675.00`, entonces `iva = $1675.00 × 0.13 = $217.75`

### Tipos de ventas por producto
Cada producto debe especificar los montos para cada tipo de venta:
1. **nonTaxableSales** (Ventas no sujetas): Exportaciones, servicios no gravados
2. **exemptSales** (Ventas exentas): Medicinas, canasta básica, libros
3. **taxableSales** (Ventas gravadas): Productos/servicios que pagan IVA normal (13%)

**Importante:** El frontend debe calcular estos montos multiplicando: `cantidad × precio × tipo_de_venta`

### Códigos generados automáticamente
- **generationCode**: UUID de 36 caracteres (ej: `550e8400-e29b-41d4-a716-446655440000`)
- **controlNumber**: Formato `DTE-03-yyyyMMdd-número` de 31 caracteres (ej: `DTE-03-20251003-123456789012345`)

### Autenticación
Debe incluir el header:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```
