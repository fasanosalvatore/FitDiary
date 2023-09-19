package it.fitdiary.backend.gestioneschedaallenamento.service;

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.*;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import it.fitdiary.backend.gestioneesercizio.repository.EsercizioRepository;
import it.fitdiary.backend.gestioneschedaallenamento.controller.dto.IstanzaEsercizioDTO;
import it.fitdiary.backend.gestioneschedaallenamento.repository.IstanzaEsercizioRepository;
import it.fitdiary.backend.gestioneschedaallenamento.repository.SchedaAllenamentoRepository;
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
public class GestioneSchedaAllenamentoServiceTest {

    @Mock
    private SchedaAllenamentoRepository schedaAllenamentoRepository;
    @Mock
    private IstanzaEsercizioRepository istanzaEsercizioRepository;
    @Mock
    private EsercizioRepository esercizioRepository;
    @InjectMocks
    private GestioneSchedaAllenamentoServiceImpl gestioneSchedaAllenamentoService;

    private SchedaAllenamento schedaAllenamento;
    private List<SchedaAllenamento> schedaAllenamentoPreparatore;
    private CategoriaEsercizio categoriaEsercizio;
    private Esercizio esercizio;
    private List<IstanzaEsercizioDTO> istanzeEsercizioDto;
    private IstanzaEsercizioDTO istanzaEsercizioDTO;
    private List<IstanzaEsercizio> istanzeEsercizio;
    private IstanzaEsercizio istanzaEsercizio;
    private Ruolo ruoloPreparatore;
    private Utente preparatore;
    private String nome;


    @BeforeEach
    public void setUp() throws IOException {



    }

    @Test
    public void creaSchedaAllenamento(){

        categoriaEsercizio = new CategoriaEsercizio(1L,"Pettorali");
        istanzeEsercizioDto = new ArrayList<>();
        istanzeEsercizio = new ArrayList<>();

        schedaAllenamento = new SchedaAllenamento();
        schedaAllenamento.setId(1L);

        istanzaEsercizio = new IstanzaEsercizio();

        esercizio = new Esercizio(1L,"Chest press",
                "EserciziPalestra/Air-Twisting-Crunch_waist.gif",categoriaEsercizio);

        istanzaEsercizioDTO = new IstanzaEsercizioDTO(GIORNO_SETTIMANA.LUNEDI,2,3,1,
                "Esercizio gambe",1L);
        istanzaEsercizio.setSchedaAllenamento(schedaAllenamento);
        istanzaEsercizio.setEsercizio(esercizio);
        istanzaEsercizio.setGiornoDellaSettimana(istanzaEsercizioDTO.getGiornoDellaSettimana());
        istanzaEsercizio.setDescrizione(istanzaEsercizioDTO.getDescrizione());
        istanzaEsercizio.setRecupero(istanzaEsercizioDTO.getRecupero());
        istanzaEsercizio.setSerie(istanzaEsercizioDTO.getSerie());
        istanzaEsercizio.setRipetizioni(istanzaEsercizioDTO.getRipetizioni());
        istanzeEsercizio.add(istanzaEsercizio);

        istanzeEsercizioDto.add(istanzaEsercizioDTO);

        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);

        nome = "Scheda allenamento femminile";
        schedaAllenamento.setNome(nome);
        schedaAllenamento.setPreparatore(preparatore);
        schedaAllenamento.setFrequenza(3);

