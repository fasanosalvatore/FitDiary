package it.fitdiary.backend.gestioneprotocollo.service;

import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.SchedaAllenamento;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneprotocollo.repository.ProtocolloRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.GestioneProtocolloSchedaAlimentareRepository;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import it.fitdiary.backend.gestioneschedaallenamento.repository.SchedaAllenamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

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
   * Repository della scheda allenamento.
   */
  private final SchedaAllenamentoRepository schedaAllenamentoRepository;


  /**
   * @param cliente               il cliente del protocollo
   * @param preparatore           il preparatore del protocollo
   * @param dataScadenza          la data di scadenza
   * @param idSchedaAlimentare    id scheda alimentare del nuovo protocollo
   * @param idSchedaAllenamento  id scheda allenamento del nuovo protocollo
   * @return Protocollo creato
   * @throws IOException
   * @throws IllegalArgumentException
   **/
  @Override
  public Protocollo creazioneProtocollo(LocalDate dataScadenza, Utente cliente, Utente preparatore,
                                        Long idSchedaAlimentare,
                                        Long idSchedaAllenamento)
      throws IOException, IllegalArgumentException {
    Protocollo newProtocollo = new Protocollo();
    newProtocollo.setCliente(cliente);
    newProtocollo.setPreparatore(preparatore);
    newProtocollo.setDataScadenza(dataScadenza);
    if (idSchedaAlimentare != null) {
      newProtocollo.setSchedaAlimentare(
          getSchedaAlimentare(preparatore.getId(), idSchedaAlimentare));
    }
    if (idSchedaAllenamento != null) {
      newProtocollo.setSchedaAllenamento(
              getSchedaAllenamento(preparatore.getId(), idSchedaAlimentare));
    }

    return protocolloRepository.save(newProtocollo);
  }

  /**
   * @param preparatoreId      preparatore che possiede la scheda
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

  private SchedaAllenamento getSchedaAllenamento(final Long preparatoreId,
                                               final Long schedaAllenamentoId)
          throws IllegalArgumentException {

    Optional<SchedaAllenamento> schedaAllenamento =
            schedaAllenamentoRepository.findById(schedaAllenamentoId);
    if (schedaAllenamento.isEmpty()) {
      throw new IllegalArgumentException(
              "la scheda allenamento con id " + schedaAllenamentoId + " non esiste");
    }
    if (!Objects.equals(preparatoreId,
            schedaAllenamento.get().getPreparatore().getId())) {
      throw new IllegalArgumentException(
              "non hai i permessi per gestire la scheda allenamento di id " + schedaAllenamentoId);
    }

    return schedaAllenamento.get();
  }


  /**
   * @param idProtocollo id del protocollo
   * @return protocollo
   */
  public Protocollo getByIdProtocollo(final Long idProtocollo) {
    if (idProtocollo == null) {
      throw new IllegalArgumentException("Id non valido");
    }
    Optional<Protocollo> protocolloOptional = protocolloRepository.findById(idProtocollo);
    if (protocolloOptional.isEmpty()) {

      throw new IllegalArgumentException("Il protocollo non esiste");
    }

    return protocolloOptional.get();
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
    protocollo.setSchedaAlimentare(
        getSchedaAlimentare(protocollo.getPreparatore().getId(), idSchedaAlimentare));
    protocolloRepository.save(protocollo);
  }

  @Override
  public void modificaSchedaAllenamento(Protocollo protocollo, Long idSchedaAllenamento) {
    protocollo.setSchedaAllenamento(
            getSchedaAllenamento(protocollo.getPreparatore().getId(), idSchedaAllenamento));
    protocolloRepository.save(protocollo);
  }
}
