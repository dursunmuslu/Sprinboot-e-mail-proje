package com.hoaxify.ws.email;

import com.hoaxify.ws.configuration.HoaxifyProperties;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    JavaMailSenderImpl mailSender;
    @Autowired
    MessageSource messageSource;

    @Autowired
    HoaxifyProperties hoaxifyProperties;

    @Value("${hoaxify.email.username}")
    String host;

    @PostConstruct
    public void initialize() {
        System.err.println("-------------");
        System.err.println(hoaxifyProperties.getClient().host());
        System.err.println("-------------");



        this.mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hoaxifyProperties.getEmail().host());
        mailSender.setPort(hoaxifyProperties.getEmail().port());
        mailSender.setUsername(hoaxifyProperties.getEmail().username());
        mailSender.setPassword(hoaxifyProperties.getEmail().password());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable", "true");

    }

    String activitonEmail= """
            <html>
                <body>
                    <h1>${title}</h1>
                    <a href= "${url}">${clickHere}</a>
                </body>
            </html>
            """;

    public void sendActivitionEmail(String email, String activationToken) {

        System.out.println("Current locale: " + LocaleContextHolder.getLocale());


        var activationUrl = hoaxifyProperties.getClient().host() + "activation/" + activationToken;
        var title = messageSource.getMessage("hoaxify.mail.user.created.title",null, LocaleContextHolder.getLocale());
        var clickHere = messageSource.getMessage("hoaxify.mail.click.here",null, LocaleContextHolder.getLocale());

        var mailBody = activitonEmail
                .replace("${url}", activationUrl)
                .replace("${title}", title)
                .replace("${clickHere}", clickHere);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        try {
            message.setFrom(hoaxifyProperties.getEmail().from());
            message.setTo(email);
            message.setSubject(title);
            message.setText(mailBody, true);
        }catch (MessagingException e) {
            e.printStackTrace();
        }

        this.mailSender.send(mimeMessage);
    }

}
