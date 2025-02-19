package dev.srijit.Splitwise.controller;

import dev.srijit.Splitwise.dto.UserLoginRequestDTO;
import dev.srijit.Splitwise.dto.UserRegistrationRequestDTO;
import dev.srijit.Splitwise.entity.User;
import dev.srijit.Splitwise.expection.UserRegistrationInvalidDataException;
import dev.srijit.Splitwise.mapper.EntityDTOMapper;
import dev.srijit.Splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserRegistrationRequestDTO userRegistrationRequestDTO) {
        validateUserRegistrationRequestDTO(userRegistrationRequestDTO);
        User savedUser = userService.signup(userRegistrationRequestDTO.getName(), userRegistrationRequestDTO.getEmail(), userRegistrationRequestDTO.getPassword());
        return ResponseEntity.ok(
                EntityDTOMapper.toDTO(savedUser)
        );
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        validateUserLoginRequestDTO(userLoginRequestDTO);
        User savedUser = userService.login(userLoginRequestDTO.getEmail(), userLoginRequestDTO.getPassword());
        return ResponseEntity.ok(
                EntityDTOMapper.toDTO(savedUser)
        );
    }

    private void validateUserRegistrationRequestDTO(UserRegistrationRequestDTO requestDTO) {
        //do both using regex: youtube.com/watch?v=K8L6KVGG-7o
        //TODO: Validate if the email is proper
        //TODO: validate if the password is atleast 8 characters including a small, capital, numeric and special character
        if(requestDTO.getEmail() == null || requestDTO.getName() == null || requestDTO.getPassword() == null) {
            throw new UserRegistrationInvalidDataException("Invalid signup data");
        }
    }

    private void validateUserLoginRequestDTO(UserLoginRequestDTO requestDTO) {

    }
}
