package com.gbill.createfinalconsumerbill.modeldto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    private String companyDocument;
    private String companyAddress;
    private String companyEmail;
    private String companyPhone;    

    //receiver
    private String customerName;
    @Pattern(regexp = "^\\d{8}-\\d$", message = "El formato del documento del cliente debe ser 99999999-9")
    private String customerDocument;
    private String customerAddress;
    private String customerEmail;
    @Pattern(regexp = "^(\\d{8}|\\d{4}-\\d{4})$", message = "El formato del teléfono del cliente debe ser 99999999 o 9999-9999")
    private String customerPhone;  

    //products
    @NotEmpty(message = "Debe llevar almenos llevar un producto")
    private List<CreateBillItemRequestDTO> products;

    // Totals
    private Double withheldIva;
}
