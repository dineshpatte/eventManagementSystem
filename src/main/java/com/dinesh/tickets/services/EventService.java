package com.dinesh.tickets.services;

import com.dinesh.tickets.Domain.CreateEventRequest;
import com.dinesh.tickets.Domain.UpdateEventRequest;
import com.dinesh.tickets.Domain.entities.Event;

import com.dinesh.tickets.exceptions.TicketTypeNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);

    Page<Event> listEventForOrganiser(UUID organiserId, Pageable pageable);

    Optional<Event> getEventForOrganizer(UUID organizerId, UUID id);

    Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest event) throws TicketTypeNotFoundException;

    void deleteEventForOrganizer(UUID organizerId, UUID id);

    Page<Event> listPublishedEvents( Pageable pageable);

    Page<Event> searchPublishedEvents(String query, Pageable pageable);

    Optional<Event> getPublishedEvent( UUID id);


}
