package com.gbill.getallbill.modeldto;

import com.gbill.getallbill.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillAllDTO {
    private String codeGenerator;
    private Date date;

    //Emisor
    private String companyName;
    private Long companyNIT;
    private String companyAddress;
    private String companyPhone;
    private String companyEmail;

    //Receptor
    private String clientName;
    private String clientDUI;
    private int clientPhone;
    private String clientEmail;

    //products
    private List<Product> listproducts;
}
