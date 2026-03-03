package com.example.graphql.service;

import com.example.graphql.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusService {

    private final Map<String, Bus> busMap = new HashMap<>();
    private final Map<String, Booking> bookingMap = new HashMap<>();
    private final Map<String, City> cityMap = new HashMap<>();

    private int busCounter = 1;
    private int bookingCounter = 1;
    private int cityCounter = 1;

    // ================= ADD BUS =================

    public Bus addBus(String busNumber,
                      String sourceName,
                      String destinationName,
                      int totalSeats,
                      int womenSeats,
                      int seniorSeats) {

        City source = getOrCreateCity(sourceName);
        City destination = getOrCreateCity(destinationName);

        Bus bus = new Bus();
        bus.setId(String.valueOf(busCounter++));
        bus.setBusNumber(busNumber);
        bus.setSource(source);
        bus.setDestination(destination);
        bus.setTotalSeats(totalSeats);
        bus.setAvailableWomenSeats(womenSeats);
        bus.setAvailableSeniorSeats(seniorSeats);
        bus.setAvailableNormalSeats(totalSeats - womenSeats - seniorSeats);
        bus.setStatus(BusStatus.ACTIVE);

        busMap.put(bus.getId(), bus);
        return bus;
    }

    // ================= BOOK SEAT =================

    public Booking bookSeat(String busId, String passengerName, PassengerType type) {

        Bus bus = busMap.get(busId);

        if (bus == null || bus.getStatus() == BusStatus.CANCELLED) {
            throw new RuntimeException("Bus not available");
        }

        boolean seatBooked = false;

        switch (type) {
            case WOMAN:
                if (bus.getAvailableWomenSeats() > 0) {
                    bus.setAvailableWomenSeats(bus.getAvailableWomenSeats() - 1);
                    seatBooked = true;
                }
                break;

            case SENIOR_CITIZEN:
                if (bus.getAvailableSeniorSeats() > 0) {
                    bus.setAvailableSeniorSeats(bus.getAvailableSeniorSeats() - 1);
                    seatBooked = true;
                }
                break;

            default:
                if (bus.getAvailableNormalSeats() > 0) {
                    bus.setAvailableNormalSeats(bus.getAvailableNormalSeats() - 1);
                    seatBooked = true;
                }
        }

        if (!seatBooked) {
            throw new RuntimeException("No seats available for selected type");
        }

        Booking booking = new Booking();
        booking.setId(String.valueOf(bookingCounter++));
        booking.setPassengerName(passengerName);
        booking.setPassengerType(type);
        booking.setSeatType(type);
        booking.setBus(bus);

        bookingMap.put(booking.getId(), booking);

        return booking;
    }

    // ================= CANCEL BOOKING =================

    public String cancelBooking(String bookingId) {

        Booking booking = bookingMap.remove(bookingId);

        if (booking == null) {
            return "Booking not found";
        }

        Bus bus = booking.getBus();

        switch (booking.getSeatType()) {
            case WOMAN:
                bus.setAvailableWomenSeats(bus.getAvailableWomenSeats() + 1);
                break;
            case SENIOR_CITIZEN:
                bus.setAvailableSeniorSeats(bus.getAvailableSeniorSeats() + 1);
                break;
            default:
                bus.setAvailableNormalSeats(bus.getAvailableNormalSeats() + 1);
        }

        return "Booking cancelled successfully";
    }

    // ================= CANCEL BUS =================

    public String cancelBus(String busId) {
        Bus bus = busMap.get(busId);
        if (bus == null) return "Bus not found";

        bus.setStatus(BusStatus.CANCELLED);
        return "Bus cancelled successfully";
    }

    // ================= QUERIES =================

    public List<Bus> busesByRoute(String source, String destination) {
        List<Bus> result = new ArrayList<>();

        for (Bus bus : busMap.values()) {
            if (bus.getSource().getName().equalsIgnoreCase(source)
                    && bus.getDestination().getName().equalsIgnoreCase(destination)
                    && bus.getStatus() == BusStatus.ACTIVE) {
                result.add(bus);
            }
        }

        return result;
    }

    public Bus getBus(String id) {
        return busMap.get(id);
    }

    public List<Booking> allBookings() {
        return new ArrayList<>(bookingMap.values());
    }

    public Booking getBooking(String id) {
        System.out.println("Requested ID: " + id);
        System.out.println("Available Keys: " + bookingMap.keySet());
        return bookingMap.get(id);
    }

    // ================= HELPER =================

    private City getOrCreateCity(String name) {

        for (City city : cityMap.values()) {
            if (city.getName().equalsIgnoreCase(name)) {
                return city;
            }
        }

        City city = new City(String.valueOf(cityCounter++), name);
        cityMap.put(city.getId(), city);
        return city;
    }
}
