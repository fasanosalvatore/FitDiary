package it.fitdiary.backend.gestioneutenza.service;

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.repository.RuoloRepository;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import it.fitdiary.backend.utility.service.EmailServiceImpl;
import it.fitdiary.backend.utility.PasswordGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BackendApplicationTest.class)
@ActiveProfiles("test")
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

    public GestioneUtenzaServiceImplTest() {
    }

    @Test
    public void inserimentoDatiPersonaliCliente() {
        Ruolo r = new Ruolo(2L, "CLIENTE", null, null);
        Utente utenteNonModificato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                null, null, null, null, null, null, null, null, null, r, null, null, null);
        Utente utenteModificato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, null, null, "3894685921", "Francesco rinaldo", "94061", "Agropoli", null, r, null, null, null);
        when(utenteRepository.findById(utenteNonModificato.getId())).thenReturn(java.util.Optional.of(utenteModificato));
        when(utenteRepository.save(utenteModificato)).thenReturn(utenteModificato);
        assertEquals(utenteModificato, gestioneUtenzaService.inserimentoDatiPersonaliCliente(utenteModificato));
    }
    @Test
    public void inserimentoDatiPersonaliClienteUtenteNullo() {
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.inserimentoDatiPersonaliCliente(null));
    }
    @Test
    public void inserimentoDatiPersonaliClienteUtenteNonPresenteNelDataBase() {
        Ruolo r = new Ruolo(2L, "CLIENTE", null, null);
        Utente utenteNonModificato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                null, null, null, null, null, null, null, null, null, r, null, null, null);
        Utente utenteModificato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, null, null, "3894685921", "Francesco rinaldo", "94061", "Agropoli", null, r, null, null, null);
        when(utenteRepository.findById(utenteNonModificato.getId())).thenReturn(java.util.Optional.of(utenteModificato));
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.inserimentoDatiPersonaliCliente(null));

    }
    @Test
    public void modificaDatiPersonaliCliente() {
        Ruolo r = new Ruolo(2L, "CLIENTE", null, null);
        Utente utenteNonModificato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                null, null, null, null, null, null, null, null, null, r, null, null, null);
        Utente utenteModificato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, null, null, "3894685921", "Francesco rinaldo", "94061", "Agropoli", null, r, null, null, null);
        when(utenteRepository.findById(utenteNonModificato.getId())).thenReturn(Optional.of(utenteModificato));
        when(utenteRepository.save(utenteModificato)).thenReturn(utenteModificato);
        when(passwordEncoder.encode(utenteModificato.getPassword())).thenReturn(utenteModificato.getPassword());
        assertEquals(utenteModificato, gestioneUtenzaService.modificaDatiPersonaliCliente(utenteModificato));
    }
    @Test
    public void modificaDatiPersonaliClienteUtenteNullo() {
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.modificaDatiPersonaliCliente(null));
    }
    @Test
    public void modificaDatiPersonaliClienteUtenteNonPresenteNelDataBase() {
        Ruolo r = new Ruolo(2L, "CLIENTE", null, null);
        Utente utenteNonModificato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                null, null, null, null, null, null, null, null, null, r, null, null, null);
        Utente utenteModificato = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, null, null, "3894685921", "Francesco rinaldo", "94061", "Agropoli", null, r, null, null, null);
        when(utenteRepository.findById(utenteNonModificato.getId())).thenReturn(java.util.Optional.of(utenteModificato));
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.modificaDatiPersonaliCliente(null));

    }
    @Test
    public void modificaDatiPersonaliPreparatore() {
        Ruolo ruolo = new Ruolo(2L, "PREPARATORE", null, null);
        Utente utente = new Utente(1L, "Michele", "De Marco", "dani5@gmail.com", "Trappo#98", true,
                LocalDate.parse("2000-03-03"), null, null, null, "3459666587", "Francesco La Francesca", "84126", "Salerno", null, ruolo, null, null, null);
        Utente updatedUtente = new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com", "Trappo#98", true,
                null, null, null, null, null, null, null, null, null, ruolo, null, null, null);
        when(utenteRepository.findById(updatedUtente.getId())).thenReturn(java.util.Optional.of(updatedUtente));
        when(utenteRepository.save(utente)).thenReturn(utente);
        when(passwordEncoder.encode(utente.getPassword())).thenReturn(utente.getPassword());
        assertEquals(utente, gestioneUtenzaService.modificaDatiPersonaliCliente(utente));
    }
    @Test
    public void modificaDatiPersonaliPreparatoreUtenteNullo() {
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.modificaDatiPersonaliCliente(null));
    }
    @Test
    public void modificaDatiPersonaliPreparatoreUtenteNonPresenteNelDataBase() {
        Ruolo ruolo = new Ruolo(2L, "PREPARATORE", null, null);
        Utente utente = new Utente(1L, "Michele", "De Marco", "dani5@gmail.com", "Trappo#98", true,
                LocalDate.parse("2000-03-03"), null, null, null, "3459666587", "Francesco La Francesca", "84126", "Salerno", null, ruolo, null, null, null);
        Utente updatedUtente = new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com", "Trappo#98", true,
                null, null, null, null, null, null, null, null, null, ruolo, null, null, null);
        when(utenteRepository.findById(updatedUtente.getId())).thenReturn(java.util.Optional.of(updatedUtente));
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.modificaDatiPersonaliCliente(null));

    }

    @Test
    public void inserisciCliente() {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);
        Ruolo ruoloCliente = new Ruolo(2L, "CLIENTE", null, null);
        String nome = "Rebecca";
        String cognome = "Melenchi";
        String email = "rebmel@gmail.com";
        String emailPrep = "davide@gmail.com";
        String password = "Melenchi123*";
        Utente preparatore = new Utente(1L, "Davide", "La Gamba", emailPrep, "Davide123*", true,
                LocalDate.parse("2000-03-03"), null, null, null, "3313098075", "Michele Santoro", "81022", "Caserta", null, ruoloPrep, null, null, null);
        Utente newUtentePre = new Utente(null, nome, cognome, email, password, true,
                LocalDate.parse("1990-01-01"), null, null, null, null, null, null, null, preparatore, ruoloCliente, null, null, null);
        Utente newUtentePost = new Utente(2L, nome, cognome, email, password, true,
                LocalDate.parse("1990-01-01"), null, null, null, null, null, null, null, preparatore, ruoloCliente, null, null, null);
        when(utenteRepository.findByEmail(emailPrep)).thenReturn(preparatore);
        when(utenteRepository.findByEmail(email)).thenReturn(null);
        when(utenteRepository.save(newUtentePre)).thenReturn(newUtentePost);
        when(ruoloRepository.findByNome("CLIENTE")).thenReturn(ruoloCliente);
        when(pwGen.generate()).thenReturn("Melenchi123*");
        doNothing().when(emailService).sendSimpleMessage(newUtentePre.getEmail(), "Benvenuto in FitDiary!", "Ecco la tua password per accedere: \n" + password);
        when(passwordEncoder.encode(password)).thenReturn(password);
        assertEquals(newUtentePost, gestioneUtenzaService.inserisciCliente(nome, cognome, email, emailPrep));
    }

    @Test
    public void inserisciClientethrowsIllegalPrep() {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);
        Ruolo ruoloCliente = new Ruolo(2L, "CLIENTE", null, null);
        String nome = "Rebecca";
        String cognome = "Melenchi";
        String email = "rebmel@gmail.com";
        String emailPrep = "davide@gmail.com";
        String password = "Melenchi123*";
        Utente preparatore = new Utente(1L, "Davide", "La Gamba", emailPrep, "Davide123*", true,
                LocalDate.parse("2000-03-03"), null, null, null, "3313098075", "Michele Santoro", "81022", "Caserta", null, ruoloPrep, null, null, null);
        Utente newUtentePre = new Utente(null, nome, cognome, email, password, true,
                LocalDate.parse("1990-01-01"), null, null, null, null, null, null, null, preparatore, ruoloCliente, null, null, null);
        Utente newUtentePost = new Utente(2L, nome, cognome, email, password, true,
                LocalDate.parse("1990-01-01"), null, null, null, null, null, null, null, preparatore, ruoloCliente, null, null, null);
        when(utenteRepository.findByEmail(emailPrep)).thenReturn(null);
        when(utenteRepository.findByEmail(email)).thenReturn(null);
        when(utenteRepository.save(newUtentePre)).thenReturn(newUtentePost);
        when(ruoloRepository.findByNome("CLIENTE")).thenReturn(ruoloCliente);
        when(pwGen.generate()).thenReturn("Melenchi123*");
        doNothing().when(emailService).sendSimpleMessage(newUtentePre.getEmail(), "Benvenuto in FitDiary!", "Ecco la tua password per accedere: \n" + password);
        when(passwordEncoder.encode(password)).thenReturn(password);

        assertThrows(IllegalArgumentException.class, () -> {
            gestioneUtenzaService.inserisciCliente(nome, cognome, email, emailPrep);
        });


    }

    @Test
    public void inserisciClientethrowsIllegalCliente() {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);
        Ruolo ruoloCliente = new Ruolo(2L, "CLIENTE", null, null);
        String nome = "Rebecca";
        String cognome = "Melenchi";
        String email = "rebmel@gmail.com";
        String emailPrep = "davide@gmail.com";
        String password = "Melenchi123*";
        Utente preparatore = new Utente(1L, "Davide", "La Gamba", emailPrep, "Davide123*", true,
                LocalDate.parse("2000-03-03"), null, null, null, "3313098075", "Michele Santoro", "81022", "Caserta", null, ruoloPrep, null, null, null);
        Utente newUtentePre = new Utente(null, nome, cognome, email, password, true,
                LocalDate.parse("1990-01-01"), null, null, null, null, null, null, null, preparatore, ruoloCliente, null, null, null);
        Utente newUtentePost = new Utente(2L, nome, cognome, email, password, true,
                LocalDate.parse("1990-01-01"), null, null, null, null, null, null, null, preparatore, ruoloCliente, null, null, null);
        when(utenteRepository.findByEmail(emailPrep)).thenReturn(preparatore);
        when(utenteRepository.findByEmail(email)).thenReturn(newUtentePost);
        when(utenteRepository.save(newUtentePre)).thenReturn(newUtentePost);
        when(ruoloRepository.findByNome("CLIENTE")).thenReturn(ruoloCliente);
        when(pwGen.generate()).thenReturn("Melenchi123*");
        doNothing().when(emailService).sendSimpleMessage(newUtentePre.getEmail(), "Benvenuto in FitDiary!", "Ecco la tua password per accedere: \n" + password);
        when(passwordEncoder.encode(password)).thenReturn(password);

        assertThrows(IllegalArgumentException.class, () -> {
            gestioneUtenzaService.inserisciCliente(nome, cognome, email, emailPrep);
        });
    }

    @Test
    public void getUtenteByEmail() {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);
        String email = "davide@gmail.com";
        Utente utente = new Utente(1L, "Davide", "La Gamba", email, "Davide123*", true,
                LocalDate.parse("2000-03-03"), null, null, null, "3313098075", "Michele Santoro", "81022", "Caserta", null, ruoloPrep, null, null, null);
        when(utenteRepository.findByEmail(email)).thenReturn(utente);
        assertEquals(utente, gestioneUtenzaService.getUtenteByEmail(email));
    }

    @Test
    public void getUtenteByEmailThrowsIllegalEmail() {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);
        String email = null;
        Utente utente = new Utente(1L, "Davide", "La Gamba", "davide@gmail.com", "Davide123*", true,
                LocalDate.parse("2000-03-03"), null, null, null, "3313098075", "Michele Santoro", "81022", "Caserta", null, ruoloPrep, null, null, null);
        when(utenteRepository.findByEmail(email)).thenReturn(utente);
        assertThrows(IllegalArgumentException.class, () -> {
            gestioneUtenzaService.getUtenteByEmail(email);
        });
    }

    @Test
    public void getUtenteByEmailThrowsIllegalUtente() {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);
        String email = "davide@gmail.com";
        Utente utente = new Utente(1L, "Davide", "La Gamba", "fabrizio@gmail.com", "Davide123*", true,
                LocalDate.parse("2000-03-03"), null, null, null, "3313098075", "Michele Santoro", "81022", "Caserta", null, ruoloPrep, null, null, null);
        when(utenteRepository.findByEmail(email)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> {
            gestioneUtenzaService.getUtenteByEmail(email);
        });
    }

    @Test
    public void registrazioneEmailError() {
        when(this.utenteRepository.existsByEmail((String) any())).thenReturn(true);
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);
        Utente utente = new Utente(null, "Daniele", "De Marco", "fabrizio" +
                "@gmail" +
                ".com", "Daniele123*", true, LocalDate.parse("2000-03-03"), null,
                null, null, "33985458", "Salvo D'Acquisto", "84047",
                "Capaccio", null, ruoloPrep, null, null, null);
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.registrazione(utente));
        verify(this.utenteRepository).existsByEmail((String) any());
    }
    @Test
    public void registrazione() {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);
        Utente utente = new Utente(null, "Daniele", "De Marco", "fabrizio" +
                "@gmail" +
                ".com", "Daniele123*", true, LocalDate.parse("2000-03-03"), null,
                null, null, "33985458", "Salvo D'Acquisto", "84047",
                "Capaccio", null, ruoloPrep, null, null, null);
        Utente newUtente = new Utente(1l, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com",
                "Daniele123*", true, LocalDate.parse("2000-03-03"), null,
                null, null, "33985458", "Salvo D'Acquisto", "84047",
                "Capaccio", null, ruoloPrep, null, null, null);
        when(this.utenteRepository.save(utente)).thenReturn(newUtente);
        when(this.utenteRepository.existsByEmail((String) any())).thenReturn(false);
        when(this.ruoloRepository.findByNome((String) any())).thenReturn(ruoloPrep);
        assertSame(newUtente,
                this.gestioneUtenzaService.registrazione(utente));
        verify(this.utenteRepository).existsByEmail((String) any());
        verify(this.utenteRepository).save(utente);
    }
    @Test
    public void registrazioneUtenteNull() {
        assertThrows(IllegalArgumentException.class,
                () -> this.gestioneUtenzaService.registrazione(null));
    }
}