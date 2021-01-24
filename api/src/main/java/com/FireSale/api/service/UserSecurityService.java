package com.firesale.api.service;

import com.firesale.api.model.ErrorTypes;
import com.firesale.api.exception.InvalidResetTokenException;
import com.firesale.api.model.PasswordResetToken;
import com.firesale.api.model.User;
import com.firesale.api.repository.UserRepository;
import com.firesale.api.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public Boolean validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        if (!isTokenFound(passToken)) {
            throw new InvalidResetTokenException("Password reset token is invalid", ErrorTypes.INVALID_RESET_TOKEN);
        } else if (isTokenExpired(passToken)) {
            throw new InvalidResetTokenException("Password reset token is expired", ErrorTypes.EXPIRED_RESET_TOKEN);
        }

        return true;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        return passToken.getExpiryDate().isBefore(LocalDateTime.now());
    }

    public Optional<User> getUserByPasswordResetToken(final String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Transactional(readOnly = false)
    public void createPasswordResetTokenForUser(User user, UUID token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    @Transactional(readOnly = false)
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        passwordResetTokenRepository.delete(passwordResetTokenRepository.findByUser(user));
        userRepository.save(user);
    }
}
