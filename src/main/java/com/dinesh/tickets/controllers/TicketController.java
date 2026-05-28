package com.dinesh.tickets.controllers;


import com.dinesh.tickets.Domain.DTO.GetTicketResponseDTO;
import com.dinesh.tickets.Domain.DTO.ListTicketResponseDTO;
import com.dinesh.tickets.mappers.TicketMapper;
import com.dinesh.tickets.services.QrCodeService;
import com.dinesh.tickets.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private QrCodeService qrCodeService;

    public Page<ListTicketResponseDTO> listTickets(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {

        UUID userId = parseUserId(jwt);

        return ticketService.listTicketsForUser(userId, pageable).map(ticketMapper::toListTicketResponseDto);

    }


    @GetMapping(path = "/{ticketId}")
    public ResponseEntity<GetTicketResponseDTO> getTicket(  @AuthenticationPrincipal Jwt jwt,@PathVariable("ticketId") UUID ticketId) {
        UUID userId = parseUserId(jwt);
       return ticketService.getTicketsForUser(userId, ticketId).map(ticketMapper::toGetTicketResponseDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getQrCode(@AuthenticationPrincipal Jwt jwt,@PathVariable UUID ticketId){
        UUID userId = parseUserId(jwt);

       byte[] qrCodeImage =  qrCodeService.getQrImageForUserAndTicket(userId, ticketId);
       return ResponseEntity.ok().body(qrCodeImage);

    }







    private UUID parseUserId(Jwt jwt){
        return UUID.fromString(jwt.getSubject());
    }
}
