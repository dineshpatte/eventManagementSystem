package com.dinesh.tickets.services;


import com.dinesh.tickets.Domain.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {
    Object generateQrCode(Ticket ticket);

    byte[] getQrImageForUserAndTicket(UUID userID,UUID ticketID );
}
