package it.fitdiary.backend.gestioneutenza.service;

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

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
    private BCryptPasswordEncoder passwordEncoder;

    public GestioneUtenzaServiceImplTest() {
    }

    @Test
    public void inserimentoDatiPersonaliCliente() {
        Ruolo r = new Ruolo(2L, "CLIENTE", null, null);
        Utente u = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, null, null, "3894685921", "Francesco rinaldo", "94061", "Agropoli", null, r, null, null, null);
        Utente ut = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                null, null, null, null, null, null, null, null, null, r, null, null, null);
        when(utenteRepository.findById(ut.getId())).thenReturn(java.util.Optional.of(ut));
        when(utenteRepository.save(u)).thenReturn(u);
        assertEquals(u, gestioneUtenzaService.inserimentoDatiPersonaliCliente(u));
    }

    @Test
    public void modificaDatiPersonaliCliente() {
        Ruolo r = new Ruolo(2L, "CLIENTE", null, null);
        Utente u = new Utente(1L, "Francesca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, null, null, "3894685921", "Francesco rinaldo", "94061", "Agropoli", null, r, null, null, null);
        Utente ut = new Utente(1L, "Rebecca", "Di Matteo", "beccadimatteoo@gmail.com", "Becca123*", true,
                null, null, null, null, null, null, null, null, null, r, null, null, null);
        when(utenteRepository.findById(ut.getId())).thenReturn(java.util.Optional.of(ut));
        when(utenteRepository.save(u)).thenReturn(u);
        when(passwordEncoder.encode(u.getPassword())).thenReturn(u.getPassword());
        assertEquals(u, gestioneUtenzaService.modificaDatiPersonaliCliente(u));
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

}