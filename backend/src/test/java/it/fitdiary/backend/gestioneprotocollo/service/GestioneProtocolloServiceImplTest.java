package it.fitdiary.backend.gestioneprotocollo.service;

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.SchedaAllenamento;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneprotocollo.repository.AlimentoRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.ProtocolloRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BackendApplicationTest.class)
@ActiveProfiles("test")
class GestioneProtocolloServiceImplTest {
    @InjectMocks
    private GestioneProtocolloServiceImpl gestioneProtocolloServiceImpl;
    @Mock
    private ProtocolloRepository protocolloRepository;
    private Ruolo ruoloCliente;
    private Ruolo ruoloPreparatore;
    private Utente cliente;
    private Utente clienteAggiornato;
    private Utente preparatore;
    private Utente updatedPreparatore;
    private Protocollo protocollo;
    @Mock
    private AlimentoRepository alimentoRepository;

    @Before
    public void setUp() {
        ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);
        cliente = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true, null, null, null,
                null, null, null, null, ruoloCliente, null, null, null, null,
                null);
        clienteAggiornato = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);
        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);
        updatedPreparatore =
                new Utente(1L, "Michele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true,
                        LocalDate.parse("2000-03-03"), null, "3459666587",
                        "Francesco La Francesca", "84126", "Salerno", null,
                        ruoloPreparatore, null, null, null, null, null);
        protocollo = new Protocollo(1L, LocalDate.parse("2022-01-05"),
                new SchedaAlimentare(), new SchedaAllenamento(), cliente,
                preparatore, null, null);

    }

    @Test
    void visualizzaStoricoProtocolliCliente() {
        List<Protocollo> protocolloList = new ArrayList<Protocollo>();
        protocolloList.add(protocollo);
        when(protocolloRepository.findAllByCliente(cliente)).thenReturn(
                protocolloList);
        assertEquals(protocolloList,
                gestioneProtocolloServiceImpl.visualizzaStoricoProtocolliCliente(
                        cliente));
    }

    @Test
    void creazioneProtocolloSchedeNulle() throws IOException {

        assertThrows(IllegalArgumentException.class,
                () -> gestioneProtocolloServiceImpl.creazioneProtocollo(
                        protocollo, null, null));
    }

    @Test
    void creazioneProtocolloOnSuccess() throws IOException {
        Protocollo protocolloSuccess =
                new Protocollo(2L, LocalDate.parse("2022-01-07"),
                        new SchedaAlimentare(), new SchedaAllenamento(), null,
                        null, null, null);
        File schedaAlimentare = new File(
                getClass().getClassLoader().getResource("schedaAlimentare.csv")
                        .getFile());
        File schedaAllenamento = new File(
                getClass().getClassLoader().getResource("schedaAllenamento.csv")
                        .getFile());
        when(mock(GestioneProtocolloServiceImpl.class).inserisciSchedaAlimentare(
                protocolloSuccess,
                schedaAlimentare)).thenReturn(protocolloSuccess);
        when(mock(GestioneProtocolloServiceImpl.class).inserisciSchedaAllenamento(
                protocolloSuccess,
                schedaAllenamento)).thenReturn(protocolloSuccess);
        assertEquals(protocolloSuccess,
                gestioneProtocolloServiceImpl.creazioneProtocollo(protocollo,
                        schedaAlimentare, schedaAllenamento));
    }




    @Test
    void getByIdProtocolloSuccess() {
       when(protocolloRepository.existsById(1L)).thenReturn(true);
       when(protocolloRepository.getById(1L)).thenReturn(protocollo);
        assertEquals(protocollo, gestioneProtocolloServiceImpl.getByIdProtocollo(1L));
    }

    @Test
    void getByIdProtocolloIdNonValido() {
        assertThrows(IllegalArgumentException.class, () -> gestioneProtocolloServiceImpl.getByIdProtocollo(null));
    }

    @Test
    void getByIdProtocolloProtocolloNonEsistente() {
        when(protocolloRepository.existsById(1L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> gestioneProtocolloServiceImpl.getByIdProtocollo(1L));
    }

}

