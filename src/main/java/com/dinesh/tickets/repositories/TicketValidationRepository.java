package com.dinesh.tickets.repositories;

import com.dinesh.tickets.Domain.entities.TicketValidations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketValidationRepository extends JpaRepository<TicketValidations, UUID> {
}
