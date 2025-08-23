package com.gbill.getIdBill.modeldto;

import com.gbill.getIdBill.model.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowBill {
    private String codeGenerator;
    private Date date;

    //Emisor
    private String companyName;
    private String companyNIT;
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
