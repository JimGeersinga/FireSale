package com.FireSale.api.controller;

import com.FireSale.api.dto.ApiResponse;
import com.FireSale.api.dto.ErrorResponse;
import com.FireSale.api.mapper.UserMapper;
import com.FireSale.api.model.ErrorTypes;
import com.FireSale.api.security.UserPrincipal;
import com.FireSale.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity Get(){
        try {
            final UserPrincipal user = SecurityUtil.getSecurityContextUser();
            return new ResponseEntity<>(new ApiResponse<>(true, userMapper.toDTO(user.getUser())), HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.UNKOWN, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
