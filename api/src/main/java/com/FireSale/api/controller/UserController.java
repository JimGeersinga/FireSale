package com.firesale.api.controller;

import com.firesale.api.dto.ApiResponse;
import com.firesale.api.dto.ErrorResponse;
import com.firesale.api.dto.address.PatchAddressDTO;
import com.firesale.api.dto.address.UpdateAddressDTO;
import com.firesale.api.dto.auction.CreateImageDTO;
import com.firesale.api.dto.user.*;
import com.firesale.api.dto.usersecurity.ChangepasswordDTO;
import com.firesale.api.dto.usersecurity.EmailaddressDTO;
import com.firesale.api.exception.InvalidResetTokenException;
import com.firesale.api.mapper.AddressMapper;
import com.firesale.api.mapper.AuctionMapper;
import com.firesale.api.mapper.UserMapper;
import com.firesale.api.model.Address;
import com.firesale.api.model.Auction;
import com.firesale.api.model.ErrorTypes;
import com.firesale.api.model.User;
import com.firesale.api.security.UserPrincipal;
import com.firesale.api.service.*;
import com.firesale.api.util.MailUtil;
import com.firesale.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    private final AddressService addressService;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final ImageService imageService;
    private final AuctionService auctionService;
    private final AuctionMapper auctionMapper;
    private final JavaMailSender mailSender;
    private final UserSecurityService userSecurityService;
    private final MailUtil mailUtil;

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@Valid @RequestBody final LoginDTO loginRequest) {
        final User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.LOGIN_FAILED, "Email or password is incorrect."), HttpStatus.UNAUTHORIZED);
        } else if (user.getIsLocked()) {
            return new ResponseEntity<>(new ErrorResponse(ErrorTypes.ACCOUNT_IS_LOCKED, "Account is locked."), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, userMapper.toDTO(user)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody RegisterDTO registerRequest) {
        final User user = userService.create(userMapper.toModel(registerRequest));
        return new ResponseEntity<>(new ApiResponse<>(true, userMapper.toDTO(user)), HttpStatus.CREATED);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<UserDTO>> me() {
        final UserPrincipal user = SecurityUtil.getSecurityContextUser();
        return new ResponseEntity<>(new ApiResponse<>(true, userMapper.toDTO(user.getUser())), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity allUsers() {
        final List<User> users = userService.getAll();
        return new ResponseEntity<>(new ApiResponse<>(true, users.stream().map(userMapper::toProfile).collect(Collectors.toList())), HttpStatus.OK);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        final UserPrincipal user = SecurityUtil.getSecurityContextUser();
        userService.delete(user.getUser());
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") final long userId) {
        final User user = userService.findUserById(userId);
        return new ResponseEntity<>(new ApiResponse<>(true, userMapper.toProfile(user)), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated() and @guard.isSelf(#userId)")
    public void updateUser(@PathVariable("userId") final long userId, @Valid @RequestBody final UpdateUserDTO updateUserDTO) {
        userService.updateUser(userId, userMapper.toModel(updateUserDTO));
    }

    @PatchMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated() and (@guard.isAdmin() or @guard.isSelf(#userId))")
    public void patchUser(@PathVariable("userId") final long userId, @Valid @RequestBody PatchUserDTO patchUserDTO) throws IOException {
        CreateImageDTO avatar = patchUserDTO.getAvatar();
        if (avatar != null && avatar.getPath().length > 0) {
            imageService.storeAvatar(avatar, userId);
        }
        userService.patchUser(userId, userMapper.toModel(patchUserDTO));
    }

    @GetMapping("/{userId}/address/{addressId}")
    public ResponseEntity getAddress(@PathVariable("userId") final long userId, @PathVariable("addressId") final long addressId) {
        final Address address = addressService.findAddressById(addressId);
        return new ResponseEntity<>(new ApiResponse<>(true, addressMapper.toDTO(address)), HttpStatus.OK);
    }

    @PutMapping("/{userId}/address/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated() and @guard.isSelf(#userId)")
    public void updateAddress(@PathVariable("userId") final long userId, @PathVariable("addressId") final long addressId, @Valid @RequestBody final UpdateAddressDTO updateAddressDTO) {
        addressService.updateAddress(addressId, addressMapper.toModel(updateAddressDTO));
    }

    @PatchMapping(value = "/{userId}/address/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated() and (@guard.isAdmin() or @guard.isSelf(#userId))")
    public void patchAddress(@PathVariable("userId") final long userId, @PathVariable("addressId") final long addressId, @Valid @RequestBody PatchAddressDTO patchAddressDTO) {
        addressService.patchAddress(addressId, addressMapper.toModel(patchAddressDTO));
    }

    @PostMapping(value = "/{userId}/avatar")
    public ResponseEntity uploadAvatar(@RequestBody CreateImageDTO imageDTO, @PathVariable Long userId) throws IOException {
        imageService.storeAvatar(imageDTO, userId);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/auctions")
    public ResponseEntity getAuctionsByUserId(@PathVariable("userId") final long userId) {
        final Collection<Auction> auctions = auctionService.getAuctionsByUserId(userId);
        return new ResponseEntity<>(new ApiResponse<>(true, auctions.stream().map(auctionMapper::toDTO)), HttpStatus.OK);
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity resetPassword(@RequestBody final EmailaddressDTO emailaddressDTO) {
        final User user = userService.findUserByEmail(emailaddressDTO.getEmailaddress());
        final UUID token = UUID.randomUUID();
        final String subject = "Wachtwoord reset - FireSale";
        final String body = "Gebruik de volgende link om uw wachtwoord te resetten: http://localhost:4200/users/changepassword/" + token;
        userSecurityService.createPasswordResetTokenForUser(user, token);
        mailSender.send(mailUtil.constructEmail(user, subject, body));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/changepassword")
    public ResponseEntity changePassword(@RequestBody final ChangepasswordDTO changepasswordDTO) {
        userSecurityService.validatePasswordResetToken(changepasswordDTO.getToken());
        Optional<User> user = userSecurityService.getUserByPasswordResetToken(changepasswordDTO.getToken());
        if (user.isPresent()) {
            userSecurityService.changeUserPassword(user.get(), changepasswordDTO.getPassword());
            return new ResponseEntity(HttpStatus.OK);
        } else {
            throw new InvalidResetTokenException(String.format("No user exists for reset token: %d", changepasswordDTO.getToken()), ErrorTypes.USER_NOT_FOUND);
        }
    }
}

