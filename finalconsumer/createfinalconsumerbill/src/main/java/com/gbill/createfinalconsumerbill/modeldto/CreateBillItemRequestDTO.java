package com.gbill.createfinalconsumerbill.modeldto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBillItemRequestDTO {
    private Long productId;
    private int requestedQuantity;
}
