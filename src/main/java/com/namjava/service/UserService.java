package com.namjava.service;

import com.namjava.controller.request.UserCreationRequest;
import com.namjava.controller.request.UserPasswordRequest;
import com.namjava.controller.request.UserUpdateRequest;
import com.namjava.controller.response.UserPageResponse;
import com.namjava.controller.response.UserResponse;

import java.util.List;

public interface UserService {
    UserPageResponse findAll(String keyword, String sort, int page, int size);

    UserResponse findById(Long id);

    UserResponse findByUsername(String userName);

    UserResponse findByEmail(String email);

    Long save(UserCreationRequest req);

    void update(UserUpdateRequest req);

    void changePassword(UserPasswordRequest req);

    void delete(Long id);
}
