package com.dinesh.tickets.controllers;

import com.dinesh.tickets.Domain.CreateEventRequest;
import com.dinesh.tickets.Domain.DTO.*;
import com.dinesh.tickets.Domain.UpdateEventRequest;
import com.dinesh.tickets.Domain.entities.Event;
import com.dinesh.tickets.exceptions.TicketTypeNotFoundException;
import com.dinesh.tickets.mappers.EventMapper;
import com.dinesh.tickets.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<CreateEventResponseDTO> createEvent(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody CreateEventRequestDTO createEventRequestDTO){

       CreateEventRequest createEventRequest =  eventMapper.fromDto(createEventRequestDTO);

       UUID userId = UUID.fromString(jwt.getSubject());

      Event createdEvent =  eventService.createEvent(userId, createEventRequest);

      CreateEventResponseDTO eventResponseDTO = eventMapper.toDto(createdEvent);

      return  new ResponseEntity<>(eventResponseDTO, HttpStatus.CREATED);


    }

    @GetMapping
    public  ResponseEntity<Page<ListEventResponseDTO>> listEvents(
            @AuthenticationPrincipal Jwt jwt, Pageable pageable
            ){

        UUID organizerId = parseUserId(jwt);
      Page<Event> events =   eventService.listEventForOrganiser(organizerId, pageable);
      Page<ListEventResponseDTO>listEventResponse =  events.map(eventMapper::toListEventDto);

      return new  ResponseEntity<>(listEventResponse, HttpStatus.OK);


    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetEventResponseDTO> getEventDetailsById(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID eventId){
        UUID userId = parseUserId(jwt);
       return eventService.getEventForOrganizer(userId, eventId).map(eventMapper::toGetEventDetailsDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<UpdateEventResponseDTO> updateEvent(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody UpdateEventRequestDTO updateEventRequestDTO, @PathVariable UUID eventId) throws TicketTypeNotFoundException {

        UpdateEventRequest updateEventRequest =  eventMapper.fromUpdateEventDto(updateEventRequestDTO);

        UUID userId = UUID.fromString(jwt.getSubject());

        Event updatedEvent  =  eventService.updateEventForOrganizer(userId,eventId,updateEventRequest);

      UpdateEventResponseDTO updatedEventResponseDTO =  eventMapper.toUpdateEventResponseDto(updatedEvent);

        return  new ResponseEntity<>(updatedEventResponseDTO, HttpStatus.CREATED);


    }

    @DeleteMapping(path = "/{eventId}")

    public ResponseEntity<Void> deleteEvent(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID eventId){

        UUID userId = parseUserId(jwt);
        eventService.deleteEventForOrganizer(userId, eventId);

        return ResponseEntity.noContent().build();
    }




    private UUID parseUserId(Jwt jwt){
       return UUID.fromString(jwt.getSubject());
    }
}
