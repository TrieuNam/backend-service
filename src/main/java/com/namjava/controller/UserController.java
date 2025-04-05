package com.namjava.controller;

import com.namjava.controller.request.UserCreationRequest;
import com.namjava.controller.request.UserPasswordRequest;
import com.namjava.controller.request.UserUpdateRequest;
import com.namjava.controller.response.UserResponse;
import com.namjava.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Tag(name = "User controller API", description = "User hello api")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @Operation(summary = "Get user list", description = "API get list user  from DB")
    public Map<String, Object> getList(@RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String sort,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size) {
      log.info("Get user list");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user list");
        result.put("data", userService.findAll(keyword,sort,page,size));

        return result;
    }


    @GetMapping("/{userId}")
    @Operation(summary = "Get user detail", description = "API get user detail from DB")
    public Map<String, Object> getUserDetail(@PathVariable @Min(value = 1, message = "userId must be equals or greater than 1") Long userId) {

        log.info("Get user detail by Id: {}", userId);
        UserResponse userResponse1 = userService.findById(userId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user list");
        result.put("data", userResponse1);

        return result;
    }

    @PostMapping("/add")
    @Operation(summary = "create user ", description = "API create user to DB")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserCreationRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "user create successfully");
        result.put("data", userService.save(request));


        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @PutMapping("/update")
    @Operation(summary = "Update user ", description = "API update user to DB")
    public Map<String, Object> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        log.info("Updating user: {}", request);

        userService.update(request);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "user update successfully");
        result.put("data","");
        return result;

    }

    @PatchMapping("/change-pwd")
    @Operation(summary = "Change password user ", description = "API Change password user to DB")
    public Map<String, Object> changePasswordUser(@RequestBody @Valid UserPasswordRequest request) {

        log.info("Updating user: {}", request);

        userService.changePassword(request);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message", "Password update successfully");
        result.put("data", "");
        return result;

    }


    @DeleteMapping("delete/{userId}")
    @Operation(summary = "delete  user ", description = "API delete user to DB")
    public Map<String, Object> deleteUser(@PathVariable @Min(value = 1, message = "userId must be equals or greater than 1") Long userId) {

        log.info("Deleting user: {}", userId);

        userService.delete(userId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message", "Delete user successfully");
        result.put("data", "");
        return result;

    }
}
