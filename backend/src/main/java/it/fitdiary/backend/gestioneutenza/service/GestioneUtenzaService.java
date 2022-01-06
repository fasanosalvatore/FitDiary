package it.fitdiary.backend.gestioneutenza.service;

import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.utility.service.FitDiaryUserDetails;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 *
 */
public interface GestioneUtenzaService extends UserDetailsService {

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
     * @param idCliente         id del cliente
     * @param clienteModificato
     * @return utente
     */
    Utente modificaDatiPersonaliCliente(Long idCliente,
                                        Utente clienteModificato);

    /**
     * @param idPreparatore         l'id del preparatore.
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

    /**
     * @param email email dell'utente
     * @return dettagli dell' utente
     * @throws UsernameNotFoundException
     */
    FitDiaryUserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException;

    /**
     * @param preparatore rappresenta il preparatore
     * @param idCliente   rappresenta l' id del cliente
     * @return @return vero se il cliente fa parte della
     * lista dei clienti di quel preparatore, falso altrimenti
     */
    Boolean existsByPreparatoreAndId(Utente preparatore,
                                     Long idCliente);
}
