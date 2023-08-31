package it.fitdiary.backend.gestionealimento.controller;

import it.fitdiary.backend.entity.Alimento;
import it.fitdiary.backend.gestionealimento.service.GestioneAlimentoService;
import it.fitdiary.backend.gestioneprotocollo.controller.GestioneProtocolloController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {GestioneAlimentoController.class})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class GestioneAlimentoControllerTest {
    private Alimento alimento;
    @MockBean
    private GestioneAlimentoService service;

    @Autowired
    private GestioneAlimentoController gestioneAlimentoController;

    @Test
    public void VisualizzaAlimentoSuccess() throws Exception {
        alimento = new Alimento();
        alimento.setId(1L);
        when(service.getById(alimento.getId())).thenReturn(alimento);

        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/alimenti/getAlimento").param("idAlimento",String.valueOf(alimento.getId())).principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneAlimentoController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void VisualizzaAlimentoBadRequest() throws Exception {
        alimento = new Alimento();
        alimento.setId(1L);
        when(service.getById(alimento.getId())).thenThrow(IllegalArgumentException.class);

        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/alimenti/getAlimento").param("idAlimento",String.valueOf(alimento.getId())).principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneAlimentoController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void VisualizzaListaAlimentiSuccess() throws Exception {
        List<Alimento> listaAlimenti = new ArrayList<>();
        Alimento alimento = new Alimento(1L,"Pollo",100f,21f,46f,
                3f,"Alimenti/1.jpg");
        listaAlimenti.add(alimento);
        when(service.getAllAlimenti()).thenReturn(listaAlimenti);

        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/alimenti/getAllAlimenti").param("idAlimento",String.valueOf(alimento.getId())).principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneAlimentoController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void VisualizzaListaAlimentiBadRequest() throws Exception {
        List<Alimento> listaAlimenti = new ArrayList<>();
        Alimento alimento = new Alimento(1L,"Pollo",100f,21f,46f,
                3f,"Alimenti/1.jpg");
        listaAlimenti.add(alimento);
        when(service.getAllAlimenti()).thenThrow(IllegalArgumentException.class);

        Principal principal = () -> "1";
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/alimenti/getAllAlimenti").param("idAlimento",String.valueOf(alimento.getId())).principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneAlimentoController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());
    }

}
