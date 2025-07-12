package com.example.airline;

import com.example.airline.model.Customer;
import com.example.airline.model.Flight;
import com.example.airline.repository.CustomerRepository;
import com.example.airline.repository.FlightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {
    private final FlightRepository flightRepository;
    private final CustomerRepository customerRepository;

    public DataLoader(FlightRepository flightRepository, CustomerRepository customerRepository) {
        this.flightRepository = flightRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        Flight flight1 = new Flight();
        flight1.setOrigin("NYC");
        flight1.setDestination("LAX");
        flight1.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight1.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(6));
        flight1.setAvailableSeats(5);
        flightRepository.save(flight1);

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customerRepository.save(customer);
    }
}
