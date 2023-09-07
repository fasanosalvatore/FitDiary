package it.fitdiary.backend.gestionecategoriaesercizio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.fitdiary.backend.entity.*;
import it.fitdiary.backend.gestionecategoriaesercizio.controller.GestioneCategoriaEsercizioController;
import it.fitdiary.backend.gestionecategoriaesercizio.service.GestioneCategoriaEsercizioService;
import it.fitdiary.backend.gestioneschedaalimentare.controller.TestObjectMapperConfig;
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
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { GestioneCategoriaEsercizioController.class, TestObjectMapperConfig.class })
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class GestioneCategoriaEsercizioControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GestioneCategoriaEsercizioService service;

    @Autowired
    private GestioneCategoriaEsercizioController gestioneCategoriaEsercizioController;
    private CategoriaEsercizio categoriaEsercizio;
    private List<CategoriaEsercizio> categorieEsercizi;

    @BeforeEach
    public void setUp() throws IOException {
        categoriaEsercizio = new CategoriaEsercizio();
        categoriaEsercizio.setId(1L);

        categorieEsercizi = new ArrayList<>();
        categorieEsercizi.add(categoriaEsercizio);
    }
    @Test
    public void visualizzaAlimentoSuccess() throws Exception{
        when(service.getById(categoriaEsercizio.getId())).thenReturn(Optional.of(categoriaEsercizio));


        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/categorieEsercizi/getCategoriaEsercizio?idCategoria="+
                                categoriaEsercizio.getId()).
                        principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneCategoriaEsercizioController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void visualizzaAlimentoBadRequest() throws Exception{
        when(service.getById(categoriaEsercizio.getId())).thenReturn(Optional.of(categoriaEsercizio));


        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/categorieEsercizi/getCategoriaEsercizio?idCategoria=999").
                        principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneCategoriaEsercizioController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void visualizzaListaAlimentiSuccess() throws Exception{
        when(service.getAllCategorie()).thenReturn(categorieEsercizi);


        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/categorieEsercizi/getAllCategorie").
                        principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneCategoriaEsercizioController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());

    }


}
