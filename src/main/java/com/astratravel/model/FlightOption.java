package com.astratravel.model;

public class FlightOption {
    private String direction; // "Outbound" or "Return"
    private String airline;
    private String airlineCode;
    private String flightNumber;
    private String departureAirport;
    private String arrivalAirport;
    private String departureCity;
    private String arrivalCity;
    private String departureTime;
    private String arrivalTime;
    private String duration;
    private String stops; // "Non-stop" or "1 Stop (DOH - 1h 45m)"
    private String cabinClass; // "Economy Classic", "Premium Economy", "Business"
    private String baggage; // "1x 23kg Checked + 7kg Cabin"
    private double price;
    private String aircraft;
    private String qrCodeData;

    public FlightOption() {}

    public FlightOption(String direction, String airline, String airlineCode, String flightNumber,
                        String departureAirport, String arrivalAirport, String departureCity, String arrivalCity,
                        String departureTime, String arrivalTime, String duration, String stops,
                        String cabinClass, String baggage, double price, String aircraft) {
        this.direction = direction;
        this.airline = airline;
        this.airlineCode = airlineCode;
        this.flightNumber = flightNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.stops = stops;
        this.cabinClass = cabinClass;
        this.baggage = baggage;
        this.price = price;
        this.aircraft = aircraft;
        this.qrCodeData = "TRIPGENIE-" + flightNumber + "-" + direction.toUpperCase();
    }

    // Getters and Setters
    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }

    public String getAirlineCode() { return airlineCode; }
    public void setAirlineCode(String airlineCode) { this.airlineCode = airlineCode; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getDepartureAirport() { return departureAirport; }
    public void setDepartureAirport(String departureAirport) { this.departureAirport = departureAirport; }

    public String getArrivalAirport() { return arrivalAirport; }
    public void setArrivalAirport(String arrivalAirport) { this.arrivalAirport = arrivalAirport; }

    public String getDepartureCity() { return departureCity; }
    public void setDepartureCity(String departureCity) { this.departureCity = departureCity; }

    public String getArrivalCity() { return arrivalCity; }
    public void setArrivalCity(String arrivalCity) { this.arrivalCity = arrivalCity; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getStops() { return stops; }
    public void setStops(String stops) { this.stops = stops; }

    public String getCabinClass() { return cabinClass; }
    public void setCabinClass(String cabinClass) { this.cabinClass = cabinClass; }

    public String getBaggage() { return baggage; }
    public void setBaggage(String baggage) { this.baggage = baggage; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getAircraft() { return aircraft; }
    public void setAircraft(String aircraft) { this.aircraft = aircraft; }

    public String getQrCodeData() { return qrCodeData; }
    public void setQrCodeData(String qrCodeData) { this.qrCodeData = qrCodeData; }
}
