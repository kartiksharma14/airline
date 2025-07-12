package com.example.airline.service;

import com.example.airline.model.Booking;
import com.example.airline.model.Customer;
import com.example.airline.model.Flight;
import com.example.airline.repository.BookingRepository;
import com.example.airline.repository.CustomerRepository;
import com.example.airline.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;

    public FlightService(FlightRepository flightRepository,
                         BookingRepository bookingRepository,
                         CustomerRepository customerRepository) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
    }

    public List<Flight> searchFlights(String origin, String destination) {
        return flightRepository.findByOriginAndDestination(origin, destination);
    }

    @Transactional
    public Optional<Booking> bookFlight(Long flightId, Long customerId) {
        Flight flight = flightRepository.findById(flightId).orElse(null);
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (flight == null || customer == null) {
            return Optional.empty();
        }
        if (flight.getAvailableSeats() <= 0) {
            return Optional.empty();
        }
        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setCustomer(customer);
        bookingRepository.save(booking);
        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);
        return Optional.of(booking);
    }

    @Transactional
    public boolean cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            return false;
        }
        Flight flight = booking.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + 1);
        flightRepository.save(flight);
        bookingRepository.delete(booking);
        return true;
    }
}
