package it.fitdiary.backend.utility.service;


import org.springframework.mail.MailException;

public interface EmailService {

    /**
     * @param destinatario destinatario del messaggio.
     * @param oggetto oggetto del messaggio.
     * @param testo testo del messaggio.
     */
    void sendSimpleMessage(String destinatario, String oggetto, String testo) throws
            MailException;

}
