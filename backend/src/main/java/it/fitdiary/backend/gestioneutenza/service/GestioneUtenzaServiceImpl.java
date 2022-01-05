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
     * @param nome      nome del cliente.
     * @param cognome   cognome del cliente.
     * @param emailCliente     email del cliente.
     * @return utente inserito nel sistema.
     * @throws IllegalArgumentException
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
     * @param idPrep rappresenta l' id del preparatore
     * @param idCli  rappresenta l' id del cliente
     * @return @return vero se il cliente fa parte della lista dei clienti di quel preparatore, falso altrimenti
     */
    @Override
    public boolean existsByPreparatoreAndId(Long idPrep, Long idCli) {
        return   utenteRepository.existsByPreparatoreAndId(utenteRepository.getById(idPrep),idCli);
    }

    /**
     * Questo metodo permette di inserire
     * i dati nel sistema ad un cliente.
     *
     * @param idCliente rappresenta l'insieme
     *               dei dati personali di un utente.
     * @param clienteModificato
     * @return utente rappresenta l'utente
     * con i nuovi dati inserito nel database.
     * @throws IllegalArgumentException
     * lancia l'errore generato da un input errato.
     */
    @Override
    public Utente inserimentoDatiPersonaliCliente(final Long idCliente,
                                                  final Utente
                                                          clienteModificato)
            throws IllegalArgumentException {
        if (idCliente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        Utente newUtente = utenteRepository.getById(idCliente);
        if (newUtente == null) {
            throw new IllegalArgumentException(
                    "Utente non presente del Database");
        }
        newUtente.setDataNascita(clienteModificato.getDataNascita());
        newUtente.setTelefono(clienteModificato.getTelefono());
        newUtente.setCitta(clienteModificato.getCitta());
        newUtente.setVia(clienteModificato.getVia());
        newUtente.setCap(clienteModificato.getCap());
        return utenteRepository.save(newUtente);
    }

    /**
     * Questo metodo permette al cliente di inserire i dati
     * da modificare nel sistema.
     *
     * @param idCliente rappresenta l'id del cliente
     * @param clienteModificato rappresenta l'insieme
     * dei dati personali di un utente.
     * @return utente rappresenta l'utente
     * con i nuovi dati inserito nel database.
     * @throws IllegalArgumentException
     * lancia l'errore generato da un input errato.
     */
    @Override
    public Utente modificaDatiPersonaliCliente(
            final Long idCliente,
            final Utente clienteModificato
    ) throws IllegalArgumentException {
        if (clienteModificato == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        Utente newUtente = utenteRepository.getById(idCliente);
        if (newUtente == null) {
            throw new IllegalArgumentException(
                    "Utente non presente del Database");
        }
        newUtente.setNome(clienteModificato.getNome());
        newUtente.setCognome(clienteModificato.getCognome());
        newUtente.setDataAggiornamento(
                clienteModificato.getDataAggiornamento());
        newUtente.setEmail(clienteModificato.getEmail());
        newUtente.setPassword(
                passwordEncoder.encode(clienteModificato.getPassword()));
        newUtente.setDataNascita(clienteModificato.getDataNascita());
        newUtente.setTelefono(clienteModificato.getTelefono());
        newUtente.setCitta(clienteModificato.getCitta());
        newUtente.setVia(clienteModificato.getVia());
        newUtente.setCap(clienteModificato.getCap());
        return utenteRepository.save(newUtente);
    }

    /**
     * Questo metodo permette di aggiornare
     * i dati presenti nel database di un utente.
     *
     * @param preparatoreModificato rappresenta l' insieme di
     * tutti i dati personali di un preparatore
     * che devono essere aggiornati.
     * @return updatedPerparatore rappresenta l' insieme
     * di dati personali di un perparatore aggiornati.
     * @throws IllegalArgumentException
     * lancia l'errore generato da un input errato.
     */
    @Override
    public Utente modificaDatiPersonaliPreparatore(
            final Long idPreparatore,
            final Utente preparatoreModificato
    ) throws IllegalArgumentException {
        Utente preparatoreDb = utenteRepository.getById(idPreparatore);
        if (preparatoreModificato == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        if (preparatoreDb == null) {
            throw new IllegalArgumentException(
                    "Utente non presente del Database");
        }
        preparatoreDb.setNome(preparatoreModificato.getNome());
        preparatoreDb.setCognome(preparatoreModificato.getCognome());
        preparatoreDb.setDataAggiornamento(
                preparatoreModificato.getDataAggiornamento());
        preparatoreDb.setEmail(preparatoreModificato.getEmail());
        preparatoreDb.setPassword(
                passwordEncoder.encode(preparatoreModificato.getPassword()));
        preparatoreDb.setDataNascita(preparatoreModificato.getDataNascita());
        preparatoreDb.setTelefono(preparatoreModificato.getTelefono());
        preparatoreDb.setCitta(preparatoreModificato.getCitta());
        preparatoreDb.setVia(preparatoreModificato.getVia());
        preparatoreDb.setCap(preparatoreModificato.getCap());
        return utenteRepository.save(preparatoreDb);
    }

    /**
     * Questo metodo permette di cercare un utente dal suo id.
     *
     * @param idUtente id dell'utente.
     * @return
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
}

