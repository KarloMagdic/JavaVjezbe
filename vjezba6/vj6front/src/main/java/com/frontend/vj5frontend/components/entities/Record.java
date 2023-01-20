package com.frontend.vj5frontend.components.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    private Long id;

    private Date dateOfMeasurement;

    private int measurement;

    private Device device;

}

