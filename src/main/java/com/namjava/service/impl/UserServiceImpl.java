package com.namjava.service.impl;

import com.namjava.common.UserStatus;
import com.namjava.controller.request.UserCreationRequest;
import com.namjava.controller.request.UserPasswordRequest;
import com.namjava.controller.request.UserUpdateRequest;
import com.namjava.controller.response.UserResponse;
import com.namjava.model.AddressEntity;
import com.namjava.model.UserEntity;
import com.namjava.repository.AddressRepository;
import com.namjava.repository.UserRepository;
import com.namjava.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<UserResponse> findAll() {
        return List.of();
    }

    @Override
    public UserResponse findById(Long id) {
        return null;
    }

    @Override
    public UserResponse findByUsername(String userName) {
        return null;
    }

    @Override
    public UserResponse findByEmail(String email) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long save(UserCreationRequest req) {
        log.info("Saving user: {}", req);
        UserEntity user = new UserEntity();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());
        user.setEmail(req.getPhone());
        user.setPhone(req.getPhone());
        user.setUserName(req.getUserName());
        user.setType(req.getType());
        user.setStatus(UserStatus.NONE);
        userRepository.save(user);
        log.info("Saved user: {}", user);

        if (user.getId() != null) {
            log.info("User id : {}", user.getId());
            List<AddressEntity> addresses = new ArrayList<>();
            req.getAddresses().forEach(addressRequest -> {
                AddressEntity address = new AddressEntity();
                address.setApartmentNumber(addressRequest.getApartmentNumber());
                address.setFloor(addressRequest.getFloor());
                address.setBuilding(addressRequest.getBuilding());
                address.setStreetNumber(addressRequest.getStreetNumber());
                address.setStreet(addressRequest.getStreet());
                address.setCity(addressRequest.getCity());
                address.setCountry(addressRequest.getCountry());
                address.setAddressType(addressRequest.getAddressType());
                address.setUserID(user.getId());
                addresses.add(address);
            });
            addressRepository.saveAll(addresses);
            log.info("Saved Addresses: {}", addresses);
        }
        return user.getId();
    }

    @Override
    public void update(UserUpdateRequest req) {

    }

    @Override
    public void changePassword(UserPasswordRequest req) {

    }

    @Override
    public void delete(Long id) {

    }
}
