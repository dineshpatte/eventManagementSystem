package com.dinesh.tickets.services.impl;

import com.dinesh.tickets.Domain.entities.Ticket;
import com.dinesh.tickets.Domain.entities.User;
import com.dinesh.tickets.exceptions.UserNotFoundException;
import com.dinesh.tickets.repositories.TicketRepository;
import com.dinesh.tickets.repositories.UserRepository;
import com.dinesh.tickets.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    @Override
    public Page<Ticket> listTicketsForUser(UUID purchaserId, Pageable pageable) {

       User purchaser =  userRepository.findById(purchaserId).orElseThrow(()->new UserNotFoundException(String.format("User with id %s not found",purchaserId)));



        return ticketRepository.findByPurchaserId(purchaser.getId(), pageable);



    }

    @Override
    public Optional<Ticket> getTicketsForUser(UUID purchaserId, UUID id) {

       return ticketRepository.findTicketByIdAndPurchaserId(id, purchaserId);
    }


}
