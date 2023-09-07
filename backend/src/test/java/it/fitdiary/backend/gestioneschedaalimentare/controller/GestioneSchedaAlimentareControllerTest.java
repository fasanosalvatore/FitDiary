package it.fitdiary.backend.gestioneschedaalimentare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.fitdiary.backend.entity.*;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import it.fitdiary.backend.entity.enums.PASTO;
import it.fitdiary.backend.gestioneschedaalimentare.controller.dto.CreaSchedaAlimentareDTO;
import it.fitdiary.backend.gestioneschedaalimentare.controller.dto.IstanzaAlimentoDTO;
import it.fitdiary.backend.gestioneschedaalimentare.controller.dto.ModificaSchedaDTO;
import it.fitdiary.backend.gestioneschedaalimentare.service.GestioneSchedaAlimentareService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;




@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { GestioneSchedaAlimentareController.class, TestObjectMapperConfig.class }) // Import the test configuration class
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class GestioneSchedaAlimentareControllerTest {
    private Alimento alimento;
    private IstanzaAlimento istanzaAlimento;
    private IstanzaAlimentoDTO istanzaAlimentoDTO;
    private Ruolo ruoloPreparatore;
    private Utente preparatore;
    private SchedaAlimentare schedaAlimentare;
    private List<IstanzaAlimento> alimenti;
    private List<IstanzaAlimentoDTO> alimentiDTO;
    private CreaSchedaAlimentareDTO creaSchedaAlimentareDTO;
    private ModificaSchedaDTO modificaSchedaDTO;
    List<SchedaAlimentare> mySchedeAlimentariPreparatore;


    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GestioneSchedaAlimentareService service;

    @Autowired
    private GestioneSchedaAlimentareController gestioneSchedaAlimentareController;

    @Test
    public void creaSchedaAlimentareSuccess() throws Exception {
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

        creaSchedaAlimentareDTO = new CreaSchedaAlimentareDTO(schedaAlimentare.getNome(),alimentiDTO);

        when(service.creaSchedaAlimentare(any(List.class), any(String.class), any(Long.class))).thenReturn(schedaAlimentare);

        String requestBody = objectMapper.writeValueAsString(creaSchedaAlimentareDTO);


        Principal principal = () -> "1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/schedaalimentare/creaScheda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAlimentareController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void creaSchedaAlimentareBadRequestNullName() throws Exception {
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

        creaSchedaAlimentareDTO = new CreaSchedaAlimentareDTO(null,alimentiDTO);

        when(service.creaSchedaAlimentare(any(List.class), any(String.class), any(Long.class))).thenReturn(schedaAlimentare);

        String requestBody = objectMapper.writeValueAsString(creaSchedaAlimentareDTO);


        Principal principal = () -> "1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/schedaalimentare/creaScheda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAlimentareController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void creaSchedaAlimentareBadRequestNullListaAlimenti() throws Exception {
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

        creaSchedaAlimentareDTO = new CreaSchedaAlimentareDTO(schedaAlimentare.getNome(),null);

        when(service.creaSchedaAlimentare(any(List.class), any(String.class), any(Long.class))).thenReturn(schedaAlimentare);

        String requestBody = objectMapper.writeValueAsString(creaSchedaAlimentareDTO);


        Principal principal = () -> "1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/schedaalimentare/creaScheda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAlimentareController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void modificiaSchedaAlimentareSuccess() throws Exception {
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

        modificaSchedaDTO = new ModificaSchedaDTO(schedaAlimentare.getNome(),alimentiDTO,1L);


        when(service.modificaSchedaAlimentare(anyList(), anyString(), anyLong(), anyLong())).thenReturn(schedaAlimentare);

        String requestBody = objectMapper.writeValueAsString(modificaSchedaDTO);


        Principal principal = () -> "1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/schedaalimentare/modificaScheda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAlimentareController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void modificiaSchedaAlimentareBadRequestNullName() throws Exception {
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

        modificaSchedaDTO = new ModificaSchedaDTO(null,alimentiDTO,1L);


        when(service.modificaSchedaAlimentare(anyList(), anyString(), anyLong(), anyLong())).thenReturn(schedaAlimentare);

        String requestBody = objectMapper.writeValueAsString(modificaSchedaDTO);


        Principal principal = () -> "1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/schedaalimentare/modificaScheda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAlimentareController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void modificiaSchedaAlimentareBadRequestNullListaAlimenti() throws Exception {
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

        modificaSchedaDTO = new ModificaSchedaDTO(schedaAlimentare.getNome(),null,1L);


        when(service.modificaSchedaAlimentare(anyList(), anyString(), anyLong(), anyLong())).thenReturn(schedaAlimentare);

        String requestBody = objectMapper.writeValueAsString(modificaSchedaDTO);


        Principal principal = () -> "1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/schedaalimentare/modificaScheda")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAlimentareController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getMySchedeAlimentariSuccess() throws Exception {
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

        modificaSchedaDTO = new ModificaSchedaDTO(schedaAlimentare.getNome(),alimentiDTO,1L);

        mySchedeAlimentariPreparatore = new ArrayList<>();
        mySchedeAlimentariPreparatore.add(schedaAlimentare);

        when(service.getSchedeAlimentariByPreparaore(any())).thenReturn(mySchedeAlimentariPreparatore);


        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/schedaalimentare/getMySchedeAlimentari").
                        principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAlimentareController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void getSchedaAlimentareByIdSuccess() throws Exception {
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

        modificaSchedaDTO = new ModificaSchedaDTO(null,alimentiDTO,1L);


        when(service.getSchedeAlimentariById(schedaAlimentare.getId())).thenReturn(schedaAlimentare);



        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/schedaalimentare/getMySchedeAlimentari?idScheda="+schedaAlimentare.getId()).
                        principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneSchedaAlimentareController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

}
