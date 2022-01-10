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
     * @param preparatore nuovo preparatore
     * @return preparatore con l'id
     * @throws IllegalArgumentException in caso di email già presente nel db
     *                                  o utente non valido.
     */
    @Override
    public Utente registrazione(final Utente preparatore)
            throws IllegalArgumentException {
        System.out.println(preparatore);
        if (preparatore == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        if (utenteRepository.existsByEmail(preparatore.getEmail())) {
            throw new IllegalArgumentException(
                    "email già presente nel " + "database");
        }
        preparatore.setRuolo(ruoloRepository.findByNome("Preparatore"));
        preparatore.setAttivo(true);
        preparatore.setPassword(
                passwordEncoder.encode(preparatore.getPassword()));
        return utenteRepository.save(preparatore);
    }

    /**
     * Questo metodo permette di inserire un cliente
     * e di associarlo ad un preparatore.
     *
     * @param idPreparatore l'id del preparatore che sta inserendo il cliente
     * @param nome          nome del cliente.
     * @param cognome       cognome del cliente.
     * @param emailCliente  email del cliente.
     * @return utente inserito nel sistema.
     * @throws IllegalArgumentException eccezione
     */
    @Override
    public Utente inserisciCliente(final Long idPreparatore,
                                   final String nome,
                                   final String cognome,
                                   final String emailCliente)
            throws IllegalArgumentException, MailException {
        Utente preparatore = utenteRepository.getById(idPreparatore);
        if (preparatore == null) {
            throw new IllegalArgumentException("Preparatore non valido");
        }
        Utente cliente = utenteRepository.findByEmail(emailCliente);
        if (cliente != null) {
            throw new IllegalArgumentException("Cliente già presente");
        }
        Utente newUtente = new Utente();
        newUtente.setNome(nome);
        newUtente.setCognome(cognome);
        newUtente.setEmail(emailCliente);
        newUtente.setRuolo(ruoloRepository.findByNome("CLIENTE"));
        newUtente.setAttivo(true);
        newUtente.setPreparatore(preparatore);
        String password = passwordGenerator.generate();
        emailService.sendSimpleMessage(newUtente, password);
        newUtente.setPassword(passwordEncoder.encode(password));
        return utenteRepository.save(newUtente);
    }

    /**
     * Questo metodo trova un utente dalla email per la verifica del login.
     *
     * @param email email dell'utente da cercare.
     * @return UserDetails
     * @throws UsernameNotFoundException eccezione
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
        fitDiaryUser.setId(utente.getId());
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
     * Questo metodo permette all'utente di inserire i dati
     * da modificare nel sistema.
     *
     * @param id     rappresenta l'id del utente
     * @param utente rappresenta l'insieme
     *               dei dati personali di un utente.
     * @return utente rappresenta l'utente
     * con i nuovi dati inserito nel database.
     * @throws IllegalArgumentException lancia l'errore generato
     * da un input errato.
     */
    @Override
    public Utente modificaDatiPersonali(
            final Long id,
            final Utente utente
    ) throws IllegalArgumentException {
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        Utente newUtente = utenteRepository.getById(id);
        if (newUtente == null) {
            throw new IllegalArgumentException(
                    "Utente non presente del Database");
        }
        if (utente.getNome() != null && !utente.getNome().equals("")) {
            newUtente.setNome(utente.getNome());
        }
        if (utente.getCognome() != null && !utente.getCognome().equals("")) {
            newUtente.setCognome(utente.getCognome());
        }
        if (utente.getEmail() != null && !utente.getEmail().equals("")) {
            newUtente.setEmail(utente.getEmail());
        }
        if (utente.getPassword() != null && !utente.getPassword().equals("")) {
            newUtente.setPassword(passwordEncoder.encode(utente.getPassword()));
        }
        if (utente.getDataNascita() != null) {
            newUtente.setDataNascita(utente.getDataNascita());
        }
        if (utente.getTelefono() != null && !utente.getTelefono().equals("")) {
            newUtente.setTelefono(utente.getTelefono());
        }
        if (utente.getCitta() != null && !utente.getCitta().equals("")) {
            newUtente.setCitta(utente.getCitta());
        }
        if (utente.getVia() != null && !utente.getVia().equals("")) {
            newUtente.setVia(utente.getVia());
        }
        if (utente.getCap() != null && !utente.getCap().equals("")) {
            newUtente.setCap(utente.getCap());
        }
        return utenteRepository.save(newUtente);
    }

    /**
     * Questo metodo permette di cercare un utente dal suo id.
     *
     * @param idUtente id dell'utente.
     * @return utente
     */
    @Override
    public Utente getById(final Long idUtente) {
        if (idUtente == null) {
            throw new IllegalArgumentException("Id non valido");
        }
        Utente utente = utenteRepository.getById(idUtente);
        if (utente == null) {
            throw new IllegalArgumentException("Utente non trovato");
        }
        return utente;
    }

    /**
     * @param preparatore rappresenta il preparatore
     * @param idCliente   rappresenta l' id del cliente
     * @return true se il cliente è associato al preparatore
     */
    @Override
    public Boolean existsByPreparatoreAndId(final Utente preparatore,
                                            final Long idCliente) {
        if (idCliente == null) {
            throw new IllegalArgumentException("Id cliente non valido");
        }
        if (preparatore == null) {
            throw new IllegalArgumentException("Preparatore non valido");
        }
        return utenteRepository.existsByPreparatoreAndId(preparatore,
                idCliente);
    }
}

