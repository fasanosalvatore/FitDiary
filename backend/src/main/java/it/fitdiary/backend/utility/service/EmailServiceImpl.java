package it.fitdiary.backend.utility.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {


    private JavaMailSender emailSender;

    /**
     * Questa funzione invia un email ad un indirzzo preso in input, con relativo oggetto e testo
     * @param destinatario indirizzo di chi deve ricevere l' indirizzo e-mail
     * @param oggetto oggetto da inserire nell' email
     * @param testo testo che deve contenere l' email
     */
    public void sendSimpleMessage(String destinatario, String oggetto, String testo) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@fitDiary.com");
        message.setTo(destinatario);
        message.setSubject(oggetto);
        message.setText(testo);
        emailSender.send(message);
    }

}