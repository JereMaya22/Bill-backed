package com.gbill.createfinalconsumerbill.modeldto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse extends RuntimeException{

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String paht;
    private List<String> details;

}
