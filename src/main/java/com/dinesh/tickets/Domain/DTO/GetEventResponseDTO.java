package com.dinesh.tickets.Domain.DTO;

import com.dinesh.tickets.Domain.entities.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor

@NoArgsConstructor
public class GetEventResponseDTO {
    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private List<ListEventsTicketTypeResponseDTO> ticketTypes =  new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
