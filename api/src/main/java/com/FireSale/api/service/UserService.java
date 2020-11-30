package com.FireSale.api.service;

import com.FireSale.api.dto.RegisterRequest;
import com.FireSale.api.exception.ResourceNotFoundException;
import com.FireSale.api.mapper.AddressMapper;
import com.FireSale.api.mapper.UserMapper;
import com.FireSale.api.model.Address;
import com.FireSale.api.model.Role;
import com.FireSale.api.model.User;
import com.FireSale.api.repository.AddressRepository;
import com.FireSale.api.repository.RoleRepository;
import com.FireSale.api.repository.UserRepository;
import com.FireSale.api.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No user exists for email: %s", email), User.class));
    }

    public User findUserById(final long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No user exists for id: %d", userId), User.class));
    }

    public User authenticate(String email, String password) {
        final User user = userRepository.findByEmail(email).orElse(null);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Transactional(readOnly = false)
    public User create(RegisterRequest registerRequest) {
        final User user = userMapper.registerToUser(registerRequest);

        final Role role = roleRepository.getOne((long) 2);
        user.setRole(role);

        Address address = addressMapper.registerToAddress(registerRequest.getAddress());
        address = addressRepository.save(address);
        user.setAddress(address);

        return  userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = false)
    public User update(Long id, User user) {
        final User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No user exists for id: %d", id), User.class));
        existing.setDisplayName(user.getDisplayName());
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setDateOfBirth(user.getDateOfBirth());
        existing.setEmail(user.getEmail());
        return userRepository.save(user);
    }
}
