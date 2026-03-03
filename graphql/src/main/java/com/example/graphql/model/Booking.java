package com.example.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private String id;
    private String passengerName;
    private PassengerType passengerType;

    private Bus bus;

    // Seat actually allocated
    private PassengerType seatType;
}
