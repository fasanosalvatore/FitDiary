package it.fitdiary.backend.utility.service;

import it.fitdiary.backend.entity.Utente;

public interface EmailService {

    void sendSimpleMessage(String destinatario, String oggetto, String testo);

}
