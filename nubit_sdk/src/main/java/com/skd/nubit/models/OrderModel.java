package com.skd.nubit.models;

import org.json.JSONArray;

public class OrderModel  {
    private String id;
    private String name;
    private String sectionType;
    private String placementOrder;
    private String header;
    private String description;
    private String sectionDesignElement;
    private JSONArray urlDetails;

    public OrderModel(String id, String name, String sectionType, String placementOrder, String header, String description, String sectionDesignElement, JSONArray urlDetails) {
        this.id = id;
        this.name = name;
        this.sectionType = sectionType;
        this.placementOrder = placementOrder;
        this.header = header;
        this.description = description;
        this.sectionDesignElement = sectionDesignElement;
        this.urlDetails = urlDetails;
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

    public String getSectionType() {
        return sectionType;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    public String getPlacementOrder() {
        return placementOrder;
    }

    public void setPlacementOrder(String placementOrder) {
        this.placementOrder = placementOrder;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSectionDesignElement() {
        return sectionDesignElement;
    }

    public void setSectionDesignElement(String sectionDesignElement) {
        this.sectionDesignElement = sectionDesignElement;
    }

    public JSONArray getUrlDetails() {
        return urlDetails;
    }

    public void setUrlDetails(JSONArray urlDetails) {
        this.urlDetails = urlDetails;
    }
}
