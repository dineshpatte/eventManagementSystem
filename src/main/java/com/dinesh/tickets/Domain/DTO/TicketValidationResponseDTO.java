package com.dinesh.tickets.Domain.DTO;

import com.dinesh.tickets.Domain.entities.TicketValidationStatusEnum;
import com.dinesh.tickets.Domain.entities.TicketValidations;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationResponseDTO {
    private UUID ticketsId;
    private TicketValidationStatusEnum status;


}
