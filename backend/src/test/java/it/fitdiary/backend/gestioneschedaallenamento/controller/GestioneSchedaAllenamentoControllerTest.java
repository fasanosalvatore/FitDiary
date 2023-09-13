package it.fitdiary.backend.gestioneschedaallenamento.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.fitdiary.backend.entity.*;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import it.fitdiary.backend.gestioneschedaalimentare.controller.TestObjectMapperConfig;
import it.fitdiary.backend.gestioneschedaallenamento.controller.dto.CreaSchedaAllenamentoDTO;
import it.fitdiary.backend.gestioneschedaallenamento.controller.dto.IstanzaEsercizioDTO;
import it.fitdiary.backend.gestioneschedaallenamento.controller.dto.ModificaSchedaAllenamentoDTO;
import it.fitdiary.backend.gestioneschedaallenamento.service.GestioneSchedaAllenamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { GestioneSchedaAllenamentoController.class, TestObjectMapperConfig.class }) // Import the test configuration class
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class GestioneSchedaAllenamentoControllerTest {


    private CreaSchedaAllenamentoDTO creaSchedaAllenamentoDTO;
    private ModificaSchedaAllenamentoDTO modificaSchedaAllenamentoDTO;
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

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GestioneSchedaAllenamentoService service;

    @Autowired
    private GestioneSchedaAllenamentoController gestioneSchedaAllenamentoController;

    @BeforeEach
    public void setUp() throws IOException {
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

        creaSchedaAllenamentoDTO = new CreaSchedaAllenamentoDTO(schedaAllenamento.getNome(),
                istanzeEsercizioDto,schedaAllenamento.getFrequenza());
        modificaSchedaAllenamentoDTO = new ModificaSchedaAllenamentoDTO(schedaAllenamento.getNome(),
                istanzeEsercizioDto,schedaAllenamento.getFrequenza(),schedaAllenamento.getId());
    }

    @Test
    public void creaSchedaAllenamentoSuccess() throws Exception {
        when(service.creaSchedaAllenamento(any(List.class), any(String.class), any(Long.class),any(Integer.class))).
                thenReturn(schedaAllenamento);

        String requestBody = objectMapper.writeValueAsString(creaSchedaAllenamentoDTO);


        Principal principal = () -> "1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/schedaAllenamento/creaScheda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAllenamentoController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void creaSchedaAllenamentoBadRequestNullName() throws Exception {

        creaSchedaAllenamentoDTO.setName(null);
        when(service.creaSchedaAllenamento(any(List.class), any(String.class), any(Long.class),any(Integer.class))).
                thenReturn(schedaAllenamento);

        String requestBody = objectMapper.writeValueAsString(creaSchedaAllenamentoDTO);


        Principal principal = () -> "1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/schedaAllenamento/creaScheda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAllenamentoController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void creaSchedaAllenamentoBadRequestNullListaIstanzeEsercizi() throws Exception {

        creaSchedaAllenamentoDTO.setIstanzeEsercizi(null);
        when(service.creaSchedaAllenamento(any(List.class), any(String.class), any(Long.class),any(Integer.class))).
                thenReturn(schedaAllenamento);

        String requestBody = objectMapper.writeValueAsString(creaSchedaAllenamentoDTO);


        Principal principal = () -> "1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/schedaAllenamento/creaScheda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAllenamentoController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void modificaSchedaAllenamentoSuccess() throws Exception {
        when(service.modificaSchedaAllenamento(any(List.class), any(String.class), any(Long.class),any(Long.class),any(Integer.class))).
                thenReturn(schedaAllenamento);

        String requestBody = objectMapper.writeValueAsString(modificaSchedaAllenamentoDTO);


        Principal principal = () -> "1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/schedaAllenamento/modificaScheda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAllenamentoController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void modificaSchedaAllenamentoBadRequestNullName() throws Exception {

        modificaSchedaAllenamentoDTO.setName(null);
        when(service.modificaSchedaAllenamento(any(List.class), any(String.class), any(Long.class),any(Long.class),any(Integer.class))).
                thenReturn(schedaAllenamento);

        String requestBody = objectMapper.writeValueAsString(modificaSchedaAllenamentoDTO);


        Principal principal = () -> "1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/schedaAllenamento/modificaScheda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAllenamentoController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void modificaSchedaAllenamentoBadRequestNullListaIstanzeEsercizi() throws Exception {

        modificaSchedaAllenamentoDTO.setIstanzeEsercizi(null);
        when(service.modificaSchedaAllenamento(any(List.class), any(String.class), any(Long.class),any(Long.class),any(Integer.class))).
                thenReturn(schedaAllenamento);

        String requestBody = objectMapper.writeValueAsString(modificaSchedaAllenamentoDTO);


        Principal principal = () -> "1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/schedaAllenamento/modificaScheda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAllenamentoController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());

    }
    @Test
    public void getMySchedeAllenamentoSuccess() throws Exception {
        when(service.getSchedeAllenamentoByPreparaore(any())).thenReturn(schedaAllenamentoPreparatore);

        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/schedaAllenamento/getMySchedeAllenamento").
                        principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAllenamentoController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void getSchedaAllenamentoByIdSuccess() throws Exception {
        when(service.getSchedeAllenamentoById(any())).thenReturn(schedaAllenamento);

        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/schedaAllenamento/getSchedaAllenamentoById?idScheda="+
                                schedaAllenamento.getId()).
                        principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAllenamentoController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());

    }


}
