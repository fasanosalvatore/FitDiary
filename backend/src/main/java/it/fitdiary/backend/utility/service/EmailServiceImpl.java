package it.fitdiary.backend.utility.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {


    private JavaMailSender emailSender;
    /**
     * Questa funzione invia un email che contiene la password di un utente
     * @param email contiene l' indirizzo email dell utente che deve ricevere le credenziali
     * @param password contiene la password dell utente che gli permetterà di eseguire il primo accesso
     */
    public void sendSimpleMessage(String email,String password) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@fitDiary.com");
        message.setTo(email);
        message.setSubject("Credenziali accesso FitDiary");
        message.setText("Salve gentile cliente le diamo il benvenut* in FitDiary, di seguito trova le sue credenziali di accesso alla nostra piattaforma:" +
                        "email: "+email+
                        "password"+password+
                        "Cordiali saluti dal team FitDiary");
        emailSender.send(message);
    }

}