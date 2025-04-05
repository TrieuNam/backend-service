package com.namjava.controller.request;

import com.namjava.common.Gender;
import com.namjava.common.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@ToString
public class UserCreationRequest implements Serializable {

    @NotBlank(message = "first Name must be not blank")
    private String firstName;

    @NotBlank(message = "last Name  must be not blank")
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String userName;

    @Email(message = "Email invalid")
    private String email;
    private String phone;
    private UserType type;
    private List<AddressRequest> addresses;
}
