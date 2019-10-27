package com.example.validationdemo.model;

import javax.validation.constraints.Size;

public class RestResponseData {

    @Size(min = 2, max = 20)
    private String id;

    private String name;
    private String yearInscribed;
    private String url;
    private String imageUrl;
    private String descriptionMarkup;
    private String states;
    private Location location;
    private String categoryName;
    private String regionName;

    public RestResponseData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearInscribed() {
        return yearInscribed;
    }

    public void setYearInscribed(String yearInscribed) {
        this.yearInscribed = yearInscribed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescriptionMarkup() {
        return descriptionMarkup;
    }

    public void setDescriptionMarkup(String descriptionMarkup) {
        this.descriptionMarkup = descriptionMarkup;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
