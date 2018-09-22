package ca.smartlibrary.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@NoArgsConstructor
public class User implements Cloneable {

    private String id;
    private String email;
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    private String firstName;
    private String lastName;
    private Instant birthDate;

    private String phoneNumber;
    private List<Location> locations;
    private Language language;

    @JsonIgnore
    public String getFullName() {
        return Stream.of(firstName, lastName)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(" "));
    }

    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
