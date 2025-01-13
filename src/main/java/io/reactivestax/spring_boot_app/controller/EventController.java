package io.reactivestax.spring_boot_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reactivestax.spring_boot_app.dto.EventDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
// @Validated
public class EventController {

    @PostMapping
    public ResponseEntity<String> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        // Process the event
        return ResponseEntity.ok("Event created successfully");
    }
}
