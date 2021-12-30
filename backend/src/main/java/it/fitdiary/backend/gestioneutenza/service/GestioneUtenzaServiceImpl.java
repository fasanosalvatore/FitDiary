package it.fitdiary.backend.gestioneutenza.service;

import com.github.curiousoddman.rgxgen.RgxGen;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.repository.RuoloRepository;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import it.fitdiary.backend.utility.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GestioneUtenzaServiceImpl implements GestioneUtenzaService, UserDetailsService {

    private final UtenteRepository utenteRepository;
    private final RuoloRepository ruoloRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public Utente registrazione(Utente utente) throws IllegalArgumentException {
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        if (utenteRepository.existsByEmail(utente.getEmail())) {
            throw new IllegalArgumentException("email già presente nel " +
                    "database");
        }
        utente.setRuolo(ruoloRepository.findByNome("PREPARATORE"));
        utente.setAttivo(false);
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        return utenteRepository.save(utente);
    }

    /**
     * Questo metodo permette di inserire un cliente e di associarlo ad un preparatore
     *
     * @param nome      nome del cliente
     * @param cognome   cognome del cliente
     * @param email     email del cliente
     * @param emailPrep email del preparatore
     * @return utente inserito nel sistema
     * @throws IllegalArgumentException
     */
    @Override
    public Utente inserisciCliente(String nome, String cognome, String email, String emailPrep) throws IllegalArgumentException {
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
        RgxGen rgxGen = new RgxGen("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$");
        String password = rgxGen.generate();
        emailService.sendSimpleMessage(newUtente.getEmail(), "Benvenuto in FitDiary!", "Ecco la tua password per accedere: \n"+password);
        newUtente.setPassword(passwordEncoder.encode(password));
        return utenteRepository.save(newUtente);
    }

    /**
     * Questo metodo trova un utente dalla email per la verifica del login
     *
     * @param email email dell'utente da cercare
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByEmail(email);
        if (utente == null) {
            log.error("Utente non trovato nel database");
            throw new UsernameNotFoundException("Utente non trovato nel database");
        } else {
            log.info("Utente trovato nel database: {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        log.info("Ruolo: {}", utente.getRuolo().getNome());
        authorities.add(new SimpleGrantedAuthority(utente.getRuolo().getNome()));

        return new org.springframework.security.core.userdetails.User(utente.getEmail(), utente.getPassword(), authorities);
    }

    /**
     * Questo metodo permette di inserire i dati nel sistema ad un cliente
     *
     * @param utente rappresenta l'insieme dei dati personali di un utente
     * @return utente rappresenta l'utente con i nuovi dati inserito nel database
     * @throws IllegalArgumentException lancia l'errore generato da un input errato
     */
    @Override
    public Utente inserimentoDatiPersonaliCliente(Utente utente) throws IllegalArgumentException {
        Utente newUtente = utenteRepository.findById(utente.getId()).orElse(null);
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        if (newUtente == null) {
            throw new IllegalArgumentException("Utente non presente del Database");
        }
        newUtente.setDataNascita(utente.getDataNascita());
        newUtente.setTelefono(utente.getTelefono());
        newUtente.setCitta(utente.getCitta());
        newUtente.setVia(utente.getVia());
        newUtente.setCap(utente.getCap());
        return utenteRepository.save(newUtente);
    }

    /**
     * Questo metodo permette di inserire i dati da modificare nel sistema ad un cliente
     *
     * @param utente rappresenta l'insieme dei dati personali di un utente
     * @return utente rappresenta l'utente con i nuovi dati inserito nel database
     * @throws IllegalArgumentException lancia l'errore generato da un input errato
     */
    @Override
    public Utente modificaDatiPersonaliCliente(Utente utente) throws IllegalArgumentException {
        Utente newUtente = utenteRepository.findById(utente.getId()).orElse(null);
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        if (newUtente == null) {
            throw new IllegalArgumentException("Utente non presente del Database");
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
     * Questo metodo permette di aggiornare i dati presenti nel database di un utente
     *
     * @param preparatore rappresenta l' insieme di tutti i dati personali di un preparatore che devono essere aggiornati
     * @return updatedPerparatore rappresenta l' insieme di dati personali di un perparatore aggiornati
     * @throws IllegalArgumentException lancia l'errore generato da un input errato
     */
    @Override
    public Utente modificaDatiPersonaliPreparatore(Utente preparatore, String email) throws IllegalArgumentException {
        Utente updatedPerparatore = utenteRepository.findByEmail(email);
        if (preparatore == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        if (updatedPerparatore == null) {
            throw new IllegalArgumentException("Utente non presente del Database");
        }
        updatedPerparatore.setNome(preparatore.getNome());
        updatedPerparatore.setCognome(preparatore.getCognome());
        updatedPerparatore.setDataAggiornamento(preparatore.getDataAggiornamento());
        updatedPerparatore.setEmail(preparatore.getEmail());
        updatedPerparatore.setPassword(passwordEncoder.encode(preparatore.getPassword()));
        updatedPerparatore.setDataNascita(preparatore.getDataNascita());
        updatedPerparatore.setTelefono(preparatore.getTelefono());
        updatedPerparatore.setCitta(preparatore.getCitta());
        updatedPerparatore.setVia(preparatore.getVia());
        updatedPerparatore.setCap(preparatore.getCap());
        return utenteRepository.save(updatedPerparatore);
    }

    /**
     * Questo metodo permette di cercare un utente dalla sua email
     *
     * @param email email dell'utente da cercare
     * @return
     */
    @Override
    public Utente getUtenteByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email non valida");
        }
        return utenteRepository.findByEmail(email);
    }
}
