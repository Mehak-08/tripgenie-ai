package com.astratravel.model;

import java.util.List;

public class StayOption {
    private String hotelName;
    private double starRating;
    private String district;
    private String city;
    private double nightlyRate;
    private int totalNights;
    private double totalCost;
    private String checkInDate;
    private String checkOutDate;
    private String roomType;
    private String vibe;
    private String address;
    private String imageUrl;
    private List<String> amenities;
    private double guestScore;
    private int reviewCount;
    private String metroDistance;

    public StayOption() {}

    public StayOption(String hotelName, double starRating, String district, String city,
                      double nightlyRate, int totalNights, String checkInDate, String checkOutDate,
                      String roomType, String vibe, String address, String imageUrl,
                      List<String> amenities, double guestScore, int reviewCount, String metroDistance) {
        this.hotelName = hotelName;
        this.starRating = starRating;
        this.district = district;
        this.city = city;
        this.nightlyRate = nightlyRate;
        this.totalNights = totalNights;
        this.totalCost = nightlyRate * totalNights;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomType = roomType;
        this.vibe = vibe;
        this.address = address;
        this.imageUrl = imageUrl;
        this.amenities = amenities;
        this.guestScore = guestScore;
        this.reviewCount = reviewCount;
        this.metroDistance = metroDistance;
    }

    // Getters and Setters
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public double getStarRating() { return starRating; }
    public void setStarRating(double starRating) { this.starRating = starRating; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getNightlyRate() { return nightlyRate; }
    public void setNightlyRate(double nightlyRate) {
        this.nightlyRate = nightlyRate;
        this.totalCost = this.nightlyRate * this.totalNights;
    }

    public int getTotalNights() { return totalNights; }
    public void setTotalNights(int totalNights) {
        this.totalNights = totalNights;
        this.totalCost = this.nightlyRate * this.totalNights;
    }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public String getCheckInDate() { return checkInDate; }
    public void setCheckInDate(String checkInDate) { this.checkInDate = checkInDate; }

    public String getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(String checkOutDate) { this.checkOutDate = checkOutDate; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public String getVibe() { return vibe; }
    public void setVibe(String vibe) { this.vibe = vibe; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }

    public double getGuestScore() { return guestScore; }
    public void setGuestScore(double guestScore) { this.guestScore = guestScore; }

    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }

    public String getMetroDistance() { return metroDistance; }
    public void setMetroDistance(String metroDistance) { this.metroDistance = metroDistance; }
}
