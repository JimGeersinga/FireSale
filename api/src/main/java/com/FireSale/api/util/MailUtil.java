package com.firesale.api.util;

import com.firesale.api.config.MailConfig;
import com.firesale.api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailUtil {

    private final MailConfig mailConfig;

    public SimpleMailMessage constructEmail(User user, String subject, String body) {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setFrom(mailConfig.from);
        return simpleMailMessage;
    }
}
