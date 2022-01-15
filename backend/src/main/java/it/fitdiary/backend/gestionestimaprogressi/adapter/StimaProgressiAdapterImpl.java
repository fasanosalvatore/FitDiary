package it.fitdiary.backend.gestionestimaprogressi.adapter;

import it.fitdiary.backend.entity.Report;
import org.pmml4s.model.Model;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StimaProgressiAdapterImpl implements StimaProgressiAdapter {

    /**
     * modello d'intelligenza artificiale.
     */
    private Model modelloIntelligente;

    /**
     * constructor.
     */
    public StimaProgressiAdapterImpl() {
        modelloIntelligente =
                Model.fromFile(new File(getClass()
                        .getClassLoader()
                        .getResource("RegressionPesoPerso.pmml")
                        .getFile()));
    }

    /**
     * metodo che serve a calcolare il peso per il mese prossimo.
     * @param report nuovo report
     * @return report con peso stimato
     */
    @Override
    public Report calcolaStimaProgressiReport(final Report report) {
        Map map = new HashMap<String, Double>();
        if (report.getCliente().getListaProtocolli().size() < 1) {
            report.setPesoStimato(report.getPeso());
            return report;
        }
        map.put("kcal",
                report.getCliente().getListaProtocolli().get(0)
                        .getSchedaAlimentare().getKcalAssunte());
        map.put("sesso", report.getCliente().getSesso().equals("M") ? 1 : 0);
        map.put("peso", report.getPeso());
        map.put("eta",
                LocalDate.now().getYear()
                        - report.getCliente().getDataNascita().getYear());
        report.setPesoStimato((float) (report.getPeso()
                - (double) modelloIntelligente.predict(map)
                .get("predicted_pesoPerso")));
        return report;
    }
}
