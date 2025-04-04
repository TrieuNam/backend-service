package com.namjava.controller.request;

import com.namjava.common.Gender;
import com.namjava.common.UserType;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class UserCreationRequest implements Serializable {

    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String userName;
    private String email;
    private String phone;
    private UserType type;
    private List<AddressRequest> addresses;
}
