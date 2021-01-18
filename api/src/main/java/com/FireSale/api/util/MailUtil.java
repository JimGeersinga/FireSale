package com.FireSale.api.util;

import com.FireSale.api.config.MailConfig;
import com.FireSale.api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailUtil {

    private final MailConfig mailConfig;

    public SimpleMailMessage constructEmail(User user, String subject, String body) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(mailConfig.from);
        return email;
    }
}
