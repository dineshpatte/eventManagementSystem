package com.dinesh.tickets.repositories;

import com.dinesh.tickets.Domain.entities.QRcodeStatusEnum;
import com.dinesh.tickets.Domain.entities.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {
    Optional<QrCode> findByTicketIdAndTicketPurchaseId(UUID ticketId, UUID ticketPurchaseId);

    Optional<QrCode> findByIdAndStatus(UUID id, QRcodeStatusEnum status);



}
