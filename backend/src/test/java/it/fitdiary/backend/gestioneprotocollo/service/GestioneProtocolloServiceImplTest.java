package it.fitdiary.backend.gestioneprotocollo.service;
/*

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.Alimento;
import it.fitdiary.backend.entity.Esercizio;
import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.SchedaAllenamento;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneprotocollo.adapter.SchedaAlimentareAdapterImpl;
import it.fitdiary.backend.gestioneprotocollo.adapter.SchedaAllenamentoAdapterImpl;
import it.fitdiary.backend.gestioneprotocollo.repository.AlimentoRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.EsercizioRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.ProtocolloRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.SchedaAlimentareRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.SchedaAllenamentoRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
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
    private Alimento alimento;
    private Esercizio esercizio;
    private SchedaAllenamento schedaAllenamento;
    private SchedaAlimentare schedaAlimentare;
    private File fileSchedaAllenamento;
    private File fileSchedaAllenamentoError;
    private File fileSchedaAlimentare;
    private File fileSchedaAlimentareError;
    private File fileNotCsv;
    @Mock
    private AlimentoRepository alimentoRepository;
    @Mock
    private EsercizioRepository esercizioRepository;
    @Mock
    private SchedaAllenamentoRepository schedaAllenamentoRepository;
    @Mock
    private SchedaAlimentareRepository schedaAlimentareRepository;



    @BeforeEach
    public void setUp() throws IOException {
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
        alimento = new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null);
        esercizio = new Esercizio(null, "pushup", "3", "10", "1", "1", "petto",
                null);
        schedaAlimentare = new SchedaAlimentare(1L, 2000f, null, protocollo);
        schedaAllenamento = new SchedaAllenamento(1L, "3", null, protocollo);
        fileSchedaAllenamento = Files.writeString(Path.of("schedaAllenamento.csv"),
                "Nome;Serie;Ripetizioni;Recupero;Numero Allenamento;Categoria\n" +
                        "pushup;3;10;1;1;petto\n" +
                        "pushup;3;10;1;1;petto\n" +
                        "pushup;3;10;1;1;petto\n" +
                        "pushup;3;10;1;1;petto\n" +
                        "pushup;3;10;1;2;petto\n" +
                        "pushup;3;10;1;2;petto\n" +
                        "pushup;3;10;1;2;petto\n" +
                        "pushup;3;10;1;2;petto\n" +
                        "pushup;3;10;1;3;petto\n" +
                        "pushup;3;10;1;3;petto\n" +
                        "pushup;3;10;1;3;petto").toFile();
        fileSchedaAlimentare = Files.writeString(Path.of("schedaAlimentare.csv"),
                "Nome;Pasto;Giorno;Kcal;Grammi\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n" +
                        "Pasta;pranzo;1;200;100\n").toFile();
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
       Protocollo protocolloPre = new Protocollo(null, LocalDate.parse("2022-01-05"),
                null, null, cliente,
                preparatore, null, null);
        when(protocolloRepository.save(protocolloPre)).thenReturn(protocollo);

        protocollo.setSchedaAlimentare(schedaAlimentare);
        when(mock(
                GestioneProtocolloServiceImpl.class).inserisciSchedaAlimentare(
                protocollo,
                fileSchedaAlimentare)).thenReturn(protocollo);

        when(mock(
                GestioneProtocolloServiceImpl.class).inserisciSchedaAllenamento(
                protocollo,
                fileSchedaAllenamento)).thenReturn(protocollo);

        protocollo.setSchedaAllenamento(schedaAllenamento);
        assertEquals(protocollo,
                gestioneProtocolloServiceImpl.creazioneProtocollo(
                        protocolloPre,
                        fileSchedaAlimentare, fileSchedaAllenamento));
    }

    @Test
    void creazioneProtocolloSuccesstWithoutSchedaAllenamento() throws IOException {
        Protocollo protocolloPre = new Protocollo(null, LocalDate.parse("2022-01-05"),
                null, null, cliente,
                preparatore, null, null);
        when(protocolloRepository.save(protocolloPre)).thenReturn(protocollo);

        protocollo.setSchedaAlimentare(null);
        when(mock(
                GestioneProtocolloServiceImpl.class).inserisciSchedaAllenamento(
                protocollo,
                fileSchedaAllenamento)).thenReturn(protocollo);

        protocollo.setSchedaAllenamento(schedaAllenamento);
        assertEquals(protocollo,
                gestioneProtocolloServiceImpl.creazioneProtocollo(
                        protocolloPre,
                        fileSchedaAlimentare, null));
    }

    @Test
    void creazioneProtocolloSuccesstWithoutSchedaAlimentare() throws IOException {
        Protocollo protocolloPre = new Protocollo(null, LocalDate.parse("2022-01-05"),
                null, null, cliente,
                preparatore, null, null);
        when(protocolloRepository.save(protocolloPre)).thenReturn(protocollo);

        protocollo.setSchedaAllenamento(null);
        when(mock(
                GestioneProtocolloServiceImpl.class).inserisciSchedaAlimentare(
                protocollo,
                fileSchedaAlimentare)).thenReturn(protocollo);

        protocollo.setSchedaAlimentare(schedaAlimentare);
        assertEquals(protocollo,
                gestioneProtocolloServiceImpl.creazioneProtocollo(
                        protocolloPre,
                        null, fileSchedaAllenamento));
    }

    @Test
    void getByIdProtocolloSuccess() {
        when(protocolloRepository.existsById(1L)).thenReturn(true);
        when(protocolloRepository.getById(1L)).thenReturn(protocollo);
        assertEquals(protocollo,
                gestioneProtocolloServiceImpl.getByIdProtocollo(1L));
    }

    @Test
    void getByIdProtocolloIdNonValido() {
        assertThrows(IllegalArgumentException.class,
                () -> gestioneProtocolloServiceImpl.getByIdProtocollo(null));
    }

    @Test
    void getByIdProtocolloProtocolloNonEsistente() {
        when(protocolloRepository.existsById(1L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class,
                () -> gestioneProtocolloServiceImpl.getByIdProtocollo(1L));
    }

    @Test
    void inserisciSchedaAlimentareSuccess() throws IOException {
        protocollo.setSchedaAlimentare(schedaAlimentare);
        List<Alimento> alimenti = new ArrayList<Alimento>();
        alimenti.add(alimento);
        when(mock(SchedaAlimentareAdapterImpl.class).parse(fileSchedaAlimentare)).thenReturn(alimenti);
        SchedaAlimentare schedaAlimentarePre= new SchedaAlimentare(null, 200f
                , null, protocollo);
        when(schedaAlimentareRepository.save(schedaAlimentarePre)).thenReturn(schedaAlimentare);
        schedaAlimentare.setKcalAssunte(200f);
        schedaAlimentare.setListaAlimenti(alimenti);
        protocollo.setSchedaAlimentare(schedaAlimentare);
        assertEquals(protocollo, gestioneProtocolloServiceImpl.inserisciSchedaAlimentare(protocollo, fileSchedaAlimentare));
    }

    @Test
    void inserisciSchedaAlimentareErrorWithoutSchedaAndThrowsIllegalArgument() throws IOException {
        List<Alimento> alimenti = new ArrayList<Alimento>();
        alimenti.add(alimento);
        when(mock(SchedaAlimentareAdapterImpl.class).parse(fileSchedaAlimentare)).thenReturn(alimenti);
        SchedaAlimentare schedaAlimentarePre= new SchedaAlimentare(null, 200f
                , null, protocollo);
        when(schedaAlimentareRepository.save(schedaAlimentarePre)).thenReturn(schedaAlimentare);
        schedaAlimentare.setKcalAssunte(200f);
        schedaAlimentare.setListaAlimenti(alimenti);
        protocollo.setSchedaAlimentare(schedaAlimentare);
        assertThrows(IllegalArgumentException.class,() -> gestioneProtocolloServiceImpl.inserisciSchedaAlimentare(protocollo, fileNotCsv));
    }

    @Test
    void inserisciSchedaAlimentareSuccessWithSchedaNull() throws IOException {
        assertEquals(protocollo, gestioneProtocolloServiceImpl.inserisciSchedaAlimentare(protocollo, null));
    }

    @Test
    void inserisciSchedaAllenamentoSuccess() throws IOException {
        protocollo.setSchedaAllenamento(schedaAllenamento);
        List<Esercizio> esercizi = new ArrayList<Esercizio>();
        esercizi.add(esercizio);
        when(mock(SchedaAllenamentoAdapterImpl.class).parse(fileSchedaAllenamento)).thenReturn(esercizi);
        SchedaAllenamento schedaAllenamentoPre= new SchedaAllenamento(null, "1", null, protocollo);
        when(schedaAllenamentoRepository.save(schedaAllenamentoPre)).thenReturn(schedaAllenamento);
        schedaAllenamento.setFrequenza("1");
        schedaAllenamento.setListaEsercizi(esercizi);
        protocollo.setSchedaAllenamento(schedaAllenamento);
        assertEquals(protocollo, gestioneProtocolloServiceImpl.inserisciSchedaAllenamento(protocollo, fileSchedaAllenamento));
    }

    @Test
    void inserisciSchedaAllenamentoErrorWithoutSchedaAndThrowsIllegalArgument() throws IOException {
        List<Esercizio> esercizi = new ArrayList<Esercizio>();
        esercizi.add(esercizio);
        when(mock(SchedaAllenamentoAdapterImpl.class).parse(fileSchedaAllenamento)).thenReturn(esercizi);
        SchedaAllenamento schedaAllenamentoPre= new SchedaAllenamento(null, "1", null, protocollo);
        when(schedaAllenamentoRepository.save(schedaAllenamentoPre)).thenReturn(schedaAllenamento);
        schedaAllenamento.setFrequenza("1");
        schedaAllenamento.setListaEsercizi(esercizi);
        protocollo.setSchedaAllenamento(schedaAllenamento);
        assertThrows(IllegalArgumentException.class,() -> gestioneProtocolloServiceImpl.inserisciSchedaAllenamento(protocollo, fileNotCsv));
    }

    @Test
    void inserisciSchedaAllenamentoSuccessWithSchedaNull() throws IOException {
        assertEquals(protocollo, gestioneProtocolloServiceImpl.inserisciSchedaAllenamento(protocollo, null));
    }

}

*/
