package com.gbill.createfinalconsumerbill.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.gbill.createfinalconsumerbill.model.Receiver;

/**
 * Cliente Feign para el microservicio de clientes.
 * Endpoints expuestos:
 * - getReceiverById: obtiene un cliente por id (usado para enriquecer receiver por customerId).
 * - searchReceivers: búsqueda por filtro (uso potencial para autocompletar).
 * Consideraciones:
 * - El contrato remoto retorna campos en español (nombre, apellido, correo, telefono, dui),
 *   mapeados en las entidades con @JsonProperty donde aplica.
 */
@FeignClient(name = "clients-service", url = "${app.client.clients}")
public interface Clientsclient {

    @GetMapping("/api/clientes")
    public Receiver getReceiver();

    @GetMapping("/api/clientes/{id}")
    Receiver getReceiverById(@PathVariable Long id);

    @GetMapping("/api/clientes/buscar")
    List<Receiver> searchReceivers(@RequestParam("filtro") String filtro);

}
