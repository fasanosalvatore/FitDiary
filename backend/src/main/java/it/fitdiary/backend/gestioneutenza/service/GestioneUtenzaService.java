package it.fitdiary.backend.gestioneutenza.service;

import it.fitdiary.backend.entity.Utente;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


public interface GestioneUtenzaService {
    Utente registrazione(Utente utente);

    Utente inserimentoDatiPersonaliCliente(Utente utente,String email);

    Utente modificaDatiPersonaliCliente(Utente utente,String email);

    Utente modificaDatiPersonaliPreparatore(Utente utente, String email);

    Utente getUtenteByEmail(String email);

    Utente inserisciCliente(String nome, String cognome, String email, String emailPrep);
}
