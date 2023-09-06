package it.fitdiary.backend.gestioneprotocollo.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.Esercizio;
import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.SchedaAllenamento;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneprotocollo.repository.GestioneProtocolloSchedaAlimentareRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.ProtocolloRepository;

import java.io.File;

import it.fitdiary.backend.gestioneschedaallenamento.repository.SchedaAllenamentoRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BackendApplicationTest.class)
@ActiveProfiles("test")
class GestioneProtocolloServiceImplTest {
  @Mock
  private ProtocolloRepository protocolloRepository;

  private Ruolo ruoloCliente;
  private Ruolo ruoloPreparatore;
  private Utente cliente;
  private Utente preparatore;
  private Protocollo protocollo;
  private Esercizio esercizio;
  private SchedaAllenamento schedaAllenamento;
  private SchedaAlimentare schedaAlimentare;
  private File fileSchedaAllenamento;
  private File fileNotCsv;

  @Mock
  private SchedaAllenamentoRepository schedaAllenamentoRepository;
  @Mock
  private GestioneProtocolloSchedaAlimentareRepository schedaAlimentareRepository;

  @InjectMocks
  private GestioneProtocolloServiceImpl gestioneProtocolloServiceImpl;

/*

  @BeforeEach
  public void setUp() throws IOException {
    ruoloCliente = new Ruolo(3L, "CLIENTE", null, null);
    ruoloPreparatore = new Ruolo(2L, "PREPARATORE", null, null);
    cliente = new Utente(1L, "Rebecca", "Di Matteo",
        "beccadimatteoo@gmail.com", "Becca123*", true, null, null, null,
        null, null, null, null, ruoloCliente, null, null, null, null,
        null);

    preparatore =
        new Utente(1L, "Daniele", "De Marco", "diodani5@gmail.com",
            "Trappo#98", true, null, null, null, null,
            null, null, null, ruoloPreparatore, null, null, null,
            null, null);

    protocollo = new Protocollo(1L, LocalDate.parse("2022-01-05"),
        new SchedaAlimentare(), new SchedaAllenamento(), cliente,
        preparatore, null, null);
    esercizio = new Esercizio(null, "pushup", "3", "10", "1", "1", "petto",
        null);
    schedaAlimentare =
        new SchedaAlimentare(1L, "schedaBuona", 2000f, new ArrayList<IstanzaAlimento>(),
            preparatore,
            LocalDateTime.now(), LocalDateTime.now());
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
  void creazioneProtocolloSchedeNulle() {

    assertThrows(IllegalArgumentException.class,
        () -> gestioneProtocolloServiceImpl.creazioneProtocollo(LocalDate.now(), preparatore,
            cliente, null, null));
  }

  @Test
  void creazioneProtocolloOnSuccess() throws IOException {
    LocalDateTime dataTest=LocalDateTime.now();
    LocalDate dieciGiorniDaOggi = LocalDate.now().plusDays(10);
    when(protocolloRepository.save(any(Protocollo.class))).thenAnswer(args -> {
      Protocollo p = args.getArgument(0);
      p.setId(1L);
      p.setDataCreazione(dataTest);
      p.setDataAggiornamento(dataTest);
      return p;
    });
    Optional<SchedaAlimentare> schedaAlimentareOptional = Optional.ofNullable(schedaAlimentare);
    when(schedaAlimentareRepository.findById(schedaAlimentare.getId())).thenReturn(
        schedaAlimentareOptional);

    when(schedaAllenamentoAdapter.parse(any())).thenReturn(schedaAllenamento.getListaEsercizi());
    when(schedaAllenamentoRepository.save(any())).thenReturn(schedaAllenamento);

    Protocollo expected = new Protocollo(1L,dieciGiorniDaOggi,schedaAlimentare,schedaAllenamento,cliente,preparatore,dataTest,dataTest);
    assertEquals(expected,
        gestioneProtocolloServiceImpl.creazioneProtocollo(dieciGiorniDaOggi
            , cliente, preparatore, schedaAlimentare.getId()
            , fileSchedaAllenamento));
  }


  @Test
  void creazioneProtocolloSuccessWithoutSchedaAllenamento() throws IOException {
    LocalDateTime dataTest=LocalDateTime.now();
    LocalDate dieciGiorniDaOggi = LocalDate.now().plusDays(10);
    when(protocolloRepository.save(any(Protocollo.class))).thenAnswer(args -> {
      Protocollo p = args.getArgument(0);
      p.setId(1L);
      p.setDataCreazione(dataTest);
      p.setDataAggiornamento(dataTest);
      return p;
    });
    Optional<SchedaAlimentare> schedaAlimentareOptional = Optional.ofNullable(schedaAlimentare);
    when(schedaAlimentareRepository.findById(schedaAlimentare.getId())).thenReturn(
        schedaAlimentareOptional);

    Protocollo expected = new Protocollo(1L,dieciGiorniDaOggi,schedaAlimentare,null,cliente,preparatore,dataTest,dataTest);
    assertEquals(expected,
        gestioneProtocolloServiceImpl.creazioneProtocollo(dieciGiorniDaOggi
            , cliente, preparatore, schedaAlimentare.getId()
            , null));
  }


  @Test
  void creazioneProtocolloSuccesstWithoutSchedaAlimentare() throws IOException {
    LocalDateTime dataTest=LocalDateTime.now();
    LocalDate dieciGiorniDaOggi = LocalDate.now().plusDays(10);
    when(protocolloRepository.save(any(Protocollo.class))).thenAnswer(args -> {
      Protocollo p = args.getArgument(0);
      p.setId(1L);
      p.setDataCreazione(dataTest);
      p.setDataAggiornamento(dataTest);
      return p;
    });


    when(schedaAllenamentoAdapter.parse(any())).thenReturn(schedaAllenamento.getListaEsercizi());
    when(schedaAllenamentoRepository.save(any())).thenReturn(schedaAllenamento);

    Protocollo expected = new Protocollo(1L,dieciGiorniDaOggi,null,schedaAllenamento,cliente,preparatore,dataTest,dataTest);
    assertEquals(expected,
        gestioneProtocolloServiceImpl.creazioneProtocollo(dieciGiorniDaOggi
            , cliente, preparatore, null
            , fileSchedaAllenamento));
  }

  @Test
  void getByIdProtocolloSuccess() {

    when(protocolloRepository.findById(1L)).thenReturn(Optional.ofNullable(protocollo));
    assertEquals(protocollo,
        gestioneProtocolloServiceImpl.getByIdProtocollo(1L));
  }


  @Test
  void getByIdProtocolloIdNonValido() {
    assertThrows(IllegalArgumentException.class,
        () -> gestioneProtocolloServiceImpl.getByIdProtocollo(null));
  }

  @Test
  void getByIdProtocolloNonEsistente() {
    when(protocolloRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(IllegalArgumentException.class,
        () -> gestioneProtocolloServiceImpl.getByIdProtocollo(1L));
  }






  @Test
  void inserisciSchedaAllenamentoSuccess() throws IOException {
    protocollo.setSchedaAllenamento(schedaAllenamento);
    List<Esercizio> esercizi = new ArrayList<Esercizio>();
    esercizi.add(esercizio);
    when(mock(SchedaAllenamentoAdapterImpl.class).parse(fileSchedaAllenamento)).thenReturn(
        esercizi);
    SchedaAllenamento schedaAllenamentoPre = new SchedaAllenamento(null, "1", null, protocollo);
    when(schedaAllenamentoRepository.save(schedaAllenamentoPre)).thenReturn(schedaAllenamento);
    schedaAllenamento.setFrequenza("1");
    schedaAllenamento.setListaEsercizi(esercizi);
    protocollo.setSchedaAllenamento(schedaAllenamento);
    assertEquals(protocollo, gestioneProtocolloServiceImpl.inserisciSchedaAllenamento(protocollo,
        fileSchedaAllenamento));
  }

  @Test
  void inserisciSchedaAllenamentoErrorWithoutSchedaAndThrowsIllegalArgument() throws IOException {
    List<Esercizio> esercizi = new ArrayList<Esercizio>();
    esercizi.add(esercizio);
    when(mock(SchedaAllenamentoAdapterImpl.class).parse(fileSchedaAllenamento)).thenReturn(
        esercizi);
    SchedaAllenamento schedaAllenamentoPre = new SchedaAllenamento(null, "1", null, protocollo);
    when(schedaAllenamentoRepository.save(schedaAllenamentoPre)).thenReturn(schedaAllenamento);
    schedaAllenamento.setFrequenza("1");
    schedaAllenamento.setListaEsercizi(esercizi);
    protocollo.setSchedaAllenamento(schedaAllenamento);
    assertThrows(IllegalArgumentException.class,
        () -> gestioneProtocolloServiceImpl.inserisciSchedaAllenamento(protocollo, fileNotCsv));
  }


  @Test
  void inserisciSchedaAllenamentoSuccessWithSchedaNull() throws IOException {
    assertEquals(protocollo,
        gestioneProtocolloServiceImpl.inserisciSchedaAllenamento(protocollo, null));
  }
*/

}


