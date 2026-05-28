package com.dinesh.tickets.services;


import com.dinesh.tickets.Domain.entities.QrCode;
import com.dinesh.tickets.Domain.entities.TicketValidations;

import java.util.UUID;

public interface TicketValidationService {
    TicketValidations validateTicketByQrCode(UUID qrCodeId);
    TicketValidations validateTicketByManually(UUID TicketId);
}
