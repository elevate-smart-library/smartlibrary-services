package ca.smartlibrary.controller;

import lombok.Data;

@Data
public class BasicRequestParamFilter {
    protected String query;
    private Double lat;
    private Double lng;
    private int radius;

    private int page = 0;
    private int size = 10;

}
