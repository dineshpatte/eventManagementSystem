package com.dinesh.tickets.Domain;


import com.dinesh.tickets.Domain.entities.EventStatusEnum;
import com.dinesh.tickets.Domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequest {
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime saleStart;
    private LocalDateTime saleEnd;
    private EventStatusEnum status;

    private List<CreateTicketTypeRequest> ticketTypes = new ArrayList<>();





}
