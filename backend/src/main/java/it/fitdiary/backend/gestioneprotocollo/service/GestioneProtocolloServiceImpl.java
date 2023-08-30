package it.fitdiary.backend.gestioneprotocollo.service;

import it.fitdiary.backend.entity.Esercizio;
import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.SchedaAllenamento;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneprotocollo.adapter.SchedaAllenamentoAdapter;
import it.fitdiary.backend.gestioneprotocollo.adapter.SchedaAllenamentoAdapterImpl;
import it.fitdiary.backend.gestioneprotocollo.repository.EsercizioRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.ProtocolloRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.GestioneProtocolloSchedaAlimentareRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.SchedaAllenamentoRepository;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GestioneProtocolloServiceImpl
    implements GestioneProtocolloService {
  /**
   * numero di protocolli in una pagina.
   */
  public static final int PAGE_SIZE = 50;
  /**
   * Repository del protocollo.
   */
  private final ProtocolloRepository protocolloRepository;
  /**
   * Repository della Scheda alimentare.
   */
  private final GestioneProtocolloSchedaAlimentareRepository schedaAlimentareRepository;

  /**
   * Adapter della scheda allenamento.
   */
  private final SchedaAllenamentoAdapter schedaAllenamentoAdapter =
      new SchedaAllenamentoAdapterImpl();
  /**
   * Repository della scheda allenamento.
   */
  private final SchedaAllenamentoRepository schedaAllenamentoRepository;
  /**
   * Repository dell'esercizio.
   */
  private final EsercizioRepository esercizioRepository;

  /**
   * @param  cliente il cliente del protocollo
   * @param preparatore il preparatore del protocollo
   * @param dataScadenza            la data di scadenza
   * @param idSchedaAlimentare  id scheda alimentare del nuovo protocollo
   * @param schedaAllenamentoFile file scheda allenamento del nuovo protocollo
   * @return Protocollo creato
   * @throws IOException
   * @throws IllegalArgumentException
   * **/
  @Override
  public Protocollo creazioneProtocollo(LocalDate dataScadenza, Utente cliente, Utente preparatore,
                                        final Long idSchedaAlimentare,
                                        final File schedaAllenamentoFile)
      throws IOException, IllegalArgumentException {
    if (schedaAllenamentoFile == null && idSchedaAlimentare == null) {
      throw new IllegalArgumentException("Nessun file presente");
    }
    Protocollo newProtocollo = new Protocollo();
    newProtocollo.setCliente(cliente);
    newProtocollo.setPreparatore(preparatore);
    newProtocollo.setDataScadenza(dataScadenza);
    if (idSchedaAlimentare != null) {
      newProtocollo.setSchedaAlimentare(getSchedaAlimentare(preparatore.getId(),idSchedaAlimentare));
    }
    if (schedaAllenamentoFile != null) {
      inserisciSchedaAllenamento(newProtocollo, schedaAllenamentoFile);
    }

    return  protocolloRepository.save(newProtocollo);
  }

  /**
   * @param preparatoreId         preparatore che possiede la scheda
   * @param schedaAlimentareId file della scheda alimentare
   * @return protocollo modificato
   */
  private SchedaAlimentare getSchedaAlimentare(final Long preparatoreId,
                                               final Long schedaAlimentareId)
      throws IllegalArgumentException {

    Optional<SchedaAlimentare> schedaAlimentare =
        schedaAlimentareRepository.findById(schedaAlimentareId);
    if (schedaAlimentare.isEmpty()) {
      throw new IllegalArgumentException(
          "la scheda alimentare con id " + schedaAlimentareId + " non esiste");
    }
    if (!Objects.equals(preparatoreId,
        schedaAlimentare.get().getPreparatore().getId())) {
      throw new IllegalArgumentException(
          "non hai i permessi per gestire la scheda alimentare di id " + schedaAlimentareId);
    }

    return schedaAlimentare.get();
  }

  /**
   * @param protocollo            protocollo per cui
   *                              inserire la scheda alimentare
   * @param schedaAllenamentoFile file della scheda allenamento
   * @return protocollo modificato
   * @throws IOException
   */
  public Protocollo inserisciSchedaAllenamento(final Protocollo protocollo,
                                               final File
                                                   schedaAllenamentoFile)
      throws IOException, IllegalArgumentException {
    if (schedaAllenamentoFile != null) {

      if (protocollo.getSchedaAllenamento() != null) {
        esercizioRepository.deleteAllBySchedaAllenamentoId(
            protocollo.getSchedaAllenamento().getId());
        schedaAllenamentoRepository.deleteAllByProtocolloId(
            protocollo.getId());
      }
      if (FilenameUtils.getExtension(schedaAllenamentoFile.getName())
          .equals("csv")) {
        List<Esercizio> esercizi =
            schedaAllenamentoAdapter.parse(schedaAllenamentoFile);
        Set<String> numeroAllenamenti = new HashSet<>();
        for (Esercizio esercizio : esercizi) {
          numeroAllenamenti.add(
              esercizio.getNumeroAllenamento().toUpperCase());
        }
        SchedaAllenamento schedaAllenamento = new SchedaAllenamento();
        schedaAllenamento.setFrequenza(
            String.valueOf(numeroAllenamenti.size()));
        schedaAllenamento.setProtocollo(protocollo);
        SchedaAllenamento newSchedaAllenamento =
            schedaAllenamentoRepository.save(schedaAllenamento);
        for (Esercizio esercizio : esercizi) {
          esercizio.setSchedaAllenamento(newSchedaAllenamento);
          esercizioRepository.save(esercizio);
        }
        protocollo.setSchedaAllenamento(schedaAllenamento);
        schedaAllenamento.setListaEsercizi(esercizi);
      } else {
        throw new IllegalArgumentException("Formato file non valido");
      }
    }
    return protocollo;
  }

  /**
   * @param idProtocollo id del protocollo
   * @return protocollo
   */
  public Protocollo getByIdProtocollo(final Long idProtocollo) {
    if (idProtocollo == null) {
      throw new IllegalArgumentException("Id non valido");
    }
    Protocollo protocollo = null;
    if (protocolloRepository.existsById(idProtocollo)) {
      protocollo = protocolloRepository.
          getById(idProtocollo);
    } else {
      throw new IllegalArgumentException("Il protocollo non esiste");
    }

    return protocollo;
  }

  /**
   * @param cliente cliente di cui s vuole visualizzare il protocollo
   *                visualizzare lo storico dei protocolli
   * @return lista dei protocolli del cliente
   */
  @Override
  public List<Protocollo> visualizzaStoricoProtocolliCliente(
      final Utente cliente) {
    return protocolloRepository.findAllByCliente(cliente);

  }

  /**
   * @param preparatore preparatore
   * @param page        numero pagine
   * @return lista protocolli creati dal preparatore
   */
  @Override
  public List<Protocollo> getAllProtocolliPreparatore(
      final Utente preparatore, final int page) {
    var pageProtocolli =
        protocolloRepository.findByPreparatoreOrderByDataScadenzaDesc(
            preparatore,
            Pageable.ofSize(PAGE_SIZE).withPage(page - 1));
    return pageProtocolli.toList();
  }

  @Override
  public void modificaSchedaAlimentare(Protocollo protocollo, Long idSchedaAlimentare) {
    protocollo.setSchedaAlimentare(getSchedaAlimentare(protocollo.getPreparatore().getId(),idSchedaAlimentare));
    protocolloRepository.save(protocollo);
  }
}
