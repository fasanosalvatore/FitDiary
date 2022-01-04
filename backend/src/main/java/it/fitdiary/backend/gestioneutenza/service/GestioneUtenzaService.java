package it.fitdiary.backend.gestioneutenza.service;

import it.fitdiary.backend.entity.Utente;


/**
 *
 */
public interface GestioneUtenzaService {

    /**
     * @param utente
     * @return utente
     */
    Utente registrazione(Utente utente);

    /**
     * @param utente
     * @param email
     * @return utente
     */
    Utente inserimentoDatiPersonaliCliente(Utente utente, String email);

    /**
     * @param utente
     * @param email
     * @return utente
     */
    Utente modificaDatiPersonaliCliente(Utente utente, String email);

    /**
     * @param utente
     * @param email
     * @return utente
     */
    Utente modificaDatiPersonaliPreparatore(Utente utente, String email);

    /**
     * @param email
     * @return utente
     */
    Utente getUtenteByEmail(String email);

    /**
     * @param nome
     * @param cognome
     * @param email
     * @param emailPrep
     * @return utente
     */
    Utente inserisciCliente(String nome, String cognome, String email,
                            String emailPrep);
}
