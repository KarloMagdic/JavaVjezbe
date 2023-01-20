package com.frontend.vj5frontend.components.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Long id;

    private String city;
    private String county;
    private Integer streetNumber;

    @JsonIgnore
    private Client client;

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", streetNumber=" + streetNumber +
                '}';
    }
}


