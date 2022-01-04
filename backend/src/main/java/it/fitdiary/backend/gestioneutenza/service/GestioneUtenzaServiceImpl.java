package it.fitdiary.backend.gestioneutenza.service;

import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.repository.RuoloRepository;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import it.fitdiary.backend.utility.PasswordGenerator;
import it.fitdiary.backend.utility.service.EmailService;
import it.fitdiary.backend.utility.service.FitDiaryUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GestioneUtenzaServiceImpl
        implements GestioneUtenzaService, UserDetailsService {

    /**
     * rappresenta la repository dell'utente.
     */
    private final UtenteRepository utenteRepository;
    /**
     * rappresenta la repository del ruolo.
     */
    private final RuoloRepository ruoloRepository;
    /**
     * rappresenta la codifica della password.
     */
    private final BCryptPasswordEncoder passwordEncoder;
    /**
     * rappresenta il generatore di password.
     */
    private final PasswordGenerator passwordGenerator;
    /**
     * rappresenta l'email per inviare la password al cliente.
     */
    private final EmailService emailService;

    /**
     * questa funzione permette di registrare un nuovo preparatore.
     *
     * @param utente nuovo preparatore
     * @return preparatore con l'id
     * @throws IllegalArgumentException in caso di email già presente nel db
     *                                  o utente non valido.
     */
    @Override
    public Utente registrazione(final Utente utente)
            throws IllegalArgumentException {
        System.out.println(utente);
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        if (utenteRepository.existsByEmail(utente.getEmail())) {
            throw new IllegalArgumentException(
                    "email già presente nel " + "database");
        }
        utente.setRuolo(ruoloRepository.findByNome("Preparatore"));
        utente.setAttivo(true);
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        return utenteRepository.save(utente);
    }

    /**
     * Questo metodo permette di inserire un cliente
     * e di associarlo ad un preparatore.
     *
     * @param nome      nome del cliente.
     * @param cognome   cognome del cliente.
     * @param email     email del cliente.
     * @param emailPrep email del preparatore.
     * @return utente inserito nel sistema.
     * @throws IllegalArgumentException
     */
    @Override
    public Utente inserisciCliente(final String nome, final String cognome,
                                   final String email, final String emailPrep)
            throws IllegalArgumentException, MailException {
        Utente preparatore = utenteRepository.findByEmail(emailPrep);
        if (preparatore == null) {
            throw new IllegalArgumentException("Preparatore non valido");
        }
        Utente cliente = utenteRepository.findByEmail(email);
        if (cliente != null) {
            throw new IllegalArgumentException("Cliente già presente");
        }
        Utente newUtente = new Utente();
        newUtente.setNome(nome);
        newUtente.setCognome(cognome);
        newUtente.setEmail(email);
        newUtente.setRuolo(ruoloRepository.findByNome("CLIENTE"));
        newUtente.setAttivo(true);
        newUtente.setPreparatore(preparatore);
        String password = passwordGenerator.generate();
        emailService.sendSimpleMessage(newUtente.getEmail(),
                "Benvenuto in FitDiary!",
                "Ecco la tua password per accedere: \n" + password);
        newUtente.setPassword(passwordEncoder.encode(password));
        Utente newCliente = utenteRepository.save(newUtente);
        return newCliente;
    }

    /**
     * Questo metodo trova un utente dalla email per la verifica del login.
     *
     * @param email email dell'utente da cercare.
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public FitDiaryUserDetails loadUserByUsername(final String email)
            throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByEmail(email);
        if (utente == null) {
            log.error("Utente non trovato nel database");
            throw new UsernameNotFoundException(
                    "Utente non trovato nel database");
        } else {
            log.info("Utente trovato nel database: {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        log.info("Ruolo: {}", utente.getRuolo().getNome());
        authorities.add(
                new SimpleGrantedAuthority(utente.getRuolo().getNome()));
        var fitDiaryUser = new FitDiaryUserDetails(
                utente.getEmail(),
                utente.getPassword(),
                authorities);
        fitDiaryUser.setName(utente.getNome());
        fitDiaryUser.setSurname(utente.getCognome());
        fitDiaryUser.setPhoneNumber(utente.getTelefono());
        fitDiaryUser.setGender(utente.getSesso());
        fitDiaryUser.setTrainerId(
                utente.getPreparatore() != null
                        ? utente.getPreparatore().getId() : -1);
        return fitDiaryUser;
    }

    /**
     * Questo metodo permette di inserire
     * i dati nel sistema ad un cliente.
     *
     * @param utente rappresenta l'insieme
     *               dei dati personali di un utente.
     * @param email
     * @return utente rappresenta l'utente
     * con i nuovi dati inserito nel database.
     * @throws IllegalArgumentException
     * lancia l'errore generato da un input errato.
     */
    @Override
    public Utente inserimentoDatiPersonaliCliente(final Utente utente,
                                                  final String email)
            throws IllegalArgumentException {
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        Utente newUtente = utenteRepository.findByEmail(email);
        if (newUtente == null) {
            throw new IllegalArgumentException(
                    "Utente non presente del Database");
        }
        newUtente.setDataNascita(utente.getDataNascita());
        newUtente.setTelefono(utente.getTelefono());
        newUtente.setCitta(utente.getCitta());
        newUtente.setVia(utente.getVia());
        newUtente.setCap(utente.getCap());
        return utenteRepository.save(newUtente);
    }

    /**
     * Questo metodo permette di inserire i dati
     * da modificare nel sistema ad un cliente.
     *
     * @param email
     * @param utente rappresenta l'insieme
     * dei dati personali di un utente.
     * @return utente rappresenta l'utente
     * con i nuovi dati inserito nel database.
     * @throws IllegalArgumentException
     * lancia l'errore generato da un input errato.
     */
    @Override
    public Utente modificaDatiPersonaliCliente(final Utente utente,
                                               final String email)
            throws IllegalArgumentException {
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        Utente newUtente = utenteRepository.findByEmail(email);

        if (newUtente == null) {
            throw new IllegalArgumentException(
                    "Utente non presente del Database");
        }
        newUtente.setNome(utente.getNome());
        newUtente.setCognome(utente.getCognome());
        newUtente.setDataAggiornamento(utente.getDataAggiornamento());
        newUtente.setEmail(utente.getEmail());
        newUtente.setPassword(passwordEncoder.encode(utente.getPassword()));
        newUtente.setDataNascita(utente.getDataNascita());
        newUtente.setTelefono(utente.getTelefono());
        newUtente.setCitta(utente.getCitta());
        newUtente.setVia(utente.getVia());
        newUtente.setCap(utente.getCap());
        return utenteRepository.save(newUtente);
    }

    /**
     * Questo metodo permette di aggiornare
     * i dati presenti nel database di un utente.
     *
     * @param preparatore rappresenta l' insieme di
     * tutti i dati personali di un preparatore
     * che devono essere aggiornati.
     * @return updatedPerparatore rappresenta l' insieme
     * di dati personali di un perparatore aggiornati.
     * @throws IllegalArgumentException
     * lancia l'errore generato da un input errato.
     */
    @Override
    public Utente modificaDatiPersonaliPreparatore(final Utente preparatore,
                                                   final String email)
            throws IllegalArgumentException {
        Utente updatedPerparatore = utenteRepository.findByEmail(email);
        if (preparatore == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        if (updatedPerparatore == null) {
            throw new IllegalArgumentException(
                    "Utente non presente del Database");
        }
        updatedPerparatore.setNome(preparatore.getNome());
        updatedPerparatore.setCognome(preparatore.getCognome());
        updatedPerparatore.setDataAggiornamento(
                preparatore.getDataAggiornamento());
        updatedPerparatore.setEmail(preparatore.getEmail());
        updatedPerparatore.setPassword(
                passwordEncoder.encode(preparatore.getPassword()));
        updatedPerparatore.setDataNascita(preparatore.getDataNascita());
        updatedPerparatore.setTelefono(preparatore.getTelefono());
        updatedPerparatore.setCitta(preparatore.getCitta());
        updatedPerparatore.setVia(preparatore.getVia());
        updatedPerparatore.setCap(preparatore.getCap());
        return utenteRepository.save(updatedPerparatore);
    }

    /**
     * Questo metodo permette di cercare un utente dalla sua email.
     *
     * @param email email dell'utente da cercare.
     * @return
     */
    @Override
    public Utente getUtenteByEmail(final String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email non valida");
        }
        Utente u = utenteRepository.findByEmail(email);
        if (u == null) {
            throw new IllegalArgumentException("Utente non trovato");
        }
        return u;
    }
}

