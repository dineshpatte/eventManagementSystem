package com.dinesh.tickets.Domain.DTO;


import com.dinesh.tickets.Domain.entities.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketResponseDTO {
    private UUID id;
    private TicketStatusEnum status;
    private ListTicketTypeResponseDTO ticketType;

}
