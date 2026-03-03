package com.example.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bus {

    private String id;
    private String busNumber;
    private City source;
    private City destination;
    private int totalSeats;
    private int availableNormalSeats;
    private int availableWomenSeats;
    private int availableSeniorSeats;
    private BusStatus status;
}