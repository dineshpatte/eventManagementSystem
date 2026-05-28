package com.dinesh.tickets.services;

import com.dinesh.tickets.Domain.entities.Ticket;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.Optional;
import java.util.UUID;

public interface TicketService {
    Page<Ticket> listTicketsForUser(UUID purchaserId, Pageable pageable);

    Optional<Ticket> getTicketsForUser(UUID purchaserId, UUID id);
}
