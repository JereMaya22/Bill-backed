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
    private CreateTransmitter transmitter;    

    //receiver
    private CreateReceiver receiver;  

    //products
    @NotEmpty(message = "Debe llevar almenos llevar un producto")
    private List<CreateBillItemRequestDTO> products;

    // Totals
    private Double withheldIva;
}
