package com.gbill.createfinalconsumerbill.mapper;

import com.gbill.createfinalconsumerbill.model.Receiver;
import com.gbill.createfinalconsumerbill.modeldto.CreateReceiver;

import shareddtos.billmodule.bill.ShowReceiver;

/**
 * Mapper utilitario para conversión entre DTOs de receptor y entidad persistente.
 * Reglas/Notas:
 * - customerId en CreateReceiver no se persiste en la entidad; se usa para enriquecer desde MS de clientes.
 * - toEntity: construye entidad JPA a partir del DTO de entrada.
 * - toDTO: arma un DTO de creación a partir de la entidad (útil para ecos/ediciones).
 * - toShowReceiverDto: representa receptor para salida/visualización en el contrato compartido.
 */
public class ReceiverMapper {

    public static Receiver toEntity(CreateReceiver dto) {
        // El customerId no se persiste en la entidad, solo se usa para obtener datos del microservicio
        return new Receiver(
            dto.getCustomerName(),
            dto.getCustomerLastname(),
            dto.getCustomerDocument(),
            dto.getCustomerAddress() != null ? dto.getCustomerAddress() : "Dirección no especificada",
            dto.getCustomerEmail(),
            dto.getCustomerPhone()
        );
    }

    public static CreateReceiver toDTO(Receiver receiver) {
        // Mapear todos los campos incluyendo el customerId (que será el id de la entidad o null)
        return new CreateReceiver(
            receiver.getId(),  // customerId
            receiver.getName(),
            receiver.getLastName(),
            receiver.getDocument(),
            receiver.getAddress(),
            receiver.getEmail(),
            receiver.getPhone()
        );
    }

    public static ShowReceiver toShowReceiverDto(Receiver receiver) {
        return new ShowReceiver(
            receiver.getName(),
            receiver.getLastName(),
            receiver.getDocument(),
            receiver.getAddress(),
            receiver.getEmail(),
            receiver.getPhone()
        );
    }

}
