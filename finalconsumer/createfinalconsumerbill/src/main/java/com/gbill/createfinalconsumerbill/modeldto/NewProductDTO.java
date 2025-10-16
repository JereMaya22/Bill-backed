package com.gbill.createfinalconsumerbill.modeldto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProductDTO {
    @JsonProperty("productoId")
    private Long productoId;
    
    private String sku;
    private String nombre;
    
    @JsonProperty("categoriaId")
    private Long categoriaId;
    
    @JsonProperty("codigoBarras")
    private String codigoBarras;
    
    @JsonProperty("stockMinimo")
    private Integer stockMinimo;
    
    @JsonProperty("stockMaximo")
    private Integer stockMaximo; 
    
    private Double precio;
    private Boolean activo;
    
    @JsonProperty("creadoEn")
    private String creadoEn;
    
    @JsonProperty("actualizadoEn")
    private String actualizadoEn;
}
