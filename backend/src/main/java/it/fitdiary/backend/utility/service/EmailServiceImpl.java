package it.fitdiary.backend.utility.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component("mailService")
@EnableAutoConfiguration
public class EmailServiceImpl implements EmailService {
    /**
     * JavaMailSender.
     */
    @Qualifier("mailService")
    private final JavaMailSender emailSender;

    public EmailServiceImpl(
            JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    /**
     * Questa funzione invia un email ad un indirzzo preso in input,
     * con relativo oggetto e testo.
     *
     * @param destinatario indirizzo di chi deve ricevere l' indirizzo e-mail.
     * @param oggetto      oggetto da inserire nell' email.
     * @param testo        testo che deve contenere l' email.
     */
    @RequestMapping("/mail")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendSimpleMessage(final String destinatario,
                                  final String oggetto, final String testo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fitdiary23@gmail.com");
        message.setTo(destinatario);
        message.setSubject(oggetto);
        message.setText(testo);
        emailSender.send(message);
    }

}
