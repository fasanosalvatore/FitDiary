package it.fitdiary.backend.gestioneutenza.service;

import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.utility.service.FitDiaryUserDetails;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


/**
 *
 */
public interface GestioneUtenzaService extends UserDetailsService {

    /**
     * @param preparatore preparatore
     * @return utente
     */
    Utente registrazione(Utente preparatore);

    /**
     * @param id     id dell'utente
     * @param utente dati dell'utente da modificare
     * @return utente modificato
     */
    Utente modificaDatiPersonali(Long id,
                                 Utente utente);

    /**
     * @param id id utente
     * @return utente
     */
    Utente getById(Long id);

    /**
     * @param idPreparatore id preparatore
     * @param nome          nome cliente
     * @param cognome       cognome cliente
     * @param email         email cliente
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
     * @throws UsernameNotFoundException lancia un eccezione se l'utente non
     *                                   è stato trovato
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

    /**
     * @return lista degli utenti del sistema
     */
    List<Utente> visualizzaListaUtenti();

    /**
     *
     * @param idCliente id del cliente
     */
    void deleteUtenteById(Long idCliente);
}
