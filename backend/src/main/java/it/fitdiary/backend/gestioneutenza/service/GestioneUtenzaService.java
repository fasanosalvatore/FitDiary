package it.fitdiary.backend.gestioneutenza.service;

import it.fitdiary.backend.entity.Utente;
import org.springframework.mail.MailException;


/**
 *
 */
public interface GestioneUtenzaService {

    /**
     * @param preparatore
     * @return utente
     */
    Utente registrazione(Utente preparatore);

    /**
     * @param idCliente
     * @param clienteModificato
     * @return utente
     */
    Utente inserimentoDatiPersonaliCliente(Long idCliente,
                                           Utente clienteModificato);

    /**
     * @param idCliente id del cliente
     * @param clienteModificato
     * @return utente
     */
    Utente modificaDatiPersonaliCliente(Long idCliente,
                                        Utente clienteModificato);

    /**
     * @param idPreparatore l'id del preparatore.
     * @param preparatoreModificato il preparatore con i dati modificati.
     * @return utente
     */
    Utente modificaDatiPersonaliPreparatore(Long idPreparatore,
                                            Utente preparatoreModificato);

    /**
     * @param id
     * @return utente
     */
    Utente getById(Long id);

    /**
     * @param idPreparatore
     * @param nome
     * @param cognome
     * @param email
     * @return utente
     */
    Utente inserisciCliente(Long idPreparatore,
                            String nome,
                            String cognome,
                            String email)
            throws IllegalArgumentException, MailException;
}
