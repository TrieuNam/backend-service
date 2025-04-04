package com.namjava.controller.response;

import com.namjava.controller.request.AddressRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserResponse implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthday;
    private String userName;
    private String email;
    private String phone;


}
