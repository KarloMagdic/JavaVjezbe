package com.frontend.vj5frontend.components.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private Long id;

    private String firstName;
    private String lastName;

    private Date dateOfBirth;

    private Address address;

    @JsonIgnore
    private Device device;

}
