package it.fitdiary.backend.utility.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {


    private JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    /**
     * Questa funzione invia un email ad un indirzzo preso in input, con relativo oggetto e testo
     * @param destinatario indirizzo di chi deve ricevere l' indirizzo e-mail
     * @param oggetto oggetto da inserire nell' email
     * @param testo testo che deve contenere l' email
     */
    public void sendSimpleMessage(String destinatario, String oggetto, String testo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fitdiary23@gmail.com");
        message.setTo(destinatario);
        message.setSubject(oggetto);
        message.setText(testo);
        emailSender.send(message);
    }

}