        when(esercizioRepository.findById(istanzaEsercizioDTO.getIdEsercizio())).thenReturn(Optional.of(esercizio));
        when(schedaAllenamentoRepository.save(any())).thenReturn(schedaAllenamento);
        SchedaAllenamento schedaAllenamento1 =  gestioneSchedaAllenamentoService.creaSchedaAllenamento(
                istanzeEsercizioDto,schedaAllenamento.getNome(),preparatore.getId(),schedaAllenamento.getFrequenza());
        assertEquals(schedaAllenamento, schedaAllenamento1);
    }

    @Test
    public void modificaSchedaAllenamento() {
        categoriaEsercizio = new CategoriaEsercizio(1L,"Pettorali");
        istanzeEsercizioDto = new ArrayList<>();
        istanzeEsercizio = new ArrayList<>();

        schedaAllenamento = new SchedaAllenamento();
        schedaAllenamento.setId(1L);

        istanzaEsercizio = new IstanzaEsercizio();

        esercizio = new Esercizio(1L,"Chest press",
                "EserciziPalestra/Air-Twisting-Crunch_waist.gif",categoriaEsercizio);

        istanzaEsercizioDTO = new IstanzaEsercizioDTO(GIORNO_SETTIMANA.LUNEDI,2,3,1,
                "Esercizio gambe",1L);
        istanzaEsercizio.setSchedaAllenamento(schedaAllenamento);
        istanzaEsercizio.setEsercizio(esercizio);
        istanzaEsercizio.setGiornoDellaSettimana(istanzaEsercizioDTO.getGiornoDellaSettimana());
        istanzaEsercizio.setDescrizione(istanzaEsercizioDTO.getDescrizione());
        istanzaEsercizio.setRecupero(istanzaEsercizioDTO.getRecupero());
        istanzaEsercizio.setSerie(istanzaEsercizioDTO.getSerie());
        istanzaEsercizio.setRipetizioni(istanzaEsercizioDTO.getRipetizioni());
        istanzeEsercizio.add(istanzaEsercizio);

        istanzeEsercizioDto.add(istanzaEsercizioDTO);

        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);

        nome = "Scheda allenamento femminile";
        schedaAllenamento.setNome(nome);
        schedaAllenamento.setPreparatore(preparatore);
        schedaAllenamento.setFrequenza(3);

        when(schedaAllenamentoRepository.findById(schedaAllenamento.getId())).thenReturn(Optional.ofNullable(schedaAllenamento));
        when(esercizioRepository.findById(istanzaEsercizioDTO.getIdEsercizio())).thenReturn(Optional.of(esercizio));
        when(schedaAllenamentoRepository.save(any())).thenReturn(schedaAllenamento);
        SchedaAllenamento schedaAllenamento1 =  gestioneSchedaAllenamentoService.modificaSchedaAllenamento(
                istanzeEsercizioDto,schedaAllenamento.getNome(),preparatore.getId(),1L,schedaAllenamento.getFrequenza());
        assertEquals(schedaAllenamento, schedaAllenamento1);

    }

    @Test
    public void getSchedeAllenamentoByPreparaore() {
        categoriaEsercizio = new CategoriaEsercizio(1L,"Pettorali");
        istanzeEsercizioDto = new ArrayList<>();
        istanzeEsercizio = new ArrayList<>();

        schedaAllenamento = new SchedaAllenamento();
        schedaAllenamento.setId(1L);

        istanzaEsercizio = new IstanzaEsercizio();

        esercizio = new Esercizio(1L,"Chest press",
                "EserciziPalestra/Air-Twisting-Crunch_waist.gif",categoriaEsercizio);

        istanzaEsercizioDTO = new IstanzaEsercizioDTO(GIORNO_SETTIMANA.LUNEDI,2,3,1,
                "Esercizio gambe",1L);
        istanzaEsercizio.setSchedaAllenamento(schedaAllenamento);
        istanzaEsercizio.setEsercizio(esercizio);
        istanzaEsercizio.setGiornoDellaSettimana(istanzaEsercizioDTO.getGiornoDellaSettimana());
        istanzaEsercizio.setDescrizione(istanzaEsercizioDTO.getDescrizione());
        istanzaEsercizio.setRecupero(istanzaEsercizioDTO.getRecupero());
        istanzaEsercizio.setSerie(istanzaEsercizioDTO.getSerie());
        istanzaEsercizio.setRipetizioni(istanzaEsercizioDTO.getRipetizioni());
        istanzeEsercizio.add(istanzaEsercizio);

        istanzeEsercizioDto.add(istanzaEsercizioDTO);

        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);

        nome = "Scheda allenamento femminile";
        schedaAllenamento.setNome(nome);
        schedaAllenamento.setPreparatore(preparatore);
        schedaAllenamento.setFrequenza(3);

        schedaAllenamentoPreparatore = new ArrayList<>();
        schedaAllenamentoPreparatore.add(schedaAllenamento);

        when(schedaAllenamentoRepository.findAllByPreparatoreId(preparatore.getId())).thenReturn(schedaAllenamentoPreparatore);
        assertEquals(schedaAllenamentoPreparatore, gestioneSchedaAllenamentoService.
                getSchedeAllenamentoByPreparaore(preparatore.getId()));
    }

    @Test
    public void getSchedeAllenamentoById() {
        categoriaEsercizio = new CategoriaEsercizio(1L,"Pettorali");
        istanzeEsercizioDto = new ArrayList<>();
        istanzeEsercizio = new ArrayList<>();

        schedaAllenamento = new SchedaAllenamento();
        schedaAllenamento.setId(1L);

        istanzaEsercizio = new IstanzaEsercizio();

        esercizio = new Esercizio(1L,"Chest press",
                "EserciziPalestra/Air-Twisting-Crunch_waist.gif",categoriaEsercizio);

        istanzaEsercizioDTO = new IstanzaEsercizioDTO(GIORNO_SETTIMANA.LUNEDI,2,3,1,
                "Esercizio gambe",1L);
        istanzaEsercizio.setSchedaAllenamento(schedaAllenamento);
        istanzaEsercizio.setEsercizio(esercizio);
        istanzaEsercizio.setGiornoDellaSettimana(istanzaEsercizioDTO.getGiornoDellaSettimana());
        istanzaEsercizio.setDescrizione(istanzaEsercizioDTO.getDescrizione());
        istanzaEsercizio.setRecupero(istanzaEsercizioDTO.getRecupero());
        istanzaEsercizio.setSerie(istanzaEsercizioDTO.getSerie());
        istanzaEsercizio.setRipetizioni(istanzaEsercizioDTO.getRipetizioni());
        istanzeEsercizio.add(istanzaEsercizio);

        istanzeEsercizioDto.add(istanzaEsercizioDTO);

        ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);

        preparatore =
                new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
                        "Trappo#98", true, null, null, null, null,
                        null, null, null, ruoloPreparatore, null, null, null,
                        null, null);

        nome = "Scheda allenamento femminile";
        schedaAllenamento.setNome(nome);
        schedaAllenamento.setPreparatore(preparatore);
        schedaAllenamento.setFrequenza(3);

        when(schedaAllenamentoRepository.findById(schedaAllenamento.getId())).thenReturn(Optional.ofNullable(schedaAllenamento));
        assertEquals(schedaAllenamento, gestioneSchedaAllenamentoService.
                getSchedeAllenamentoById(schedaAllenamento.getId()));

    }

}
