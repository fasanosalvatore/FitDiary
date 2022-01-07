package it.fitdiary.backend.gestioneutenza.service;

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.repository.RuoloRepository;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import it.fitdiary.backend.utility.PasswordGenerator;
import it.fitdiary.backend.utility.service.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BackendApplicationTest.class)
public class GestioneUtenzaServiceImplTest {

    @InjectMocks
    private GestioneUtenzaServiceImpl gestioneUtenzaService;
    @Mock
    private UtenteRepository utenteRepository;
    @Mock
    private RuoloRepository ruoloRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private EmailServiceImpl emailService;
    @Mock
    private PasswordGenerator pwGen;
    private Ruolo ruoloCliente;
    private Ruolo ruoloPreparatore;
    private Utente cliente;
    private Utente clienteAggiornato;
    private Utente preparatore;
    private Utente updatedPreparatore;

    public GestioneUtenzaServiceImplTest() {
    }

    @BeforeEach
    public void setUp() {
        ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);
        cliente = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true, null, null, null,
                null, null, null, null, ruoloCliente, null, null, null, null, null);
        clienteAggiornato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921", "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);
        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com", "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null, null, null);
        updatedPreparatore = new Utente(1L, "Michele", "De Marco", "diodani5@gmail.com", "Trappo#98", true,
                LocalDate.parse("2000-03-03"), null, "3459666587", "Francesco La Francesca", "84126", "Salerno", null,
                ruoloPreparatore, null, null, null, null, null);

    }

    @Test
    public void inserimentoDatiPersonaliCliente() {
        when(utenteRepository.getById(cliente.getId())).thenReturn((clienteAggiornato));
        when(utenteRepository.save(clienteAggiornato)).thenReturn(clienteAggiornato);
        assertEquals(clienteAggiornato,
                gestioneUtenzaService.inserimentoDatiPersonaliCliente(cliente.getId(), clienteAggiornato));
    }

    @Test
    public void inserimentoDatiPersonaliClienteUtenteNullo() {
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.inserimentoDatiPersonaliCliente(null, null));
    }

    @Test
    public void inserimentoDatiPersonaliClienteUtenteNonPresenteNelDataBase() {
        when(utenteRepository.getById(cliente.getId())).thenReturn(clienteAggiornato);
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.inserimentoDatiPersonaliCliente(null, null));

    }

    @Test
    public void modificaDatiPersonaliCliente() {
        when(utenteRepository.getById(cliente.getId())).thenReturn((clienteAggiornato));
        when(utenteRepository.save(clienteAggiornato)).thenReturn(clienteAggiornato);
        when(passwordEncoder.encode(clienteAggiornato.getPassword())).thenReturn(clienteAggiornato.getPassword());
        assertEquals(clienteAggiornato,
                gestioneUtenzaService.modificaDatiPersonaliCliente(cliente.getId(), clienteAggiornato));
    }

    @Test
    public void modificaDatiPersonali_ClienteUtenteNullo_ThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.modificaDatiPersonaliCliente(null, null));
    }

    @Test
    public void modificaDatiPersonaliCliente_UtenteNonPresenteNelDataBase_ThrowException() {
        when(utenteRepository.getById(cliente.getId())).thenReturn(clienteAggiornato);
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.modificaDatiPersonaliPreparatore(null, null));

    }

    @Test
    public void modificaDatiPersonaliPreparatore_Success() {
        when(utenteRepository.getById(preparatore.getId())).thenReturn(updatedPreparatore);
        when(utenteRepository.save(updatedPreparatore)).thenReturn(updatedPreparatore);
        when(passwordEncoder.encode(updatedPreparatore.getPassword())).thenReturn(updatedPreparatore.getPassword());
        assertEquals(updatedPreparatore,
                gestioneUtenzaService.modificaDatiPersonaliPreparatore(updatedPreparatore.getId(), updatedPreparatore));
    }

    @Test
    public void modificaDatiPersonali_PreparatoreUtenteNullo_ThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.modificaDatiPersonaliPreparatore(null, null));
    }

    @Test
    public void modificaDatiPersonali_PreparatoreUtenteNonPresenteNelDataBase_ThrowException() {
        Utente updatedUtente =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com", "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null, null, null);
        when(utenteRepository.findById(updatedUtente.getId())).thenReturn(java.util.Optional.of(updatedUtente));
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.modificaDatiPersonaliPreparatore(null, null));

    }

    @Test
    public void inserisciCliente_Success() {
        String nome = "Rebecca";
        String cognome = "Melenchi";
        String email = "rebmel@gmail.com";
        String password = "Melenchi123*";
       Utente newUtentePre =
                new Utente(null, nome, cognome, email, password, true, LocalDate.parse("1990-01-01"), null, null, null,
                        null, null, preparatore, ruoloCliente, null, null, null, null, null);
        Utente newUtentePost =
                new Utente(2L, nome, cognome, email, password, true, LocalDate.parse("1990-01-01"), null, null, null,
                        null, null, preparatore, ruoloCliente, null, null, null, null, null);
        when(utenteRepository.getById(preparatore.getId())).thenReturn(preparatore);
        when(utenteRepository.findByEmail(email)).thenReturn(null);
        when(utenteRepository.save(newUtentePre)).thenReturn(newUtentePost);
        when(ruoloRepository.findByNome("CLIENTE")).thenReturn(ruoloCliente);
        when(pwGen.generate()).thenReturn("Melenchi123*");
        doNothing().when(emailService).sendSimpleMessage(newUtentePre.getEmail(), "Benvenuto in FitDiary!",
                "Ecco la tua password per accedere: \n" + password);
        when(passwordEncoder.encode(password)).thenReturn(password);
        assertEquals(newUtentePost, gestioneUtenzaService.inserisciCliente(preparatore.getId(), nome, cognome, email));
    }

    @Test
    public void inserisciClientethrowsIllegalPrep() {
        String nome = "Rebecca";
        String cognome = "Melenchi";
        String email = "rebmel@gmail.com";
        String password = "Melenchi123*";
       Utente newUtentePre =
                new Utente(null, nome, cognome, email, password, true, LocalDate.parse("1990-01-01"), null, null, null,
                        null, null, preparatore, ruoloCliente, null, null, null, null, null);
        Utente newUtentePost =
                new Utente(2L, nome, cognome, email, password, true, LocalDate.parse("1990-01-01"), null, null, null,
                        null, null, preparatore, ruoloCliente, null, null, null, null, null);
        when(utenteRepository.getById(preparatore.getId())).thenReturn(null);
        when(utenteRepository.save(newUtentePre)).thenReturn(newUtentePost);
        when(ruoloRepository.findByNome("CLIENTE")).thenReturn(ruoloCliente);
        when(pwGen.generate()).thenReturn("Melenchi123*");
        doNothing().when(emailService).sendSimpleMessage(newUtentePre.getEmail(), "Benvenuto in FitDiary!",
                "Ecco la tua password per accedere: \n" + password);
        when(passwordEncoder.encode(password)).thenReturn(password);
        assertThrows(IllegalArgumentException.class, () -> gestioneUtenzaService.inserisciCliente(preparatore.getId(), nome, cognome, email));
    }

    @Test
    public void inserisciClientethrowsIllegalCliente() {
        String nome = "Rebecca";
        String cognome = "Melenchi";
        String email = "rebmel@gmail.com";
        String password = "Melenchi123*";
        Utente newUtentePre =
                new Utente(null, nome, cognome, email, password, true, LocalDate.parse("1990-01-01"), null, null, null,
                        null, null, preparatore, ruoloCliente, null, null, null, null, null);
        Utente newUtentePost =
                new Utente(2L, nome, cognome, email, password, true, LocalDate.parse("1990-01-01"), null, null, null,
                        null, null, preparatore, ruoloCliente, null, null, null, null, null);
        when(utenteRepository.getById(preparatore.getId())).thenReturn(preparatore);
        when(utenteRepository.findByEmail(email)).thenReturn(newUtentePost);
        when(utenteRepository.save(newUtentePre)).thenReturn(newUtentePost);
        when(ruoloRepository.findByNome("CLIENTE")).thenReturn(ruoloCliente);
        when(pwGen.generate()).thenReturn("Melenchi123*");
        doNothing().when(emailService).sendSimpleMessage(newUtentePre.getEmail(), "Benvenuto in FitDiary!",
                "Ecco la tua password per accedere: \n" + password);
        when(passwordEncoder.encode(password)).thenReturn(password);
        assertThrows(IllegalArgumentException.class, () -> gestioneUtenzaService.inserisciCliente(preparatore.getId(), nome, cognome, email));
    }

    @Test
    public void getById() {
        when(utenteRepository.getById(1L)).thenReturn(cliente);
        assertEquals(cliente, gestioneUtenzaService.getById(1L));
    }

    @Test
    public void getByIdNull_ThrowsIllegalId() {
       when(utenteRepository.getById(null)).thenReturn(cliente);
        assertThrows(IllegalArgumentException.class, () -> gestioneUtenzaService.getById(null));
    }

    @Test
    public void getById_ThrowsIllegalUtente() {
        when(utenteRepository.getById(2L)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> gestioneUtenzaService.getById(2L));
    }

    @Test
    public void registrazioneEmailError() {
        when(this.utenteRepository.existsByEmail(any())).thenReturn(true);
        Utente utente = new Utente(null, "Daniele", "De Marco", "fabrizio" + "@gmail" + ".com", "Daniele123*", true,
                LocalDate.parse("2000-03-03"), null, "33985458", "Salvo D'Acquisto", "84047", "Capaccio", null,
                ruoloPreparatore, null, null, null, null, null);
        assertThrows(IllegalArgumentException.class, () -> this.gestioneUtenzaService.registrazione(utente));
        verify(this.utenteRepository).existsByEmail(any());
    }

    @Test
    public void registrazione() {
        Utente utente = new Utente(null, "Daniele", "De Marco", "fabrizio" + "@gmail" + ".com", "Daniele123*", true,
                LocalDate.parse("2000-03-03"), null, "33985458", "Salvo D'Acquisto", "84047", "Capaccio", null,
                ruoloPreparatore, null, null, null, null, null);
        Utente newUtente = new Utente(1L, "Daniele", "De Marco", "fabrizio" + "@gmail.com", "Daniele123*", true,
                LocalDate.parse("2000-03-03"), null, "33985458", "Salvo D'Acquisto", "84047", "Capaccio", null,
                ruoloPreparatore, null, null, null, null, null);
        when(this.utenteRepository.save(utente)).thenReturn(newUtente);
        when(this.utenteRepository.existsByEmail(any())).thenReturn(false);
        when(this.ruoloRepository.findByNome(any())).thenReturn(ruoloPreparatore);
        assertSame(newUtente, this.gestioneUtenzaService.registrazione(utente));
        verify(this.utenteRepository).existsByEmail(any());
        verify(this.utenteRepository).save(utente);
    }

    @Test
    public void registrazioneUtenteNull() {
        assertThrows(IllegalArgumentException.class, () -> this.gestioneUtenzaService.registrazione(null));
    }

    @Test
    public void existsByPreparatoreAndIdSuccessTrue() {
        List<Utente> clienti= new ArrayList<Utente>();
        clienti.add(cliente);
        preparatore.setListaClienti(clienti);
        cliente.setPreparatore(preparatore);
        when(this.utenteRepository.existsByPreparatoreAndId(preparatore, cliente.getId())).thenReturn(true);
        assertEquals(true, gestioneUtenzaService.existsByPreparatoreAndId(preparatore, cliente.getId()));
    }

    @Test
    public void existsByPreparatoreAndIdSuccessFalse() {
        List<Utente> clienti= new ArrayList<Utente>();
        clienti.add(cliente);
        preparatore.setListaClienti(clienti);
        cliente.setPreparatore(preparatore);
        when(this.utenteRepository.existsByPreparatoreAndId(preparatore, cliente.getId())).thenReturn(false);
        assertEquals(false, gestioneUtenzaService.existsByPreparatoreAndId(preparatore, cliente.getId()));
    }

    @Test
    public void existsByPreparatoreAndIdThrowsIllegalArgumentInvalidCliente() {
        List<Utente> clienti= new ArrayList<Utente>();
        clienti.add(cliente);
        preparatore.setListaClienti(clienti);
        cliente.setPreparatore(preparatore);
        assertThrows(IllegalArgumentException.class, () -> gestioneUtenzaService.existsByPreparatoreAndId(null, cliente.getId()));
    }

    @Test
    public void existsByPreparatoreAndIdThrowsIllegalArgumentInvalidPreparatore() {
        List<Utente> clienti= new ArrayList<Utente>();
        clienti.add(cliente);
        preparatore.setListaClienti(clienti);
        cliente.setPreparatore(preparatore);
        assertThrows(IllegalArgumentException.class, () -> gestioneUtenzaService.existsByPreparatoreAndId(preparatore, null));
    }
}