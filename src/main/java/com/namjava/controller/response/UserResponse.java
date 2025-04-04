package com.namjava.controller.response;

import com.namjava.common.Gender;
import com.namjava.controller.request.AddressRequest;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String userName;
    private String email;
    private String phone;


}
