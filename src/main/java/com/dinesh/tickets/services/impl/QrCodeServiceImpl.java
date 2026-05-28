package com.dinesh.tickets.services.impl;

import com.dinesh.tickets.Domain.entities.QRcodeStatusEnum;
import com.dinesh.tickets.Domain.entities.QrCode;
import com.dinesh.tickets.Domain.entities.Ticket;
import com.dinesh.tickets.exceptions.QrCodeGenerationException;
import com.dinesh.tickets.exceptions.QrCodeNotFoundException;
import com.dinesh.tickets.repositories.QrCodeRepository;
import com.dinesh.tickets.services.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {
    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;
    private final QrCodeService qrCodeService;
    private final QRCodeWriter qrCodeWriter;
    private final QrCodeRepository qrCodeRepository;
    @Override
    public QrCode generateQrCode(Ticket ticket) {
            try {
                UUID uniqueId = UUID.randomUUID();

                String QrCodeImage = generateQrCodeImage(uniqueId);

                QrCode qrCode = new QrCode();
                qrCode.setId(uniqueId);
                qrCode.setStatus(QRcodeStatusEnum.ACTIVE);
                qrCode.setValue(QrCodeImage);
                qrCode.setTickets(ticket);


              return qrCodeRepository.saveAndFlush(qrCode);


            }
            catch(IOException | WriterException ex) {
                throw new QrCodeGenerationException("failed to generate qr code", ex);
            }


    }

    @Override
    public byte[] getQrImageForUserAndTicket(UUID userID, UUID ticketID) {
       QrCode qrCode =  qrCodeRepository.findByTicketIdAndTicketPurchaseId(ticketID, userID).orElseThrow(QrCodeNotFoundException::new);

       try{
           return Base64.getDecoder().decode(qrCode.getValue());
       }
       catch(IllegalArgumentException ex) {
           log.error("invalid Base64 QrCode for the ticket Id:{}", ticketID,ex);
           throw new QrCodeNotFoundException();
       }

    }

    private String generateQrCodeImage(UUID uniqueId) throws WriterException,IOException {
    BitMatrix bitMatrix =  qrCodeWriter.encode(
             uniqueId.toString(),
             BarcodeFormat.QR_CODE,
             QR_WIDTH,
             QR_HEIGHT

     );
    BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

    try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
        ImageIO.write(qrCodeImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        return Base64.getEncoder().encodeToString(imageBytes);
    }
    }
}
