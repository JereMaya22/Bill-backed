package com.gbill.createfinalconsumerbill.modeldto;

import java.util.List;

import com.gbill.createfinalconsumerbill.modeldto.payment.PaymentDTO;

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

    //payment
    private PaymentDTO payment;

    public CreateFinalConsumerBillDTO(
    String paymentCondition,
    CreateTransmitter transmitter,
    CreateReceiver receiver,
    List<CreateBillItemRequestDTO> products,
    Double withheldIva
) {
    this.paymentCondition = paymentCondition;
    this.transmitter = transmitter;
    this.receiver = receiver;
    this.products = products;
    this.withheldIva = withheldIva;
}

}
