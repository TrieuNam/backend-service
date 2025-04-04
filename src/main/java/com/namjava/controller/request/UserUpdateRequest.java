package com.namjava.controller.request;

import lombok.Getter;

import java.util.Date;

@Getter
public class UserUpdateRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthday;

    private String userName;
    private String email;
    private String phone;
}

