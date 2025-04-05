package com.namjava.service.impl;

import com.namjava.common.UserStatus;
import com.namjava.controller.request.UserCreationRequest;
import com.namjava.controller.request.UserPasswordRequest;
import com.namjava.controller.request.UserUpdateRequest;
import com.namjava.controller.response.UserPageResponse;
import com.namjava.controller.response.UserResponse;
import com.namjava.exception.InvalidDataException;
import com.namjava.exception.ResourceNotFoundException;
import com.namjava.model.AddressEntity;
import com.namjava.model.UserEntity;
import com.namjava.repository.AddressRepository;
import com.namjava.repository.UserRepository;
import com.namjava.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncode;


    @Override
    public UserPageResponse findAll(String keyword, String sort, int page, int size) {
        log.info("findAll start");

        // Sorting
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        if (StringUtils.hasLength(sort)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)"); // tencot:asc|desc
            Matcher matcher = pattern.matcher(sort);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    order = new Sort.Order(Sort.Direction.ASC, columnName);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, columnName);
                }
            }
        }

        int pageNo = 0;
        if (page > 0) {
            pageNo = page - 1;
        }


        //Paging
        Pageable pageable = PageRequest.of(pageNo,size,Sort.by(order));
        Page<UserEntity> userEntities;
        if(StringUtils.hasLength(keyword)){
            keyword = "%" + keyword.toLowerCase() + "%";
            userEntities=   userRepository.searchByKeyword(keyword, pageable);
        }else {
            userEntities  = userRepository.findAll(pageable);
        }

        return  getUserPageResponse(page, size, userEntities);
    }

    private static UserPageResponse getUserPageResponse(int page, int size, Page<UserEntity> userEntities) {
        List<UserResponse> userList = userEntities.stream().map(
                userEntity -> UserResponse.builder()
                        .id(userEntity.getId())
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .gender(userEntity.getGender())
                        .birthday(userEntity.getBirthday())
                        .userName(userEntity.getUserName())
                        .email(userEntity.getEmail())
                        .phone(userEntity.getPhone())
                        .build()
        ).toList();

        UserPageResponse response = new UserPageResponse();
        response.setPageNumber(page);
        response.setPageSize(size);
        response.setTotalElement(userEntities.getTotalElements());
        response.setTotalPage(userEntities.getTotalPages());
        response.setUsers(userList);
        return response;
    }

    @Override
    public UserResponse findById(Long id) {
        log.info("find by user id: {}", id);
        UserEntity userEntity = getUserEntity(id);


        return UserResponse.builder()
                .id(id)
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .gender(userEntity.getGender())
                .birthday(userEntity.getBirthday())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .build();
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

        UserEntity userByEmail = userRepository.findByEmail(req.getEmail());
        if(userByEmail != null){
            throw new InvalidDataException("Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());
        user.setEmail(req.getEmail());
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
                address.setUserId(user.getId());
                addresses.add(address);
            });
            addressRepository.saveAll(addresses);
            log.info("Saved Addresses: {}", addresses);
        }
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateRequest req) {
        log.info("Updating user: {}", req);
        // Get yser id
        UserEntity user = getUserEntity(req.getId());
        //set data
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUserName(req.getUserName());
        // save user
        userRepository.save(user);
        log.info("Updated user: {}", user);

        List<AddressEntity> addresses = new ArrayList<>();

        req.getAddresses().forEach(addressRequest -> {
            AddressEntity addressEntity = addressRepository.findByUserIdAndAddressType(user.getId(), addressRequest.getAddressType());
            if (addressEntity == null) {
                addressEntity = new AddressEntity();
            }
            addressEntity.setApartmentNumber(addressRequest.getApartmentNumber());
            addressEntity.setFloor(addressRequest.getFloor());
            addressEntity.setBuilding(addressRequest.getBuilding());
            addressEntity.setStreetNumber(addressRequest.getStreetNumber());
            addressEntity.setStreet(addressRequest.getStreet());
            addressEntity.setCity(addressRequest.getCity());
            addressEntity.setCountry(addressRequest.getCountry());
            addressEntity.setAddressType(addressRequest.getAddressType());
            addressEntity.setUserId(user.getId());
            addresses.add(addressEntity);
        });
        //save address
        addressRepository.saveAll(addresses);
    }

    @Override
    public void changePassword(UserPasswordRequest req) {
        log.info("Change password user: {}", req);
        // Get yser id
        UserEntity user = getUserEntity(req.getId());

        if (req.getPassword().equals(req.getConfirmPassword())) {
            user.setPassword(passwordEncode.encode(req.getPassword()));
        }
        userRepository.save(user);
        log.info("Changeed password user: {}", user);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user: {}", id);

        UserEntity user = getUserEntity(id);
        user.setStatus(UserStatus.INACTIVE);

        userRepository.save(user);

        log.info("Deleted user: {}", user);

    }

    private UserEntity getUserEntity(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

}
