package com.dinesh.tickets.services.impl;

import com.dinesh.tickets.Domain.CreateEventRequest;
import com.dinesh.tickets.Domain.UpdateEventRequest;
import com.dinesh.tickets.Domain.UpdateTicketTypeRequest;
import com.dinesh.tickets.Domain.entities.Event;
import com.dinesh.tickets.Domain.entities.EventStatusEnum;
import com.dinesh.tickets.Domain.entities.TicketType;
import com.dinesh.tickets.Domain.entities.User;
import com.dinesh.tickets.exceptions.EventNotFoundException;
import com.dinesh.tickets.exceptions.EventUpdateException;
import com.dinesh.tickets.exceptions.TicketTypeNotFoundException;
import com.dinesh.tickets.exceptions.UserNotFoundException;
import com.dinesh.tickets.repositories.EventRepository;
import com.dinesh.tickets.repositories.UserRepository;
import com.dinesh.tickets.services.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
      User organizer =   userRepository.findById(organizerId).orElseThrow(()->new UserNotFoundException(toString().formatted("user not found",organizerId)));

        Event EventToCreate = new Event();
    List<TicketType> ticketTypesToCreate =   event.getTicketTypes().stream().map(ticketType->{
          TicketType ticketTypeToCreate = new TicketType();
          ticketTypeToCreate.setName(ticketType.getName());
          ticketTypeToCreate.setPrice(ticketType.getPrice());
          ticketTypeToCreate.setDescription(ticketType.getDescription());
          ticketTypeToCreate.setTotalAvailableTickets(ticketType.getTotalAvailable());
          ticketTypeToCreate.setEvent(EventToCreate);
            return ticketTypeToCreate;
      }).toList();


      EventToCreate.setName(event.getName());
      EventToCreate.setStart(event.getStart());
      EventToCreate.setEnd(event.getEnd());
      EventToCreate.setVenue(event.getVenue());
      EventToCreate.setSalesStart(event.getSaleStart());
      EventToCreate.setSalesEnd(event.getSaleEnd());
      EventToCreate.setStatus(event.getStatus());
      EventToCreate.setOrganizer(organizer);
      EventToCreate.setTicketTypes(ticketTypesToCreate);

     return  eventRepository.save(EventToCreate);


    }

    @Override
    public Page<Event> listEventForOrganiser(UUID organiserId,Pageable
                                             pageable) {
        return  eventRepository.findByOrganizer_id(organiserId,pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID id) {

        return eventRepository.findByIdAndOrganizer_id(id,organizerId);

    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest event) throws TicketTypeNotFoundException {
        if(null==event.getId()){
            throw new EventUpdateException("event id not found");
        }
        if(!id.equals(event.getId())){
            throw new EventUpdateException("cannot update id of an event");
        }

       Event existingEvent =  eventRepository.findByIdAndOrganizer_id(id,organizerId).orElseThrow(()->new EventNotFoundException(String.format("event with id %s not found",id)));
        existingEvent.setName(event.getName());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setSalesStart(event.getSaleStart());
        existingEvent.setSalesEnd(event.getSaleEnd());
        existingEvent.setStatus(event.getStatus());

       Set<UUID> requestTicketTypeIds =  event.getTicketTypes().stream().map(UpdateTicketTypeRequest::getId).filter(Objects::nonNull).collect(Collectors.toSet());

       existingEvent.getTicketTypes().removeIf(existingTicketType->!requestTicketTypeIds.contains(existingTicketType.getId()));

       Map<UUID,TicketType> existingTicketTypesIndex = existingEvent.getTicketTypes().stream().collect(Collectors.toMap(TicketType::getId, ticketType->ticketType));

       for(UpdateTicketTypeRequest ticketType:event.getTicketTypes()){
           if(null == ticketType.getId()){
               //create

               TicketType ticketTypeToCreate = new TicketType();
               ticketTypeToCreate.setName(ticketType.getName());
               ticketTypeToCreate.setPrice(ticketType.getPrice());
               ticketTypeToCreate.setDescription(ticketType.getDescription());
               ticketTypeToCreate.setTotalAvailableTickets(ticketType.getTotalAvailable());
               ticketTypeToCreate.setEvent(existingEvent);
               existingEvent.getTicketTypes().add(ticketTypeToCreate);

           }
           else if (existingTicketTypesIndex.containsKey(ticketType.getId())) {
               TicketType existingTicketType = existingTicketTypesIndex.get(ticketType.getId());
               existingTicketType.setName(ticketType.getName());
               existingTicketType.setPrice(ticketType.getPrice());
               existingTicketType.setDescription(ticketType.getDescription());
               existingTicketType.setTotalAvailableTickets(ticketType.getTotalAvailable());

               
           }
           else{
               throw new TicketTypeNotFoundException(String.format("ticketType with ID '%s' not found",ticketType.getId()));
           }
       }

       return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizerId, UUID id) {

        getEventForOrganizer(organizerId,id).ifPresent(eventRepository::delete);
    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
      return  eventRepository.findByStatus(EventStatusEnum.PUBLISHED,pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return eventRepository.searchEvents(query,pageable);
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID id) {
       return eventRepository.findByIdAndStatus(id,EventStatusEnum.PUBLISHED);
    }


}
