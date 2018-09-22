package ca.smartlibrary.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class Location {

    private String address;
    private Double lat;
    private Double lng;
    private String tag;

    @Tolerate
    public Location() {

    }
}