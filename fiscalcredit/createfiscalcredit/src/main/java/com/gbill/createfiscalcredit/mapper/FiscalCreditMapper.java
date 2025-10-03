package com.gbill.createfiscalcredit.mapper;

import com.gbill.createfiscalcredit.model.FiscalCredit;
import com.gbill.createfiscalcredit.model.FiscalCreditItem;
import com.gbill.createfiscalcredit.modeldto.CreateFiscalCreditDTO;
import com.gbill.createfiscalcredit.modeldto.CreateFiscalCreditItemDTO;
import com.gbill.createfiscalcredit.modeldto.ShowProductDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class FiscalCreditMapper {

    /**
     * Convierte un DTO de creación a una entidad FiscalCredit
     * @param dto DTO con los datos del crédito fiscal
     * @param productMap Mapa de productos obtenidos del inventario
     */
    public static FiscalCredit toEntity(CreateFiscalCreditDTO dto, Map<Long, ShowProductDTO> productMap) {
        FiscalCredit fiscalCredit = new FiscalCredit();
        
        // Generar código de generación (36 caracteres - UUID)
        fiscalCredit.setGenerationCode(UUID.randomUUID().toString());
        
        // Generar número de control (31 caracteres - DTE-03-yyyyMMdd-número)
        String controlNumber = "DTE-03-" + 
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + 
            "-" + String.format("%015d", ThreadLocalRandom.current().nextLong(1_000_000_000_000_000L));
        fiscalCredit.setControlNumber(controlNumber);
        
        // Datos del emisor (empresa)
        fiscalCredit.setCompanyName(dto.getCompanyName());
        fiscalCredit.setCompanyDocument(dto.getCompanyDocument());
        fiscalCredit.setCompanyAddress(dto.getCompanyAddress());
        fiscalCredit.setCompanyPhone(dto.getCompanyPhone());
        fiscalCredit.setCompanyEmail(dto.getCompanyEmail());
        
        // Datos del receptor (cliente)
        fiscalCredit.setCustomerName(dto.getCustomerName());
        fiscalCredit.setCustomerDocument(dto.getCustomerDocument());
        fiscalCredit.setCustomerAddress(dto.getCustomerAddress());
        fiscalCredit.setCustomerPhone(dto.getCustomerPhone());
        fiscalCredit.setCustomerEmail(dto.getCustomerEmail());
        
        // Información de factura
        fiscalCredit.setBillGenerationDate(LocalDateTime.now());
        fiscalCredit.setAccount(dto.getAccount());
        fiscalCredit.setPaymentCondition(dto.getPaymentCondition());
        
        // Convertir items usando los productos del inventario
        if (dto.getProducts() != null && !dto.getProducts().isEmpty()) {
            List<FiscalCreditItem> items = new ArrayList<>();
            for (CreateFiscalCreditItemDTO itemDTO : dto.getProducts()) {
                FiscalCreditItem item = toItemEntity(itemDTO, productMap);
                item.setFiscalCredit(fiscalCredit);
                items.add(item);
            }
            fiscalCredit.setProducts(items);
        }
        
        // Calcular totales
        calculateTotals(fiscalCredit);
        
        return fiscalCredit;
    }

    /**
     * Convierte un DTO de item a entidad FiscalCreditItem
     * @param dto DTO con ID y cantidad del producto
     * @param productMap Mapa de productos del inventario
     */
    public static FiscalCreditItem toItemEntity(CreateFiscalCreditItemDTO dto, Map<Long, ShowProductDTO> productMap) {
        FiscalCreditItem item = new FiscalCreditItem();
        
        // Obtener producto del inventario
        ShowProductDTO product = productMap.get(dto.getProductId());
        if (product == null) {
            throw new RuntimeException("Producto con ID " + dto.getProductId() + " no encontrado en inventario");
        }
        
        // Datos del producto desde el inventario
        item.setProductId(product.getId());
        item.setName(product.getName());
        item.setPrice(product.getPrice());
        item.setRequestedQuantity(dto.getRequestedQuantity());
        
        // Los 3 campos nuevos (ventas por tipo)
        item.setNonTaxableSales(dto.getNonTaxableSales() != null ? dto.getNonTaxableSales() : 0.0);
        item.setExemptSales(dto.getExemptSales() != null ? dto.getExemptSales() : 0.0);
        item.setTaxableSales(dto.getTaxableSales() != null ? dto.getTaxableSales() : 0.0);
        
        // Calcular subtotal del item (suma de todos los tipos de ventas)
        Double subTotal = item.getNonTaxableSales() + item.getExemptSales() + item.getTaxableSales();
        item.setSubTotal(subTotal);
        
        return item;
    }

    /**
     * Calcula los totales del crédito fiscal
     */
    private static void calculateTotals(FiscalCredit fiscalCredit) {
        Double totalNonTaxed = 0.0;
        Double totalExempt = 0.0;
        Double totalTaxed = 0.0;
        
        // Sumar todos los items
        if (fiscalCredit.getProducts() != null) {
            for (FiscalCreditItem item : fiscalCredit.getProducts()) {
                totalNonTaxed += item.getNonTaxableSales();
                totalExempt += item.getExemptSales();
                totalTaxed += item.getTaxableSales();
            }
        }
        
        // Asignar totales por tipo de venta
        fiscalCredit.setNonTaxedSales(totalNonTaxed);
        fiscalCredit.setExemptSales(totalExempt);
        fiscalCredit.setTaxedSales(totalTaxed);
        
        // Subtotal = suma de todas las ventas (sin IVA)
        Double subtotal = totalNonTaxed + totalExempt + totalTaxed;
        fiscalCredit.setSubtotal(subtotal);
        
        // Calcular IVA (13% solo sobre ventas gravadas)
        Double ivaAmount = totalTaxed * 0.13;
        fiscalCredit.setIva(ivaAmount);
        
        // IVA percibido (por ahora igual al IVA calculado)
        fiscalCredit.setPerceivedIva(ivaAmount);
        
        // IVA retenido (por defecto 0, se puede calcular según reglas específicas)
        fiscalCredit.setWithheldIva(0.0);
        
        // Total con IVA = subtotal + IVA percibido - IVA retenido
        Double totalWithIva = subtotal + fiscalCredit.getPerceivedIva() - fiscalCredit.getWithheldIva();
        fiscalCredit.setTotalWithIva(totalWithIva);
    }
}
