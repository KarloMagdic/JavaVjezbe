package com.frontend.vj5frontend.components.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    private Long id;

    private int lowerBound;
    private int upperBound;

    @JsonIgnore
    private Set<Record> records;

    private Client client;

}
