package it.fitdiary.backend.gestioneschedaalimentare.service;

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.*;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import it.fitdiary.backend.entity.enums.PASTO;
import it.fitdiary.backend.gestionealimento.repository.AlimentoRepository;
import it.fitdiary.backend.gestioneschedaalimentare.controller.dto.IstanzaAlimentoDTO;
import it.fitdiary.backend.gestioneschedaalimentare.repository.IstanzaAlimentoRepository;
import it.fitdiary.backend.gestioneschedaalimentare.repository.SchedaAlimentareRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BackendApplicationTest.class)
@ActiveProfiles("test")
public class GestioneSchedaAlimentareImplTest {
    private Alimento alimento;
    private IstanzaAlimento istanzaAlimento;
    private IstanzaAlimentoDTO istanzaAlimentoDTO;
    private Ruolo ruoloPreparatore;
    private Utente preparatore;
    private SchedaAlimentare schedaAlimentare;
    private List<IstanzaAlimento> alimenti;
    private List<IstanzaAlimentoDTO> alimentiDTO;
    @Mock
    private SchedaAlimentareRepository schedaAlimentareRepository;
    @Mock
    private IstanzaAlimentoRepository istanzaAlimentoRepository;
    @Mock
    private AlimentoRepository alimentoRepository;
    @InjectMocks
    private GestioneSchedaAlimentareServiceImpl gestioneSchedaAlimentareService;


    @BeforeEach
    public void setUp() throws IOException {


    }

    @Test
    public void creaSchedaAlimentare(){
        alimento = new Alimento(1L,"Pollo",100f,21f,46f,
                3f,"Alimenti/1.jpg");

        schedaAlimentare =
                new SchedaAlimentare();
        istanzaAlimento = new IstanzaAlimento(1L, GIORNO_SETTIMANA.LUNEDI, PASTO.COLAZIONE,16
                ,alimento,schedaAlimentare);
        istanzaAlimentoDTO = new IstanzaAlimentoDTO(istanzaAlimento.getGiornoDellaSettimana(),istanzaAlimento.getPasto(),istanzaAlimento.getGrammi(),istanzaAlimento.getId());
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);
        alimenti = new ArrayList<>();
        alimenti.add(istanzaAlimento);
        alimentiDTO = new ArrayList<>();
        alimentiDTO.add(istanzaAlimentoDTO);
        schedaAlimentare.setId(1L);
        schedaAlimentare.setPreparatore(preparatore);
        schedaAlimentare.setNome("schedaBuona");
        schedaAlimentare.setKcalAssunte(2000f);
        schedaAlimentare.setListaAlimenti(alimenti);
        schedaAlimentare.setDataCreazione(LocalDateTime.now());
        schedaAlimentare.setDataAggiornamento(LocalDateTime.now());


