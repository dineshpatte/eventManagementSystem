package com.dinesh.tickets.mappers;


import com.dinesh.tickets.Domain.CreateEventRequest;
import com.dinesh.tickets.Domain.CreateTicketTypeRequest;
import com.dinesh.tickets.Domain.DTO.*;
import com.dinesh.tickets.Domain.UpdateEventRequest;
import com.dinesh.tickets.Domain.UpdateTicketTypeRequest;
import com.dinesh.tickets.Domain.entities.Event;
import com.dinesh.tickets.Domain.entities.TicketType;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDTO dto);
    CreateEventRequest fromDto(CreateEventRequestDTO dto);
    CreateEventResponseDTO toDto(Event event);

    ListEventsTicketTypeResponseDTO toDto(TicketType ticketType);
    ListEventResponseDTO toListEventDto(Event event);

    GetEventResponseDTO toGetEventDetailsDto(Event event);
    GetEventTicketTypeResponseDTO toGetEventDetailsTicketTypeDto(Event event);

    UpdateTicketTypeRequest fromUpdateTicketTypeDto(UpdateTicketTypeRequestDTO dto);

    UpdateEventRequest fromUpdateEventDto(@Valid UpdateEventRequestDTO dto);

    UpdateTicketTypeResponseDTO toUpdateTicketTypeResponseDto(Event event);

    UpdateTicketTypeRequestDTO toUpdateTicketTypeRequestDto(Event event);
    UpdateEventResponseDTO toUpdateEventResponseDto(Event event);

    UpdateEventRequestDTO toUpdateEventRequestDto(Event event);

    ListPublishedEventsResponseDTO toListPublishedEventsDto(Event event);

    GetPublishedEventDetailsResponseDTO toGetPublishedEventDetailsDto(Event event);
    GetPublishedEventTicketTypeResponseDTO toGetPublishedEventTicketTypeDto(TicketType ticketType);



}
