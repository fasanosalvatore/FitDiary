package it.fitdiary.backend.gestioneesercizio.controller;

import it.fitdiary.backend.entity.CategoriaEsercizio;
import it.fitdiary.backend.entity.Esercizio;
import it.fitdiary.backend.gestionecategoriaesercizio.controller.GestioneCategoriaEsercizioController;
import it.fitdiary.backend.gestionecategoriaesercizio.service.GestioneCategoriaEsercizioService;
import it.fitdiary.backend.gestioneesercizio.service.GestioneEsercizioService;
import it.fitdiary.backend.gestioneschedaalimentare.controller.TestObjectMapperConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { GestioneEsercizioController.class, TestObjectMapperConfig.class })
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class GestioneEsercizioControllerTest {

    @MockBean
    private GestioneEsercizioService service;

    @Autowired
    private GestioneEsercizioController gestioneEsercizioController;
    private Esercizio esercizio;

    private List<Esercizio> listaEsercizi;

    @BeforeEach
    public void setUp() throws IOException {
        esercizio = new Esercizio();
        esercizio.setId(1L);

        listaEsercizi = new ArrayList<>();
        listaEsercizi.add(esercizio);
    }

    @Test
    public void visualizzaEsercizioSuccess() throws Exception{
        when(service.getById(esercizio.getId())).thenReturn(Optional.of(esercizio));


        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/esercizi/getEsercizio?idEsercizio="+
                                esercizio.getId()).
                        principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneEsercizioController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void visualizzaAlimentoBadRequest() throws Exception{
        when(service.getById(esercizio.getId())).thenReturn(Optional.of(esercizio));


        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/esercizi/getEsercizio?idEsercizio=999").
                        principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneEsercizioController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void visualizzaListaAlimentiSuccess() throws Exception{
        when(service.getAll()).thenReturn(listaEsercizi);


        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/esercizi/getAllEsercizi").
                        principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneEsercizioController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());

    }

}
