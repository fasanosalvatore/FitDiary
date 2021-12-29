package it.fitdiary.backend.gestioneutenza.service;

import it.fitdiary.backend.entity.Utente;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


public interface GestioneUtenzaService {
    Utente registrazione(Utente utente);

    Utente inserimentoDatiPersonaliCliente(Utente utente);

    Utente modificaDatiPersonaliCliente(Utente utente);

    Utente modificaDatiPersonaliPreparatore(Utente utente);

    Utente getUtenteByEmail(String email);
}
