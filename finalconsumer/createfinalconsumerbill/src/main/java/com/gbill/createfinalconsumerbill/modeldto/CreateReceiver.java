package com.gbill.createfinalconsumerbill.modeldto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReceiver {
    private String customerName;
    private String customerLastname;
    @Pattern(regexp = "^\\d{8}-\\d$", message = "El formato del documento del cliente debe ser 99999999-9")
    private String customerDocument;
    private String customerAddress;
    private String customerEmail;
    @Pattern(regexp = "^(\\d{8}|\\d{4}-\\d{4})$", message = "El formato del teléfono del cliente debe ser 99999999 o 9999-9999")
    private String customerPhone;
}
