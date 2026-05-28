package com.dinesh.tickets.controllers;

import com.dinesh.tickets.Domain.DTO.GetPublishedEventDetailsResponseDTO;
import com.dinesh.tickets.Domain.DTO.ListPublishedEventsResponseDTO;
import com.dinesh.tickets.Domain.entities.Event;
import com.dinesh.tickets.mappers.EventMapper;
import com.dinesh.tickets.repositories.EventRepository;
import com.dinesh.tickets.services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@AllArgsConstructor
public class PublishedEventController {


    private final EventService eventService;
    private final EventMapper eventMapper;


    @GetMapping
    public ResponseEntity<ListPublishedEventsResponseDTO> listPublishedEvents(@RequestParam(required = false) String q, Pageable pageable) {
        Page<Event> events;
        if(null!=q&&!q.trim().isEmpty()){
          events =   eventService.searchPublishedEvents(q,pageable);
        }
        else{
            events = eventService.listPublishedEvents(pageable);
        }
    return ResponseEntity.ok((ListPublishedEventsResponseDTO) events.map(eventMapper::toListPublishedEventsDto));

    }

    @GetMapping(path = "{eventID}")
    public ResponseEntity<GetPublishedEventDetailsResponseDTO> getPublishedEventDetails(@PathVariable UUID eventId) {

      return  eventService.getPublishedEvent(eventId).map(eventMapper::toGetPublishedEventDetailsDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }
}
