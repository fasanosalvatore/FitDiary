package it.fitdiary.backend.utility.service;


import it.fitdiary.backend.entity.Utente;
import org.springframework.mail.MailException;

public interface EmailService {

    /**
     * @param destinatario destinatario del messaggio.
     * @param oggetto oggetto del messaggio.
     * @param testo testo del messaggio.
     */
    void sendSimpleMessage(String destinatario,
                           String oggetto, String testo) throws
            MailException;

    /**
     * @param newUtente destinatario del messaggio.
     * @param password password dell'utente.
     */
    void sendSimpleMessage(Utente newUtente, String password) throws
            MailException;
}
