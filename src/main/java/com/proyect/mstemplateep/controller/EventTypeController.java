package com.proyect.mstemplateep.controller;

import com.proyect.mstemplateep.model.EventType;
import com.proyect.mstemplateep.service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/event-types")
public class EventTypeController {

    @Autowired
    private final EventTypeService eventTypeService;

    @Autowired
    public EventTypeController(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    @GetMapping
    public ResponseEntity<List<EventType>> getAllEventTypes() {
        List<EventType> eventTypes = eventTypeService.getAllEventTypes();
        return ResponseEntity.ok(eventTypes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventType> getEventTypeById(@PathVariable Integer id) {
        Optional<EventType> eventType = eventTypeService.getEventTypeById(id);
        return eventType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventType> createService(@RequestBody EventType eventType) {
        try {
            EventType savedService = eventTypeService.saveEventType(eventType);
            return ResponseEntity.ok(savedService);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
