package com.dinesh.tickets.mappers;


import com.dinesh.tickets.Domain.DTO.GetTicketResponseDTO;
import com.dinesh.tickets.Domain.DTO.ListTicketResponseDTO;
import com.dinesh.tickets.Domain.DTO.ListTicketTypeResponseDTO;
import com.dinesh.tickets.Domain.entities.Ticket;
import com.dinesh.tickets.Domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {
    ListTicketTypeResponseDTO toListTicketTypeResponseDto(TicketType ticketType);

    ListTicketResponseDTO toListTicketResponseDto(Ticket ticket);

    @Mapping(target = "price", source = "ticket.ticketType.price")
    @Mapping(target = "description", source = "ticket.ticketType.description")
    @Mapping(target = "eventName", source = "ticket.ticketType.event.name")
    @Mapping(target = "eventVenue", source = "ticket.ticketType.event.venue")
    @Mapping(target = "eventStart", source = "ticket.ticketType.event.start")
    @Mapping(target = "eventEnd", source = "ticket.ticketType.event.end")
    GetTicketResponseDTO toGetTicketResponseDto(Ticket ticket);

}
