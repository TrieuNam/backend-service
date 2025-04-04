package com.namjava.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AddressRequest implements Serializable {

    private String apartmentNumber;
    private String floor;
    private String building;
    private String StreetNumber;
    private String Street;
    private String city;
    private String country;
    private Integer addressType;

}
