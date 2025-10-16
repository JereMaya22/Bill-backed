package com.gbill.createfinalconsumerbill.modeldto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransmitter {
    private String companyName;
    private String companyDocument;
    private String companyAddress;
    private String companyEmail;
    private String companyPhone;
}
