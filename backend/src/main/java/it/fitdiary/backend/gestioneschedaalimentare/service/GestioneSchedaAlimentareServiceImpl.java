package it.fitdiary.backend.gestioneschedaalimentare.service;

import it.fitdiary.backend.entity.Alimento;
import it.fitdiary.backend.entity.IstanzaAlimento;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestionealimento.repository.AlimentoRepository;
import it.fitdiary.backend.gestioneschedaalimentare.controller.dto.IstanzaAlimentoDTO;
import it.fitdiary.backend.gestioneschedaalimentare.repository.IstanzaAlimentoRepository;
import it.fitdiary.backend.gestioneschedaalimentare.repository.SchedaAlimentareRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GestioneSchedaAlimentareServiceImpl implements GestioneSchedaAlimentareService{

  private final SchedaAlimentareRepository schedaAlimentareRepository;
  private final IstanzaAlimentoRepository istanzaAlimentoRepository;
  private final AlimentoRepository alimentoRepository;

  @Transactional
  @Override
  public SchedaAlimentare creaSchedaAlimentare(List<IstanzaAlimentoDTO> istanzeAlimentoDto, String name,
                                               Long idPreparatore) {
    SchedaAlimentare schedaAlimentare = new SchedaAlimentare();
    schedaAlimentare.setNome(name);
    List<IstanzaAlimento> istanzeAlimento = new ArrayList<>();
    for (IstanzaAlimentoDTO istanzaAlimentoDTO: istanzeAlimentoDto) {
      IstanzaAlimento istanzaAlimento = new IstanzaAlimento();
      Optional<Alimento> alimento = alimentoRepository.findById(istanzaAlimentoDTO.getIdAlimento());
      if(alimento.isEmpty())
      {
        throw new IllegalStateException("uno delle istanze alimento fa riferimento ad un Alimento insesistente");
      }
      istanzaAlimento.setAlimento(alimento.get());
      istanzaAlimento.setGrammi(istanzaAlimentoDTO.getGrammi());
      istanzaAlimento.setPasto(istanzaAlimentoDTO.getPasto());
      istanzaAlimento.setGiornoDellaSettimana(istanzaAlimentoDTO.getGiornoDellaSettimana());
      istanzaAlimento.setSchedaAlimentare(schedaAlimentare);
      istanzeAlimento.add(istanzaAlimento);

    }
    schedaAlimentare.setListaAlimenti(istanzeAlimento);
    Utente creatore = new Utente();
    creatore.setId(idPreparatore);
    schedaAlimentare.setPreparatore(creatore);
    float kcalTotali = 0;
    for (IstanzaAlimento istanzaAlimento: istanzeAlimento) {
      kcalTotali += istanzaAlimento.getGrammi() * istanzaAlimento.getAlimento().getKcal()/100;
    }
    schedaAlimentare.setKcalAssunte(kcalTotali);
    return schedaAlimentareRepository.save(schedaAlimentare);
  }

  @Transactional
  @Override
  public SchedaAlimentare modificaSchedaAlimentare(List<IstanzaAlimentoDTO> istanzeAlimentoDTO,
                                                   String name, Long idPreparatoreRichiedente,
                                                   Long idScheda) {
    Optional<SchedaAlimentare> schedaAlimentareDaModificareOptional = schedaAlimentareRepository.findById(idScheda);
    if(schedaAlimentareDaModificareOptional.isEmpty())
    {
      throw new IllegalStateException("scheda da modifcare con id " + idScheda + " non esiste");
    }
    SchedaAlimentare schedaAlimentareDaModificare = schedaAlimentareDaModificareOptional.get();
    if(!Objects.equals(schedaAlimentareDaModificare.getPreparatore().getId(),
        idPreparatoreRichiedente))
    {
      throw new IllegalStateException("non hai i permessi per modificare la scheda con id " + idScheda);
    }
    schedaAlimentareDaModificare.setNome(name);

    List<IstanzaAlimento> istanzeAlimento = new ArrayList<>();
    for (IstanzaAlimentoDTO istanzaAlimentoDTO: istanzeAlimentoDTO) {
      IstanzaAlimento istanzaAlimento = new IstanzaAlimento();
      Optional<Alimento> alimento = alimentoRepository.findById(istanzaAlimentoDTO.getIdAlimento());
      if(alimento.isEmpty())
      {
        throw new IllegalStateException("uno delle istanze alimento fa riferimento ad un Alimento insesistente");
      }
      istanzaAlimento.setAlimento(alimento.get());
      istanzaAlimento.setGrammi(istanzaAlimentoDTO.getGrammi());
      istanzaAlimento.setPasto(istanzaAlimentoDTO.getPasto());
      istanzaAlimento.setGiornoDellaSettimana(istanzaAlimentoDTO.getGiornoDellaSettimana());
      istanzaAlimento.setSchedaAlimentare(schedaAlimentareDaModificare);
      istanzeAlimento.add(istanzaAlimento);

    }
    istanzaAlimentoRepository.deleteAll(schedaAlimentareDaModificare.getListaAlimenti());
    schedaAlimentareDaModificare.setListaAlimenti(istanzeAlimento);
    float kcalTotali = 0;
    for (IstanzaAlimento istanzaAlimento: istanzeAlimento) {
      kcalTotali += istanzaAlimento.getGrammi() * istanzaAlimento.getAlimento().getKcal()/100;
    }
    schedaAlimentareDaModificare.setKcalAssunte(kcalTotali);
    return schedaAlimentareRepository.save(schedaAlimentareDaModificare);
  }

  @Override
  public List<SchedaAlimentare> getSchedeAlimentariByPreparaore(Long idPreparatoreRichiedente) {
    return schedaAlimentareRepository.findAllByPreparatoreId(idPreparatoreRichiedente);
  }

  @Override
  public SchedaAlimentare getSchedeAlimentariById(Long idScheda) {
    Optional<SchedaAlimentare> schedaAlimentareOptional = schedaAlimentareRepository.findById(idScheda);
    if(schedaAlimentareOptional.isEmpty())
    {
      throw new IllegalStateException("scheda alimentare con id " + idScheda + " non trovata");
    }
    return schedaAlimentareOptional.get();
  }
}
