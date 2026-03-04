package com.example.graphql.controller;

import com.example.graphql.model.Booking;
import com.example.graphql.model.Bus;
import com.example.graphql.model.City;
import com.example.graphql.model.PassengerType;
import com.example.graphql.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BusGraphQLController {
    @Autowired
    private BusService busService;

    // ================= QUERIES =================

    @QueryMapping
    public List<Bus> busesByRoute(@Argument String source,
                                  @Argument String destination) {
        return busService.busesByRoute(source, destination);
    }

    @QueryMapping
    public Bus getBus(@Argument String id) {
        return busService.getBus(id);
    }

    @QueryMapping
    public List<Booking> allBookings() {
        return busService.allBookings();
    }

    @QueryMapping
    public Booking getBooking(@Argument String id) {
        System.out.println("I am in get booking by id");
        return busService.getBooking(id);
    }

    // ================= MUTATIONS =================

    @MutationMapping
    public Bus addBus(@Argument String busNumber,
                      @Argument String source,
                      @Argument String destination,
                      @Argument int totalSeats,
                      @Argument int womenSeats,
                      @Argument int seniorSeats) {

        return busService.addBus(busNumber, source, destination,
                totalSeats, womenSeats, seniorSeats);
    }

    @MutationMapping
    public Booking bookSeat(@Argument String busId,
                            @Argument String passengerName,
                            @Argument PassengerType passengerType) {

        return busService.bookSeat(busId, passengerName, passengerType);
    }

    @MutationMapping
    public String cancelBooking(@Argument String bookingId) {
        return busService.cancelBooking(bookingId);
    }

    @MutationMapping
    public String cancelBus(@Argument String busId) {
        return busService.cancelBus(busId);
    }



    // =========================
    // FIELD RESOLVERS
    // =========================

    @SchemaMapping(typeName = "Bus", field = "totalAvailableSeats")
    public int totalAvailableSeats(Bus bus) {
        return bus.getAvailableNormalSeats()
                + bus.getAvailableWomenSeats()
                + bus.getAvailableSeniorSeats();
    }
}
