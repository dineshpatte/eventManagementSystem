package com.dinesh.tickets.services.impl;

import com.dinesh.tickets.Domain.entities.Ticket;
import com.dinesh.tickets.Domain.entities.TicketStatusEnum;
import com.dinesh.tickets.Domain.entities.TicketType;
import com.dinesh.tickets.Domain.entities.User;
import com.dinesh.tickets.exceptions.TicketSoldOutException;
import com.dinesh.tickets.exceptions.TicketTypeNotFoundException;
import com.dinesh.tickets.exceptions.UserNotFoundException;
import com.dinesh.tickets.repositories.QrCodeRepository;
import com.dinesh.tickets.repositories.TicketRepository;
import com.dinesh.tickets.repositories.TicketTypeRepository;
import com.dinesh.tickets.repositories.UserRepository;
import com.dinesh.tickets.services.QrCodeService;
import com.dinesh.tickets.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@AllArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeRepository qrCodeRepository;
    private final QrCodeService qrCodeService;

    @Override
    public Ticket purchaseTicket(UUID userId,UUID ticketTypeId)  {

    User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(String.format("User with id %s not found", userId)));


    TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(()->new TicketTypeNotFoundException(String.format("Ticket type with id %s not found",ticketTypeId)));


    int purchasedTickets = ticketRepository.CountTicketsById(ticketType.getId());

    Integer totalAvailable = ticketType.getTotalAvailableTickets();

    if(purchasedTickets+1>totalAvailable){
        throw  new TicketSoldOutException();
    }

    Ticket ticket =  new Ticket();
    ticket.setStatus(TicketStatusEnum.PURCHASED);
    ticket.setTicketType(ticketType);
    ticket.setPurchaser(user);

    Ticket savedTicket =  ticketRepository.save(ticket);
    qrCodeService.generateQrCode(savedTicket);
   return ticketRepository.save(savedTicket);


    }
}
