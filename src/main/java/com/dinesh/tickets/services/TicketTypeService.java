package com.dinesh.tickets.services;


import com.dinesh.tickets.Domain.entities.Ticket;
import com.dinesh.tickets.Domain.entities.TicketType;

import java.util.UUID;

public interface TicketTypeService {
    Ticket  purchaseTicket(UUID userId, UUID TicketTypeId);

}
