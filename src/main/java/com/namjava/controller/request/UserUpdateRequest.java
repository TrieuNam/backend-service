package com.namjava.controller.request;

import com.namjava.common.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@ToString
public class UserUpdateRequest {

    @NotNull(message = "id must be not null")
    @Min(value = 1, message = "userId must be equals or greater than 1")
    private Long id;

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

    private List<AddressRequest> addresses;
}

