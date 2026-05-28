package com.dinesh.tickets.repositories;

import com.dinesh.tickets.Domain.entities.Ticket;
import com.dinesh.tickets.Domain.entities.TicketType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    int CountTicketsById(UUID ticketTypeId);
    Page<Ticket> findByPurchaserId(UUID purchaserId, Pageable pageable);

    Optional<Ticket> findTicketByIdAndPurchaserId(UUID id, UUID purchaserId);


}
