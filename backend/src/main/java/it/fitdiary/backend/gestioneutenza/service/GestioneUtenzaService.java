package it.fitdiary.backend.gestioneutenza.service;

import it.fitdiary.backend.entity.Utente;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


public interface GestioneUtenzaService {
    Utente registrazione(Utente utente);
}
