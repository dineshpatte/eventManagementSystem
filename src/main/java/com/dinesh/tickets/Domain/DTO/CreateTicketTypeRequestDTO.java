package com.dinesh.tickets.Domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

@NoArgsConstructor
public class CreateTicketTypeRequestDTO {

    @NotBlank(message = "ticket type name is required")
    private String name;
    @NotNull(message = "price should not be zero")
    @PositiveOrZero(message = "price should not be negitive")
    private Double price;
    private String Description;
    private Integer totalAvailable;
}
