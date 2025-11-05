package com.gbill.createfinalconsumerbill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un renglón (ítem) de la factura.
 * Campos clave:
 * - productId/name: referencia informativa al producto en catálogo.
 * - requestedQuantity/price/subTotal: montos calculados al momento de facturar.
 * Relaciones:
 * - ManyToOne hacia la factura (FinalConsumerBill) con carga LAZY para evitar overhead en listados.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String name;
    private int requestedQuantity;
    private Double price;
    private Double subTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private FinalConsumerBill finalConsumerBill;

    public Double sumSubtotal(Double price, int requestedQuantity){
        // Cálculo simple de subtotal (sin descuentos/impuestos).
        return price * requestedQuantity;
    }

}




