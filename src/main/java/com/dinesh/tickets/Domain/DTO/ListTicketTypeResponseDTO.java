package com.dinesh.tickets.Domain.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketTypeResponseDTO {
    private UUID id;
    private String name;
    private Double price;
}
