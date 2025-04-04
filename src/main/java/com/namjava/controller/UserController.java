package com.namjava.controller;

import com.namjava.controller.request.UserCreationRequest;
import com.namjava.controller.request.UserPasswordRequest;
import com.namjava.controller.response.UserResponse;
import com.namjava.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Tag(name = "User controller API", description = "User hello api")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @Operation(summary = "Get user list", description = "API get list user  from DB")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size) {
        UserResponse userResponse1 = new UserResponse();
        userResponse1.setId(1l);
        userResponse1.setFirstName("Nam");
        userResponse1.setLastName("java");
        userResponse1.setGender("Nam");
        userResponse1.setBirthday(new Date());
        userResponse1.setUserName("admin");
        userResponse1.setEmail("trieuphuongnam85@gmail.com");
        userResponse1.setPhone("0987121831");


        UserResponse userResponse2 = new UserResponse();
        userResponse2.setId(1l);
        userResponse2.setFirstName("Nam2");
        userResponse2.setLastName("java2");
        userResponse2.setGender("Nam");
        userResponse2.setBirthday(new Date());
        userResponse2.setUserName("name");
        userResponse2.setEmail("trieuphuongnam85@gmail.com");
        userResponse2.setPhone("0987121831");

        List<UserResponse> userList = List.of(userResponse1, userResponse2);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user list");
        result.put("data", userList);

        return result;
    }


    @GetMapping("/{userID}")
    @Operation(summary = "Get user detail", description = "API get user detail from DB")
    public Map<String, Object> getUserDetail(@PathVariable Long userId) {

        UserResponse userResponse1 = new UserResponse();
        userResponse1.setId(userId);
        userResponse1.setFirstName("Nam");
        userResponse1.setLastName("java");
        userResponse1.setGender("Nam");
        userResponse1.setBirthday(new Date());
        userResponse1.setUserName("admin");
        userResponse1.setEmail("trieuphuongnam85@gmail.com");
        userResponse1.setPhone("0987121831");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user list");
        result.put("data", userResponse1);

        return result;
    }

    @PostMapping("/add")
    @Operation(summary = "create user ", description = "API create user to DB")
    public ResponseEntity<Object> createUser(@RequestBody UserCreationRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "user create successfully");
        result.put("data", userService.save(request));


        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @PutMapping("/update")
    @Operation(summary = "Update user ", description = "API update user to DB")
    public Map<String, Object> updateUser(@RequestBody UserCreationRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "user update successfully");
        result.put("data", request);
        return result;

    }

    @PatchMapping("/change-pwd")
    @Operation(summary = "Change password user ", description = "API Change password user to DB")
    public Map<String, Object> changePasswordUser(@RequestBody UserPasswordRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message", "Password update successfully");
        result.put("data", request);
        return result;

    }


    @DeleteMapping("delete/{userid}")
    @Operation(summary = "delete  user ", description = "API delete user to DB")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message", "Delete user successfully");
        result.put("data", "");
        return result;

    }
}
