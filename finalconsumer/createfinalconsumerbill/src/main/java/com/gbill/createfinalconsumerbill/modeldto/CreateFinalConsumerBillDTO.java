package com.gbill.createfinalconsumerbill.modeldto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    private Double nonTaxedSales;
    private Double exemptSales;
    private Double taxedSales;
    private Double perceivedIva;
    private Double withheldIva;
}
