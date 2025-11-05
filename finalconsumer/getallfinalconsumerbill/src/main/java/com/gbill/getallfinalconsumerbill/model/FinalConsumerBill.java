package com.gbill.getallfinalconsumerbill.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidad que representa una factura a consumidor final.
 * Componentes principales:
 * - Emisor/Receptor: relaciones 1-1 con persistencia controlada por cascadas.
 * - Items: relación 1-N con eliminación huérfana para mantener consistencia.
 * - Totales/Impuestos: montos calculados en el service.
 * - PDF: ruta a archivo generado post-persistencia.
 * - Promociones: metadatos de promos aplicadas y productos afectados.
 * - Devoluciones: flags/códigos para enlazar factura original y de reemplazo.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "payment")
@EqualsAndHashCode(exclude = "payment")
public class FinalConsumerBill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String generationCode; 
    private String controlNumber; 
    private LocalDateTime billGenerationDate;
    private String account;
    private String paymentCondition;

    //transmitter
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "transmitter_id")
    private Transmitter transmitter;    

    //receiver
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id")
    private Receiver receiver;  

    //products
    @OneToMany(mappedBy = "finalConsumerBill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> products;

    // Totals
    private Double nonTaxedSales;
    private Double exemptSales;
    private Double taxedSales;
    private Double iva;
    private Double perceivedIva;
    private Double withheldIva;
    private Double totalWithIva;

    private String pdfPath;

    //promorions
    private Boolean promotionApplied;
    private String promotionCode;
    private String promotionName;
    private Double promotionDiscount;

    @ElementCollection
    private List<Long> productsWithPromotion;

    @OneToOne(mappedBy = "bill", cascade = CascadeType.ALL)
    private Payment payment;

    //factura devuelta
    private Boolean isReversed = false;
    private String returnBillCode;
    private String originBillCode;

    public FinalConsumerBill(
    Long id,
    String generationCode,
    String controlNumber,
    LocalDateTime billGenerationDate,
    String account,
    String paymentCondition,
    Transmitter transmitter,
    Receiver receiver,
    List<BillItem> products,
    Double nonTaxedSales,
    Double exemptSales,
    Double taxedSales,
    Double iva,
    Double perceivedIva,
    Double withheldIva,
    Double totalWithIva,
    String pdfPath,
    Boolean promotionApplied,
    String promotionCode,
    String promotionName,
    Double promotionDiscount,
    List<Long> productsWithPromotion
) {
    this.id = id;
    this.generationCode = generationCode;
    this.controlNumber = controlNumber;
    this.billGenerationDate = billGenerationDate;
    this.account = account;
    this.paymentCondition = paymentCondition;
    this.transmitter = transmitter;
    this.receiver = receiver;
    this.products = products;
    this.nonTaxedSales = nonTaxedSales;
    this.exemptSales = exemptSales;
    this.taxedSales = taxedSales;
    this.iva = iva;
    this.perceivedIva = perceivedIva;
    this.withheldIva = withheldIva;
    this.totalWithIva = totalWithIva;
    this.pdfPath = pdfPath;
    this.promotionApplied = promotionApplied;
    this.promotionCode = promotionCode;
    this.promotionName = promotionName;
    this.promotionDiscount = promotionDiscount;
    this.productsWithPromotion = productsWithPromotion;
}



}
