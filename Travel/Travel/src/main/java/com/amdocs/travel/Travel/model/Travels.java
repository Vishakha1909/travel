package com.amdocs.travel.Travel.model;

import jakarta.persistence.*;

@Entity
public class Travels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelId;

    @ManyToOne
    @JoinColumn(name = "city_source_id")
    private City sourceCity;

    @ManyToOne
    @JoinColumn(name = "city_destination_id")
    private City destinationCity;

    @ManyToOne
    @JoinColumn(name = "transport_mode_id")
    private Transport transportMode;

    private int timeTaken;
    private double travelPrice;

    public Travels() {

    }

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public City getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(City sourceCity) {
        this.sourceCity = sourceCity;
    }

    public City getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(City destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Transport getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(Transport transportMode) {
        this.transportMode = transportMode;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public double getTravelPrice() {
        return travelPrice;
    }

    public void setTravelPrice(double travelPrice) {
        this.travelPrice = travelPrice;
    }
}