        when(alimentoRepository.findById(alimento.getId())).thenReturn(Optional.ofNullable(alimento));
        when(schedaAlimentareRepository.save(any())).thenReturn(schedaAlimentare);
        SchedaAlimentare schedaAlimentare1 =  gestioneSchedaAlimentareService.creaSchedaAlimentare(
                alimentiDTO,schedaAlimentare.getNome(),preparatore.getId());
        assertEquals(schedaAlimentare, schedaAlimentare1);

    }

    @Test
    public void modificaSchedaAlimentare(){
        alimento = new Alimento(1L,"Maiale",100f,21f,46f,
                3f,"Alimenti/1.jpg");

        schedaAlimentare =
                new SchedaAlimentare();
        istanzaAlimento = new IstanzaAlimento(1L, GIORNO_SETTIMANA.LUNEDI, PASTO.COLAZIONE,16
                ,alimento,schedaAlimentare);
        istanzaAlimentoDTO = new IstanzaAlimentoDTO(istanzaAlimento.getGiornoDellaSettimana(),istanzaAlimento.getPasto(),istanzaAlimento.getGrammi(),istanzaAlimento.getId());
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);
        alimenti = new ArrayList<>();
        alimenti.add(istanzaAlimento);
        alimentiDTO = new ArrayList<>();
        alimentiDTO.add(istanzaAlimentoDTO);
        schedaAlimentare.setId(1L);
        schedaAlimentare.setPreparatore(preparatore);
        schedaAlimentare.setNome("schedaBuona");
        schedaAlimentare.setKcalAssunte(2000f);
        schedaAlimentare.setListaAlimenti(alimenti);
        schedaAlimentare.setDataCreazione(LocalDateTime.now());
        schedaAlimentare.setDataAggiornamento(LocalDateTime.now());

        when(schedaAlimentareRepository.findById(schedaAlimentare.getId())).
                thenReturn(Optional.ofNullable(schedaAlimentare));
        when(alimentoRepository.findById(istanzaAlimentoDTO.getIdAlimento())).
                thenReturn(Optional.ofNullable(alimento));
        when(schedaAlimentareRepository.save(any())).thenReturn(schedaAlimentare);

        assertEquals(schedaAlimentare, gestioneSchedaAlimentareService.modificaSchedaAlimentare(
                alimentiDTO,schedaAlimentare.getNome(),preparatore.getId(),schedaAlimentare.getId()));
    }

    @Test
    public void getSchedeAlimentariByPreparaore(){
        alimento = new Alimento(1L,"Maiale",100f,21f,46f,
                3f,"Alimenti/1.jpg");

        schedaAlimentare =
                new SchedaAlimentare();
        istanzaAlimento = new IstanzaAlimento(1L, GIORNO_SETTIMANA.LUNEDI, PASTO.COLAZIONE,16
                ,alimento,schedaAlimentare);
        istanzaAlimentoDTO = new IstanzaAlimentoDTO(istanzaAlimento.getGiornoDellaSettimana(),istanzaAlimento.getPasto(),istanzaAlimento.getGrammi(),istanzaAlimento.getId());
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);
        alimenti = new ArrayList<>();
        alimenti.add(istanzaAlimento);
        alimentiDTO = new ArrayList<>();
        alimentiDTO.add(istanzaAlimentoDTO);
        schedaAlimentare.setId(1L);
        schedaAlimentare.setPreparatore(preparatore);
        schedaAlimentare.setNome("schedaBuona");
        schedaAlimentare.setKcalAssunte(2000f);
        schedaAlimentare.setListaAlimenti(alimenti);
        schedaAlimentare.setDataCreazione(LocalDateTime.now());
        schedaAlimentare.setDataAggiornamento(LocalDateTime.now());

        List<SchedaAlimentare> schedeAlimentarePreparatore = new ArrayList<>();
        schedeAlimentarePreparatore.add(schedaAlimentare);

        when(schedaAlimentareRepository.findAllByPreparatoreId(preparatore.getId())).
                thenReturn(schedeAlimentarePreparatore);

        assertEquals(schedeAlimentarePreparatore, gestioneSchedaAlimentareService.
                getSchedeAlimentariByPreparaore(preparatore.getId()));

    }

    @Test
    public void getSchedeAlimentariById(){
        alimento = new Alimento(1L,"Maiale",100f,21f,46f,
                3f,"Alimenti/1.jpg");

        schedaAlimentare =
                new SchedaAlimentare();
        istanzaAlimento = new IstanzaAlimento(1L, GIORNO_SETTIMANA.LUNEDI, PASTO.COLAZIONE,16
                ,alimento,schedaAlimentare);
        istanzaAlimentoDTO = new IstanzaAlimentoDTO(istanzaAlimento.getGiornoDellaSettimana(),istanzaAlimento.getPasto(),istanzaAlimento.getGrammi(),istanzaAlimento.getId());
        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);
        alimenti = new ArrayList<>();
        alimenti.add(istanzaAlimento);
        alimentiDTO = new ArrayList<>();
        alimentiDTO.add(istanzaAlimentoDTO);
        schedaAlimentare.setId(1L);
        schedaAlimentare.setPreparatore(preparatore);
        schedaAlimentare.setNome("schedaBuona");
        schedaAlimentare.setKcalAssunte(2000f);
        schedaAlimentare.setListaAlimenti(alimenti);
        schedaAlimentare.setDataCreazione(LocalDateTime.now());
        schedaAlimentare.setDataAggiornamento(LocalDateTime.now());

        when(schedaAlimentareRepository.findById(schedaAlimentare.getId())).
                thenReturn(Optional.ofNullable(schedaAlimentare));

        assertEquals(schedaAlimentare, gestioneSchedaAlimentareService.
                getSchedeAlimentariById(schedaAlimentare.getId()));
    }

}
