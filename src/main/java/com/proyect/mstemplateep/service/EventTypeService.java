package com.proyect.mstemplateep.service;

import com.proyect.mstemplateep.model.EventType;
import com.proyect.mstemplateep.repository.EventTypeRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventTypeService {

    @Autowired
    private final EventTypeRepo eventTypeRepo;

    @Autowired
    public EventTypeService(EventTypeRepo eventTypeRepository) {
        this.eventTypeRepo = eventTypeRepository;
    }

    public List<EventType> getAllEventTypes(){
        return eventTypeRepo.findByKilled((byte) 0);
    }

    public Optional<EventType> getEventTypeById(Integer id){
        return eventTypeRepo.findById(id);
    }

    public EventType saveEventType(EventType eventType){
        return eventTypeRepo.save(eventType);
    }
}
