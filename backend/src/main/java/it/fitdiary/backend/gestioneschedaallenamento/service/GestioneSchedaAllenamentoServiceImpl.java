package it.fitdiary.backend.gestioneschedaallenamento.service;

import it.fitdiary.backend.entity.*;
import it.fitdiary.backend.gestioneesercizio.repository.EsercizioRepository;
import it.fitdiary.backend.gestioneschedaallenamento.controller.dto.IstanzaEsercizioDTO;
import it.fitdiary.backend.gestioneschedaallenamento.repository.IstanzaEsercizioRepository;
import it.fitdiary.backend.gestioneschedaallenamento.repository.SchedaAllenamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GestioneSchedaAllenamentoServiceImpl implements GestioneSchedaAllenamentoService{
    private final IstanzaEsercizioRepository istanzaEsercizioRepository;
    private final SchedaAllenamentoRepository schedaAllenamentoRepository;
    private final EsercizioRepository esercizioRepository;

    @Transactional
    @Override
    public SchedaAllenamento creaSchedaAllenamento(List<IstanzaEsercizioDTO> istanzeEsercizioDto, String nome,
                                                  Long idPreparatore, Integer frequenza) {
        SchedaAllenamento schedaAllenamento = new SchedaAllenamento();
        schedaAllenamento.setNome(nome);
        List<IstanzaEsercizio> istanzeEsercizio = new ArrayList<>();
        for(IstanzaEsercizioDTO istanzaEsercizioDTO: istanzeEsercizioDto){
            IstanzaEsercizio istanzaEsercizio = new IstanzaEsercizio();
            Optional<Esercizio> esercizio = esercizioRepository.findById(istanzaEsercizioDTO.getIdEsercizio());
            if(esercizio.isEmpty()){
                throw new IllegalStateException("uno delle istanze esercizio fa riferimento ad un Esercizio insesistente");
            }
            istanzaEsercizio.setSchedaAllenamento(schedaAllenamento);
            istanzaEsercizio.setEsercizio(esercizio.get());
            istanzaEsercizio.setGiornoDellaSettimana(istanzaEsercizioDTO.getGiornoDellaSettimana());
            istanzaEsercizio.setDescrizione(istanzaEsercizioDTO.getDescrizione());
            istanzaEsercizio.setRecupero(istanzaEsercizioDTO.getRecupero());
            istanzaEsercizio.setSerie(istanzaEsercizioDTO.getSerie());
            istanzaEsercizio.setRipetizioni(istanzaEsercizioDTO.getRipetizioni());
            istanzeEsercizio.add(istanzaEsercizio);
        }
        schedaAllenamento.setListaEsercizi(istanzeEsercizio);
        Utente creatore = new Utente();
        creatore.setId(idPreparatore);
        schedaAllenamento.setPreparatore(creatore);
        schedaAllenamento.setFrequenza(frequenza);

        return schedaAllenamentoRepository.save(schedaAllenamento);
    }

    @Transactional
    @Override
    public SchedaAllenamento modificaSchedaAllenamento(List<IstanzaEsercizioDTO> istanzeEsercizioDto, String nome,
                                                       Long idPreparatoreRichiedente, Long idScheda, Integer frequenza) {

        Optional<SchedaAllenamento> schedaAllenamentoDaModificareOptional = schedaAllenamentoRepository.findById(idScheda);
        if(schedaAllenamentoDaModificareOptional.isEmpty()){
            throw new IllegalStateException("scheda da modifcare con id " + idScheda + " non esiste");
        }
        SchedaAllenamento schedaAllenamentoDaModificare = schedaAllenamentoDaModificareOptional.get();
        if(!Objects.equals(schedaAllenamentoDaModificare.getPreparatore().getId(),
                idPreparatoreRichiedente))
        {
            throw new IllegalStateException("non hai i permessi per modificare la scheda con id " + idScheda);
        }
        schedaAllenamentoDaModificare.setNome(nome);
        List<IstanzaEsercizio> istanzeEsercizio = new ArrayList<>();
        for (IstanzaEsercizioDTO istanzaEsercizioDTO: istanzeEsercizioDto) {
            IstanzaEsercizio istanzaEsercizio = new IstanzaEsercizio();
            Optional<Esercizio> esercizio = esercizioRepository.findById(istanzaEsercizioDTO.getIdEsercizio());
            if(esercizio.isEmpty())
            {
                throw new IllegalStateException("uno delle istanze alimento fa riferimento ad un Alimento insesistente");
            }
            istanzaEsercizio.setSchedaAllenamento(schedaAllenamentoDaModificare);
            istanzaEsercizio.setEsercizio(esercizio.get());
            istanzaEsercizio.setGiornoDellaSettimana(istanzaEsercizioDTO.getGiornoDellaSettimana());
            istanzaEsercizio.setDescrizione(istanzaEsercizioDTO.getDescrizione());
            istanzaEsercizio.setRecupero(istanzaEsercizioDTO.getRecupero());
            istanzaEsercizio.setSerie(istanzaEsercizioDTO.getSerie());
            istanzaEsercizio.setRipetizioni(istanzaEsercizioDTO.getRipetizioni());
            istanzeEsercizio.add(istanzaEsercizio);
        }
        istanzaEsercizioRepository.deleteAll(schedaAllenamentoDaModificare.getListaEsercizi());
        schedaAllenamentoDaModificare.setListaEsercizi(istanzeEsercizio);
        schedaAllenamentoDaModificare.setFrequenza(frequenza);


        return schedaAllenamentoRepository.save(schedaAllenamentoDaModificare);
    }

    @Override
    public List<SchedaAllenamento> getSchedeAllenamentoByPreparaore(Long idPreparatoreRichiedente) {
        return schedaAllenamentoRepository.findAllByPreparatoreId(idPreparatoreRichiedente);
    }

    @Override
    public SchedaAllenamento getSchedeAllenamentoById(Long idScheda) {
        Optional<SchedaAllenamento> schedaAllenamentoOptional = schedaAllenamentoRepository.findById(idScheda);
        if(schedaAllenamentoOptional.isEmpty())
        {
            throw new IllegalStateException("scheda allenamento con id " + idScheda + " non trovata");
        }
        return schedaAllenamentoOptional.get();
    }
}
