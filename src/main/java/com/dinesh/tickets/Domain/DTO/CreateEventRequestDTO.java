package com.dinesh.tickets.Domain.DTO;

import com.dinesh.tickets.Domain.entities.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDTO {
    @NotBlank(message = "event name is required")
    private String name;

    private LocalDateTime start;
    private LocalDateTime end;
    @NotBlank(message = "venue details are required")
    private String venue;
    private LocalDateTime saleStart;
    private LocalDateTime saleEnd;
    @NotNull(message = "status is required")
    private EventStatusEnum status;
    @NotEmpty(message = "At least  one ticket type is required ")
    @Valid
    private List<CreateTicketTypeRequestDTO> ticketTypes;
}
