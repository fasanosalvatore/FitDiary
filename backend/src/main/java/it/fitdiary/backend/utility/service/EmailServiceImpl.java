package it.fitdiary.backend.utility.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component("mailService")
@EnableAutoConfiguration
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void sendEmail(MimeMessagePreparator preparator) {
        mailSender.send(preparator); // STEP 2
    }

    /**
     * Questa funzione invia un email ad un indirzzo preso in input,
     * con relativo oggetto e testo.
     *
     * @param emailAddress indirizzo di chi deve ricevere l' indirizzo e-mail.
     * @param oggetto      oggetto da inserire nell' email.
     * @param testo        testo che deve contenere l' email.
     */
    @RequestMapping("/mail")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendSimpleMessage(final String emailAddress,
                                  final String oggetto, final String testo)
            throws MailException {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(emailAddress);
            message.setFrom(
                    new InternetAddress("socialnetworkmailsender@yandex.com"));
            message.setSubject(oggetto);
            message.setSentDate(new Date());
            message.setText(testo);
        };

        sendEmail(preparator);
    }

}
