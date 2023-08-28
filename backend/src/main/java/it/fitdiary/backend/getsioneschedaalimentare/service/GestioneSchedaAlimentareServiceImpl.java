package it.fitdiary.backend.getsioneschedaalimentare.service;

import it.fitdiary.backend.entity.IstanzaAlimento;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.getsioneschedaalimentare.repository.SchedaAlimentareRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GestioneSchedaAlimentareServiceImpl implements GestioneSchedaAlimentareService{

  SchedaAlimentareRepository repository;

  @Override
  public SchedaAlimentare creaSchedaAlimentare(List<IstanzaAlimento> istanzeAlimento, String name,
                                               Long idPreparatore) {
    SchedaAlimentare schedaAlimentare = new SchedaAlimentare();
    schedaAlimentare.setNome(name);
    schedaAlimentare.setListaAlimenti(istanzeAlimento);
    Utente creatore = new Utente();
    creatore.setId(idPreparatore);
    schedaAlimentare.setCreatore(creatore);
    LocalDateTime adesso = LocalDateTime.now();
    schedaAlimentare.setDataCreazione(adesso);
    schedaAlimentare.setDataAggiornamento(adesso);
    float kcalTotali = 0;
    for (IstanzaAlimento istanzaAlimento: istanzeAlimento) {
      kcalTotali += istanzaAlimento.getGrammi() * istanzaAlimento.getAlimento().getKcal()/100;
    }
    schedaAlimentare.setKcalAssunte(kcalTotali);
    return repository.save(schedaAlimentare);
  }

  @Override
  public SchedaAlimentare modificaSchedaAlimentare(List<IstanzaAlimento> istanzeAlimento,
                                                   String name, Long idPreparatoreRichiedente,
                                                   Long idScheda) {
    SchedaAlimentare schedaAlimentareDaModificare = repository.getById(idScheda);
    if(schedaAlimentareDaModificare == null)
    {
      throw new IllegalStateException("scheda da modifcare con id " + idScheda + " non esiste");
    }
    if(schedaAlimentareDaModificare.getCreatore().getId() != idPreparatoreRichiedente)
    {
      throw new IllegalStateException("non hai i permessi per modificare la scheda con id " + idScheda);
    }
    schedaAlimentareDaModificare.setNome(name);
    schedaAlimentareDaModificare.setListaAlimenti(istanzeAlimento);

    LocalDateTime adesso = LocalDateTime.now();
    schedaAlimentareDaModificare.setDataAggiornamento(adesso);
    float kcalTotali = 0;
    for (IstanzaAlimento istanzaAlimento: istanzeAlimento) {
      kcalTotali += istanzaAlimento.getGrammi() * istanzaAlimento.getAlimento().getKcal()/100;
    }
    schedaAlimentareDaModificare.setKcalAssunte(kcalTotali);
    return repository.save(schedaAlimentareDaModificare);
  }
}
