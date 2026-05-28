package com.dinesh.tickets.mappers;


import com.dinesh.tickets.Domain.DTO.TicketValidationRequestDTO;
import com.dinesh.tickets.Domain.DTO.TicketValidationResponseDTO;
import com.dinesh.tickets.Domain.entities.TicketValidations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMappers {
    @Mapping(target = "ticketId",source = "ticket.id")
    TicketValidationResponseDTO toTicketValidationResponseDto(TicketValidations ticketValidations);

}
