package it.fitdiary.backend.utility.service;



public interface EmailService {

    void sendSimpleMessage(String destinatario, String oggetto, String testo);

}
