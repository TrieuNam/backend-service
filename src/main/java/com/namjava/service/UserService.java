package com.namjava.service;

import com.namjava.controller.request.UserCreationRequest;
import com.namjava.controller.request.UserPasswordRequest;
import com.namjava.controller.request.UserUpdateRequest;
import com.namjava.controller.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse findByUsername(String userName);

    UserResponse findByEmail(String email);

    Long save(UserCreationRequest req);

    void update(UserUpdateRequest req);

    void changePassword(UserPasswordRequest req);

    void delete(Long id);
}
