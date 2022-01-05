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
    /**
     * Repository del protocollo.
     */
    private final ProtocolloRepository protocolloRepository;
    /**
     * Repository dell'alimento.
     */
    private final AlimentoRepository alimentoRepository;
    /**
     * Repository della Scheda alimentare.
     */
    private final SchedaAlimentareRepository schedaAlimentareRepository;
    /**
     * Adapter della scheda alimentare.
     */
    private final SchedaAlimentareAdapter schedaAlimentareAdapter =
            new SchedaAlimentareAdapterImpl();
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
     * @param protocolloRep repository del protocollo
     * @param alimentoRep repository degli alimenti
     * @param schedaAlimentareRep repository della scheda alimentare
     * @param schedaAllenamentoRep repository della scheda allenamento
     * @param esercizioRep repository degli esercizi
     */
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

    /**
     * @param protocollo            nuovo protocollo
     * @param schedaAlimentareFile  file scheda alimentare del nuovo protocollo
     * @param schedaAllenamentoFile file scheda allenamento del nuovo protocollo
     * @return
     * @throws IOException
     * @throws IllegalArgumentException
     */
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
