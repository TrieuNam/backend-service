package com.namjava.controller.request;

import com.namjava.common.Gender;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@ToString
public class UserUpdateRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;

    private String userName;
    private String email;
    private String phone;

    private List<AddressRequest> addresses;
}

