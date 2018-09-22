package ca.smartlibrary.dto.library;

import ca.smartlibrary.dto.Location;
import ca.smartlibrary.dto.User;
import lombok.Data;

import java.util.List;

@Data
public class Library {

    private String id;
    private String name;
    private Location location;
    private List<User> contacts;

    private LibraryType type;
}
