package com.fitness.user_service.utils;

import com.fitness.user_service.dtos.responses.UserResponseDTO;
import com.fitness.user_service.models.TUser;
import org.apache.catalina.User;

import java.util.stream.Collectors;

public class Utilities {

    public static UserResponseDTO buildUserResponseDTO(TUser tUser) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setFirstName(tUser.getStrFname());
        userResponseDTO.setLastName(tUser.getStrLname());
        userResponseDTO.setEmail(tUser.getStrEmail());
        userResponseDTO.setId(tUser.getLgUserId());
        userResponseDTO.setPhoneNumber(tUser.getStrPhoneNumber());
        userResponseDTO.setGender(tUser.getEmSex());
        userResponseDTO.setRole(tUser.getTUserRoles().stream().map(uRole -> uRole.getLgRole().getStrKey()).collect(Collectors.joining(",")));

        return userResponseDTO;
    }
}
