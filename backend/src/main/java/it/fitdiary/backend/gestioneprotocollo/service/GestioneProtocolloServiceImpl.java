package it.fitdiary.backend.gestioneprotocollo.service;

import it.fitdiary.backend.entity.Alimento;
import it.fitdiary.backend.entity.Esercizio;
import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.entity.SchedaAllenamento;
import it.fitdiary.backend.gestioneprotocollo.adapter.SchedaAlimentareAdapter;
import it.fitdiary.backend.gestioneprotocollo.adapter.SchedaAlimentareAdapterImpl;
import it.fitdiary.backend.gestioneprotocollo.adapter.SchedaAllenamentoAdapter;
import it.fitdiary.backend.gestioneprotocollo.adapter.SchedaAllenamentoAdapterImpl;
import it.fitdiary.backend.gestioneprotocollo.repository.AlimentoRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.EsercizioRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.ProtocolloRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.SchedaAlimentareRepository;
import it.fitdiary.backend.gestioneprotocollo.repository.SchedaAllenamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GestioneProtocolloServiceImpl
        implements GestioneProtocolloService {
    private final ProtocolloRepository protocolloRepository;
    private final AlimentoRepository alimentoRepository;
    private final SchedaAlimentareRepository schedaAlimentareRepository;
    private final SchedaAlimentareAdapter schedaAlimentareAdapter =
            new SchedaAlimentareAdapterImpl();
    private final SchedaAllenamentoAdapter schedaAllenamentoAdapter =
            new SchedaAllenamentoAdapterImpl();
    private final SchedaAllenamentoRepository schedaAllenamentoRepository;
    private final EsercizioRepository esercizioRepository;

    @Autowired
    public GestioneProtocolloServiceImpl(
            final ProtocolloRepository protocolloRep,
            final AlimentoRepository alimentoRep,
            final SchedaAlimentareRepository schedaAlimentareRep,
            final SchedaAllenamentoRepository schedaAllenamentoRep,
            final EsercizioRepository esercizioRep) {
        this.protocolloRepository = protocolloRep;
        this.alimentoRepository = alimentoRep;
        this.schedaAlimentareRepository = schedaAlimentareRep;
        this.schedaAllenamentoRepository = schedaAllenamentoRep;
        this.esercizioRepository = esercizioRep;
    }

    @Override
    public Protocollo creazioneProtocollo(final Protocollo protocollo,
                                          final File schedaAlimentareFile,
                                          final File schedaAllenamentoFile)
            throws IOException, IllegalArgumentException {
        if (schedaAllenamentoFile == null && schedaAlimentareFile == null) {
            throw new IllegalArgumentException("Nessun file presente");
        }
        Protocollo newProtocollo = protocolloRepository.save(protocollo);
        if (schedaAlimentareFile != null) {
            if (FilenameUtils.getExtension(schedaAlimentareFile.getName())
                    .equals("csv")) {
                List<Alimento> alimenti =
                        schedaAlimentareAdapter.parse(schedaAlimentareFile);
                int kcal = 0;
                for (Alimento alimento : alimenti) {
                    kcal = kcal + alimento.getKcal();
                }
                SchedaAlimentare schedaAlimentare = new SchedaAlimentare();
                schedaAlimentare.setKcalAssunte(kcal);
                schedaAlimentare.setProtocollo(newProtocollo);
                SchedaAlimentare newSchedaAlimentare =
                        schedaAlimentareRepository.save(schedaAlimentare);
                for (Alimento alimento : alimenti) {
                    alimento.setSchedaAlimentare(newSchedaAlimentare);
                    alimentoRepository.save(alimento);
                }
                protocollo.setSchedaAlimentare(schedaAlimentare);
                schedaAlimentare.setListaAlimenti(alimenti);
            } else {
                throw new IllegalArgumentException("Formato file non valido");
            }
        }
        if (schedaAllenamentoFile != null) {
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
                schedaAllenamento.setProtocollo(newProtocollo);
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
        protocollo.setPreparatore(protocollo.getCliente().getPreparatore());
        return newProtocollo;
    }
}
