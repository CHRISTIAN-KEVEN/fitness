package com.fitness.user_service.services.impl;

import com.fitness.user_service.dtos.requests.UserRegistrationDTO;
import com.fitness.user_service.dtos.responses.UserResponseDTO;
import com.fitness.user_service.models.TRole;
import com.fitness.user_service.models.TUser;
import com.fitness.user_service.models.TUserRole;
import com.fitness.user_service.repositories.UserRepository;
import com.fitness.user_service.repositories.UserRoleRepository;
import com.fitness.user_service.services.UserService;
import com.fitness.user_service.utils.Constants;
import com.fitness.user_service.utils.ResponseBuilder;
import com.fitness.user_service.utils.Utilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.fitness.user_service.utils.Constants.DEFAULT_PAGE_START;
import static org.springframework.beans.support.PagedListHolder.DEFAULT_PAGE_SIZE;

@Service
public class UserServiceImpl implements UserService {

    private final Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional
    @Override
    public ResponseBuilder<?> register(UserRegistrationDTO registrationDto) {

        if(!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            return ResponseBuilder.errorMsgReturn("Passwords don't match !");
        }

        TUser tUser = new TUser();
//        tUser.setLgUserId(UUID.randomUUID().toString());
        tUser.setStrFname(registrationDto.getFirstName());
        tUser.setStrLname(registrationDto.getLastName());
        tUser.setStrEmail(registrationDto.getEmail());
        tUser.setStrPassword(registrationDto.getPassword());
        tUser.setStrPhoneNumber(registrationDto.getPhoneNumber());
        tUser.setEmSex(registrationDto.getGender());
        tUser.setEmStatus(Constants.USER_STATUS.ENABLED.name());
        tUser.setDtCreated(Instant.now());

        TUser savedUser = userRepository.save(tUser);

        TUserRole tUserRole = new TUserRole();
        tUserRole.setLgUser(savedUser);
        TRole tRole = new TRole();
        tRole.setStrKey(Constants.USER_ROLE.USER.name());
        tUserRole.setLgRole(tRole);
        tUserRole.setEmStatus(Constants.RoleStatus.enabled.name());
        tUserRole.setDtCreated(Instant.now());
        userRoleRepository.save(tUserRole);

        UserResponseDTO responseDTO = UserResponseDTO.builder()
                .id(tUser.getLgUserId())
                .firstName(savedUser.getStrFname())
                .lastName(savedUser.getStrLname())
                .email(savedUser.getStrEmail())
                .phoneNumber(savedUser.getStrPhoneNumber())
                .gender(savedUser.getEmSex())
                .role(tRole.getStrKey())
                .build();

        return ResponseBuilder.dataReturn(responseDTO);

    }

    @Override
    public ResponseBuilder<?> getUsers(Optional<Integer> start, Optional<Integer> size) {
       try {
           int pageStart = start.orElse(DEFAULT_PAGE_START);
           int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

           List<TUser> tUserList = userRepository.findByEmStatus(Constants.USER_STATUS.ENABLED.name(), pageStart, pageSize);
           List<UserResponseDTO> userResponse = new ArrayList<>();
           if(tUserList != null && !tUserList.isEmpty()) {
               for(TUser tUser : tUserList) {
                   userResponse.add(Utilities.buildUserResponseDTO(tUser));
               }
           }
           return ResponseBuilder.dataReturn(userResponse);
       } catch (Exception ex) {
           LOG.error("FAILED TO GET USERS ", ex);
           return ResponseBuilder.errorMsgReturn("FAILED TO GET USERS. Please try again later !");
       }
    }

    @Override
    public ResponseBuilder<?> getUsersPageableVersion(Optional<Integer> start, Optional<Integer> size) {

        try {
            int pageStart = start.orElse(DEFAULT_PAGE_START);
            int pageSize = size.orElse(DEFAULT_PAGE_SIZE);

            Pageable pageable = PageRequest.of(pageStart, pageSize);
            Page<TUser> tUserPage = userRepository.findByEmStatus(Constants.USER_STATUS.ENABLED.name(), pageable);
            List<UserResponseDTO> userResponse = new ArrayList<>();
            if(tUserPage != null && !tUserPage.getContent().isEmpty()) {
                for(TUser tUser : tUserPage.getContent()) {
                    userResponse.add(Utilities.buildUserResponseDTO(tUser));
                }
            }
            return ResponseBuilder.dataReturn(userResponse);
        } catch (Exception ex) {
            LOG.error("FAILED TO GET USERS ", ex);
            return ResponseBuilder.errorMsgReturn("FAILED TO GET USERS. Please try again later !");
        }
    }

    @Override
    public ResponseBuilder<?> getUser(String userId) {
        try {
            LOG.info("Getting user data for user {}", userId);
            TUser tUser = userRepository.findByLgUserId(userId);
            if(tUser == null) {
                return ResponseBuilder.successMsgReturn("User not found !");
            }
            UserResponseDTO userResponseDTO = Utilities.buildUserResponseDTO(tUser);
            return ResponseBuilder.dataReturn(userResponseDTO);

        } catch (Exception ex) {
            LOG.error("FAILED TO GET USER ", ex);
            return ResponseBuilder.errorMsgReturn("FAILED TO GET USER PROFILE. PLEASE TRY AGAIN LATER !");
        }
    }
}
