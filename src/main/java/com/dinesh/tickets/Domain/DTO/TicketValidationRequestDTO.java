package com.dinesh.tickets.Domain.DTO;


import com.dinesh.tickets.Domain.entities.TicketValidationMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequestDTO {
    private UUID id;
    private TicketValidationMethodEnum method;

}
