package it.fitdiary.backend.gestioneprotocollo.controller;

import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Customer;
import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.Alimento;
import it.fitdiary.backend.entity.Esercizio;
import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.SchedaAllenamento;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneprotocollo.service.GestioneProtocolloService;
import it.fitdiary.backend.gestioneprotocollo.service.GestioneProtocolloServiceImpl;
import it.fitdiary.backend.gestioneutenza.controller.GestioneUtenzaController;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaServiceImpl;
import it.fitdiary.backend.utility.FileUtility;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ContextConfiguration(classes = {GestioneProtocolloController.class})
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
class GestioneProtocolloControllerTest {
    @Autowired
    private GestioneProtocolloController gestioneProtocolloController;
    @MockBean
    private GestioneUtenzaServiceImpl gestioneUtenzaServiceImpl;
    @MockBean
    private GestioneProtocolloServiceImpl gestioneProtocolloServiceImpl;
    private Ruolo ruoloCliente;
    private Ruolo ruoloPreparatore;
    private Utente cliente;
    private Utente clienteAggiornato;
    private Utente preparatore;
    private Utente updatedPreparatore;
    private Utente cliente1;
    private Utente cliente2;
    private Utente cliente3;
    private Utente preparatore1;
    private Protocollo protocollo;
    private Protocollo protocolloConSchedaAlimentare;
    private Protocollo protocolloConSchedaAllenamento;
    private Protocollo protocolloPieno;
    private Alimento alimento;
    private Esercizio esercizio;
    private SchedaAllenamento schedaAllenamento;
    private SchedaAlimentare schedaAlimentare;
    private File fileSchedaAllenamento;
    private File fileSchedaAllenamentoError;
    private File fileSchedaAlimentare;
    private File fileSchedaAlimentareError;
    private File fileNotCsv;


