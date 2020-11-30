package com.FireSale.api.controller;

import com.FireSale.api.dto.*;
import com.FireSale.api.mapper.UserMapper;
import com.FireSale.api.model.ErrorTypes;
import com.FireSale.api.model.User;
import com.FireSale.api.security.UserPrincipal;
import com.FireSale.api.service.UserService;
import com.FireSale.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@Valid @RequestBody final LoginRequest loginRequest){
        try {
            final User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            if (user == null) {
                return new ResponseEntity<>(new ErrorResponse(ErrorTypes.LOGIN_FAILED, "Email or password is incorrect."), HttpStatus.UNAUTHORIZED);
            } else if (user.getIsLocked()) {
                return new ResponseEntity<>(new ErrorResponse(ErrorTypes.ACCOUNT_IS_LOCKED, "Account is locked."), HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(new ApiResponse<>(true, userMapper.toDTO(user)), HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.UNKOWN, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/me")
    public ResponseEntity me(){
        try {
            final UserPrincipal user = SecurityUtil.getSecurityContextUser();
            return new ResponseEntity<>(new ApiResponse<>(true, userMapper.toDTO(user.getUser())), HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.UNKOWN, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity all(){
        try {
            final List<User> users = userService.getAll();
            return new ResponseEntity<>(new ApiResponse<>(true, users.stream().map(userMapper::toDTO)), HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.UNKOWN, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") final long id) {
        try {
            final User user = userService.findUserById(id);
            return new ResponseEntity<>(new ApiResponse<>(true, userMapper.toDTO(user)), HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.UNKOWN, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity update(@PathVariable("id") final long id, @Valid @RequestBody final UserDTO userDTO) {
        try {
            final User user = userService.update(id, userMapper.toModel(userDTO));
        }
        catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.UNKOWN, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            final User user = userService.create(registerRequest);
            return new ResponseEntity<>(new ApiResponse<>(true, userMapper.toDTO(user)), HttpStatus.CREATED);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.UNKOWN, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
