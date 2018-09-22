package ca.smartlibrary.dto;

import lombok.Data;

@Data
public class Review {

    private String id;
    private String title;
    private String description;
    private User author;
    private int note;

}