    @BeforeEach
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
        protocollo =
                new Protocollo(1L, LocalDate.now(), null, null, cliente,
                        preparatore, LocalDateTime.now(), null);
        protocolloConSchedaAlimentare =
                new Protocollo(1L, LocalDate.now(), schedaAlimentare, null, cliente,
                        preparatore, LocalDateTime.now(), null);
        protocolloConSchedaAllenamento =
                new Protocollo(1L, LocalDate.now(), null, schedaAllenamento, cliente,
                        preparatore, LocalDateTime.now(), null);
        protocolloPieno = new Protocollo(1L, LocalDate.now(), schedaAlimentare, schedaAllenamento, cliente,
                preparatore, LocalDateTime.now(), null);
         cliente1 = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);


         preparatore1 =
                new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                        , "Davide123*", true, LocalDate.parse("2000-03" +
                        "-03"), "M", null, null, null,
                        null, null, ruoloPreparatore, null, null, null, null, null);
        cliente2 = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", preparatore,
                ruoloCliente, null, null, null, null, null);
        cliente3 = new Utente(2L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", preparatore,
                ruoloCliente, null, null, null, null, null);
        alimento = new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null);
        esercizio = new Esercizio(null, "pushup", "3", "10", "1", "1", "petto",
                null);
        schedaAlimentare = new SchedaAlimentare(1L, 2000f, null, protocollo);
        schedaAllenamento = new SchedaAllenamento(1L, "3", null, protocollo);
        fileSchedaAllenamento = new File(
                getClass().getClassLoader().getResource("schedaAllenamento.csv")
                        .getFile());
        fileSchedaAllenamentoError = new File(
                getClass().getClassLoader().getResource("schedaAllenamentoError.csv")
                        .getFile());
        fileSchedaAlimentare = new File(
                getClass().getClassLoader().getResource("schedaAlimentare.csv")
                        .getFile());
        fileSchedaAlimentareError = new File(
                getClass().getClassLoader().getResource("schedaAlimentareError.csv")
                        .getFile());
        fileNotCsv = new File(
                getClass().getClassLoader().getResource("notCsvFile.txt")
                        .getFile());
    }

    @Test
    void visualizzaStoricoProtocolliCliente() throws Exception {
        Principal principal = () -> "1";
        when(gestioneProtocolloServiceImpl.visualizzaStoricoProtocolliCliente(
                cliente2)).thenReturn(new ArrayList<Protocollo>());
        when(gestioneUtenzaServiceImpl.getById(1L)).thenReturn(preparatore);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore,
                cliente2.getId())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/protocolli").param("clienteId",
                        String.valueOf(cliente2.getId())).principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(
                        this.gestioneProtocolloController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andDo(print()).andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void visualizzaStoricoProtocolliSuccess() throws Exception {
        Principal principal = () -> "1";
        when(gestioneUtenzaServiceImpl.getById(1L)).thenReturn(cliente);
        when(gestioneProtocolloServiceImpl.visualizzaStoricoProtocolliCliente(
                cliente)).thenReturn(new ArrayList<Protocollo>());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/protocolli").principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(
                        this.gestioneProtocolloController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
    }

   @Test
    void visualizzaStoricoProtocolliBadRequest() throws Exception {
        Principal principal = () -> "1";
        when(gestioneUtenzaServiceImpl.getById(1L)).thenThrow(IllegalArgumentException.class);
        when(gestioneProtocolloServiceImpl.visualizzaStoricoProtocolliCliente(
                cliente)).thenReturn(new ArrayList<Protocollo>());
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/protocolli").principal(principal);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(
                        this.gestioneProtocolloController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andDo(print()).andExpect(
                MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void visualizzaProtocolloFromClienteTest_Success() throws Exception {

        Ruolo ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);

        Utente cliente = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);

        Utente preparatore =
                new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                        , "Davide123*", true, LocalDate.parse("2000-03" +
                        "-03"), "M", null, null, null,
                        null, null, ruoloPrep, null, null, null, null, null);

        Protocollo protocollo =
                new Protocollo(1L, LocalDate.now(), null, null, cliente,
                        preparatore, LocalDateTime.now(), null);
        Principal principal = () -> "1";
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(
                protocollo.getId())).thenReturn(protocollo);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get("/api/v1/protocolli/1")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void visualizzaProtocolloTest_Success()
            throws Exception {

        Ruolo ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);

        Utente cliente = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);

        Utente preparatore =
                new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                        , "Davide123*", true, LocalDate.parse("2000-03" +
                        "-03"), "M", null, null, null,
                        null, null, ruoloPrep, null, null, null, null, null);

        Protocollo protocollo =
                new Protocollo(1L, LocalDate.now(), null, null, cliente,
                        preparatore, LocalDateTime.now(), null);
        Principal principal = () -> "1";
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(
                protocollo.getId())).thenReturn(protocollo);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore,cliente.getId())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/protocolli/1")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    public void visualizzaProtocolloTest_BadRequest() throws Exception {

        Ruolo ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);

        Utente cliente = new Utente(1L, "Rebecca", "Di Matteo",
                "beccadimatteoo@gmail.com", "Becca123*", true,
                LocalDate.parse("2000-10-30"), null, "3894685921",
                "Francesco rinaldo", "94061", "Agropoli", null,
                ruoloCliente, null, null, null, null, null);
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null, null);

        Utente preparatore =
                new Utente(1L, "Davide", "La Gamba", "davide@gmail.com"
                        , "Davide123*", true, LocalDate.parse("2000-03" +
                        "-03"), "M", null, null, null,
                        null, null, ruoloPrep, null, null, null, null, null);

        Protocollo protocollo =
                new Protocollo(1L, LocalDate.now(), null, null, cliente,
                        preparatore, LocalDateTime.now(), null);
        Principal principal = () -> "8";
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(
                protocollo.getId())).thenReturn(protocollo);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore,cliente.getId())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(
                                "/api/v1/protocolli/1")
                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void creazioneProtocolloSuccess()
            throws Exception {
        String dataScadenza= "2023-12-12";
        MockMultipartFile multipartSchedaAlimentare= new MockMultipartFile("schedaAlimentare", fileSchedaAlimentare.getAbsolutePath(), null, new FileInputStream(fileSchedaAlimentare));
        MockMultipartFile multipartSchedaAllenamento= new MockMultipartFile("schedaAllenamento", fileSchedaAllenamento.getAbsolutePath(), null, new FileInputStream(fileSchedaAllenamento));
        Principal principal = () -> "1";
        Long idCliente = 2L;
        when(gestioneUtenzaServiceImpl.getById(1L)).thenReturn(preparatore);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore, idCliente)).thenReturn(true);
        when(gestioneUtenzaServiceImpl.getById(idCliente)).thenReturn(cliente3);

        MockedStatic<FileUtility> fileUtility = Mockito.mockStatic(FileUtility.class);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAlimentare)).thenReturn(fileSchedaAlimentare);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAllenamento)).thenReturn(fileSchedaAllenamento);
        Protocollo protocolloPre = new Protocollo();
        protocolloPre.setDataScadenza(LocalDate.parse(dataScadenza));
        protocolloPre.setCliente(cliente3);
        protocolloPre.setPreparatore(preparatore);
       when(gestioneProtocolloServiceImpl.creazioneProtocollo(protocolloPre, fileSchedaAlimentare, fileSchedaAllenamento)).thenReturn(protocollo);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.multipart(
                                "/api/v1/protocolli").file(multipartSchedaAlimentare).file(multipartSchedaAllenamento)
                        .param("dataScadenza", dataScadenza).param("idCliente", idCliente.toString())

                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
        fileUtility.close();
    }

    @Test
    public void creazioneProtocolloErrorUnauthorized()
            throws Exception {
        String dataScadenza= "2023-12-12";
        MockMultipartFile multipartSchedaAlimentare= new MockMultipartFile("schedaAlimentare", fileSchedaAlimentare.getAbsolutePath(), null, new FileInputStream(fileSchedaAlimentare));
        MockMultipartFile multipartSchedaAllenamento= new MockMultipartFile("schedaAllenamento", fileSchedaAllenamento.getAbsolutePath(), null, new FileInputStream(fileSchedaAllenamento));
        Principal principal = () -> "1";
        Long idCliente = 2L;
        when(gestioneUtenzaServiceImpl.getById(1L)).thenReturn(preparatore);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore, idCliente)).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.multipart(
                                "/api/v1/protocolli").file(multipartSchedaAlimentare).file(multipartSchedaAllenamento)
                        .param("dataScadenza", dataScadenza).param("idCliente", idCliente.toString())

                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isUnauthorized());

    }

    @Test
    public void creazioneProtocolloErrorSchedeVuote()
            throws Exception {
        String dataScadenza= "2023-12-12";
        MockMultipartFile multipartSchedaAlimentare= new MockMultipartFile("schedaAlimentare", new byte[0]);
        MockMultipartFile multipartSchedaAllenamento= new MockMultipartFile("schedaAllenamento", new byte[0]);
        Principal principal = () -> "1";
        Long idCliente = 2L;
        when(gestioneUtenzaServiceImpl.getById(1L)).thenReturn(preparatore);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore, idCliente)).thenReturn(true);
        when(gestioneUtenzaServiceImpl.getById(idCliente)).thenReturn(cliente3);

        MockedStatic<FileUtility> fileUtility = Mockito.mockStatic(FileUtility.class);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAlimentare)).thenReturn(fileSchedaAlimentare);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAllenamento)).thenReturn(fileSchedaAllenamento);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.multipart(
                                "/api/v1/protocolli").file(multipartSchedaAlimentare).file(multipartSchedaAllenamento)
                        .param("dataScadenza", dataScadenza).param("idCliente", idCliente.toString())

                        .principal(principal);
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());
        fileUtility.close();
    }

   @Test
    public void modificaProtocolloSuccess()
            throws Exception {
        MockMultipartFile multipartSchedaAlimentare= new MockMultipartFile("schedaAlimentare", fileSchedaAlimentare.getAbsolutePath(), null, new FileInputStream(fileSchedaAlimentare));
        MockMultipartFile multipartSchedaAllenamento= new MockMultipartFile("schedaAllenamento", fileSchedaAllenamento.getAbsolutePath(), null, new FileInputStream(fileSchedaAllenamento));
        Principal principal = () -> "1";
        Long idCliente = 1L;
        Long idProtocollo = 1L;
        when(gestioneUtenzaServiceImpl.getById(1L)).thenReturn(preparatore);
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(idProtocollo)).thenReturn(protocollo);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore, idCliente)).thenReturn(true);

        MockedStatic<FileUtility> fileUtility = Mockito.mockStatic(FileUtility.class);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAlimentare)).thenReturn(fileSchedaAlimentare);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAllenamento)).thenReturn(fileSchedaAllenamento);

       when(gestioneProtocolloServiceImpl.inserisciSchedaAlimentare(protocollo, fileSchedaAlimentare)).thenReturn(protocolloConSchedaAlimentare);
       when(gestioneProtocolloServiceImpl.inserisciSchedaAllenamento(protocolloConSchedaAlimentare, fileSchedaAllenamento)).thenReturn(protocolloPieno);
        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.multipart(
                                "/api/v1/protocolli/1").file(multipartSchedaAlimentare).file(multipartSchedaAllenamento)
                      .param("idProtocollo", idProtocollo.toString())
                        .principal(principal);
        requestBuilder.with(request -> {request.setMethod("PUT"); return request;});
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
        fileUtility.close();
    }

    @Test
    public void modificaProtocolloErrorUnauthorized()
            throws Exception {
        MockMultipartFile multipartSchedaAlimentare= new MockMultipartFile("schedaAlimentare", fileSchedaAlimentare.getAbsolutePath(), null, new FileInputStream(fileSchedaAlimentare));
        MockMultipartFile multipartSchedaAllenamento= new MockMultipartFile("schedaAllenamento", fileSchedaAllenamento.getAbsolutePath(), null, new FileInputStream(fileSchedaAllenamento));
        Principal principal = () -> "1";
        Long idCliente = 1L;
        Long idProtocollo = 1L;
        when(gestioneUtenzaServiceImpl.getById(1L)).thenReturn(preparatore);
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(idProtocollo)).thenReturn(protocollo);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore, idCliente)).thenReturn(false);

        MockedStatic<FileUtility> fileUtility = Mockito.mockStatic(FileUtility.class);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAlimentare)).thenReturn(fileSchedaAlimentare);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAllenamento)).thenReturn(fileSchedaAllenamento);

        when(gestioneProtocolloServiceImpl.inserisciSchedaAlimentare(protocollo, fileSchedaAlimentare)).thenReturn(protocolloConSchedaAlimentare);
        when(gestioneProtocolloServiceImpl.inserisciSchedaAllenamento(protocolloConSchedaAlimentare, fileSchedaAllenamento)).thenReturn(protocolloPieno);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.multipart(
                                "/api/v1/protocolli/1").file(multipartSchedaAlimentare).file(multipartSchedaAllenamento)
                        .param("idProtocollo", idProtocollo.toString())
                        .principal(principal);
        requestBuilder.with(request -> {request.setMethod("PUT"); return request;});
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isUnauthorized());
        fileUtility.close();
    }

    @Test
    public void modificaProtocolloSuccessSchedaAllenamentoEmpty()
            throws Exception {
        MockMultipartFile multipartSchedaAlimentare= new MockMultipartFile("schedaAlimentare", fileSchedaAlimentare.getAbsolutePath(), null, new FileInputStream(fileSchedaAlimentare));
        MockMultipartFile multipartSchedaAllenamento= new MockMultipartFile("schedaAllenamento",new byte[0]);
        Principal principal = () -> "1";
        Long idCliente = 1L;
        Long idProtocollo = 1L;
        when(gestioneUtenzaServiceImpl.getById(1L)).thenReturn(preparatore);
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(idProtocollo)).thenReturn(protocollo);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore, idCliente)).thenReturn(true);

        MockedStatic<FileUtility> fileUtility = Mockito.mockStatic(FileUtility.class);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAlimentare)).thenReturn(fileSchedaAlimentare);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAllenamento)).thenReturn(null);

        when(gestioneProtocolloServiceImpl.inserisciSchedaAlimentare(protocollo, fileSchedaAlimentare)).thenReturn(protocolloConSchedaAlimentare);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.multipart(
                                "/api/v1/protocolli/1").file(multipartSchedaAlimentare).file(multipartSchedaAllenamento)
                        .param("idProtocollo", idProtocollo.toString())
                        .principal(principal);
        requestBuilder.with(request -> {request.setMethod("PUT"); return request;});
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
        fileUtility.close();
    }

    @Test
    public void modificaProtocolloSuccessSchedaAlimentareEmpty()
            throws Exception {
        MockMultipartFile multipartSchedaAlimentare= new MockMultipartFile("schedaAlimentare", new byte[0]);
        MockMultipartFile multipartSchedaAllenamento= new MockMultipartFile("schedaAllenamento", fileSchedaAllenamento.getAbsolutePath(), null, new FileInputStream(fileSchedaAllenamento));
        Principal principal = () -> "1";
        Long idCliente = 1L;
        Long idProtocollo = 1L;
        when(gestioneUtenzaServiceImpl.getById(1L)).thenReturn(preparatore);
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(idProtocollo)).thenReturn(protocollo);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore, idCliente)).thenReturn(true);

        MockedStatic<FileUtility> fileUtility = Mockito.mockStatic(FileUtility.class);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAlimentare)).thenReturn(null);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAllenamento)).thenReturn(fileSchedaAllenamento);

        when(gestioneProtocolloServiceImpl.inserisciSchedaAllenamento(protocollo, fileSchedaAllenamento)).thenReturn(protocolloConSchedaAllenamento);

        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.multipart(
                                "/api/v1/protocolli/1").file(multipartSchedaAlimentare).file(multipartSchedaAllenamento)
                        .param("idProtocollo", idProtocollo.toString())
                        .principal(principal);
        requestBuilder.with(request -> {request.setMethod("PUT"); return request;});
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().is2xxSuccessful());
        fileUtility.close();
    }

    @Test
    public void modificaProtocolloErrorSchedeEmpty()
            throws Exception {
        MockMultipartFile multipartSchedaAlimentare= new MockMultipartFile("schedaAlimentare", new byte[0]);
        MockMultipartFile multipartSchedaAllenamento= new MockMultipartFile("schedaAllenamento", new byte[0]);
        Principal principal = () -> "1";
        Long idCliente = 1L;
        Long idProtocollo = 1L;
        when(gestioneUtenzaServiceImpl.getById(1L)).thenReturn(preparatore);
        when(gestioneProtocolloServiceImpl.getByIdProtocollo(idProtocollo)).thenReturn(protocollo);
        when(gestioneUtenzaServiceImpl.existsByPreparatoreAndId(preparatore, idCliente)).thenReturn(true);

        MockedStatic<FileUtility> fileUtility = Mockito.mockStatic(FileUtility.class);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAlimentare)).thenReturn(null);
        fileUtility.when(() -> FileUtility.getFile(multipartSchedaAllenamento)).thenReturn(null);


        MockHttpServletRequestBuilder requestBuilder =
                MockMvcRequestBuilders.multipart(
                                "/api/v1/protocolli/1").file(multipartSchedaAlimentare).file(multipartSchedaAllenamento)
                        .param("idProtocollo", idProtocollo.toString())
                        .principal(principal);
        requestBuilder.with(request -> {request.setMethod("PUT"); return request;});
        ResultActions actualPerformResult =
                MockMvcBuilders.standaloneSetup(gestioneProtocolloController)
                        .build()
                        .perform(requestBuilder);
        actualPerformResult.andExpect(
                MockMvcResultMatchers.status().isBadRequest());
        fileUtility.close();
    }
}