package com.gbill.createfinalconsumerbill.modeldto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFinalConsumerBillDTO {
    @NotBlank(message = "La condicion de pago no puede ir vacia")
    private String paymentCondition;
    //transmitter
    private String companyName;
    private String companyDocument; // NIT
    private String companyAddress;
    private String companyEmail;
    private String companyPhone;    

    //receiver
    private String customerName;
    private String customerDocument;
    private String customerAddress;
    private String customerEmail;
    private String customerPhone;  

    //products
    @NotEmpty(message = "Debe llevar almenos llevar un producto")
    private List<CreateBillItemRequestDTO> products;

    // Totals
    @PositiveOrZero(message = "Las ventas no gravadas deben ser mayor o igual a 0")
    private Double nonTaxedSales;
    @PositiveOrZero(message = "Las ventas exentas deben ser mayor o igual a 0")
    private Double exemptSales;
    @PositiveOrZero(message = "Las ventas gravadas deben ser mayor o igual a 0")
    private Double taxedSales;
    @PositiveOrZero(message = "El iva debe de ser mayor 0")
    private Double perceivedIva;
    private Double withheldIva;
}
