package com.example.airline.controller;

import com.example.airline.model.Booking;
import com.example.airline.model.Flight;
import com.example.airline.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flights")
    public List<Flight> searchFlights(@RequestParam String origin, @RequestParam String destination) {
        return flightService.searchFlights(origin, destination);
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> bookFlight(@RequestBody Map<String, Long> payload) {
        Long flightId = payload.get("flightId");
        Long customerId = payload.get("customerId");
        return flightService.bookFlight(flightId, customerId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Booking failed"));
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        boolean cancelled = flightService.cancelBooking(id);
        if (cancelled) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
