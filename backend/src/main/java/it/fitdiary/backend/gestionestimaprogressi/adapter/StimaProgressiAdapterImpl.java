package it.fitdiary.backend.gestionestimaprogressi.adapter;

import it.fitdiary.backend.entity.Report;
import org.pmml4s.model.Model;

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
                Model.fromString("<?xml version=\"1.0\" encoding=\"UTF-8\" "
                        + "standalone=\"yes\"?>\n"
                        + "<PMML xmlns=\"http://www.dmg.org/PMML-4_4\" "
                        + "xmlns:data=\"http://jpmml"
                        + ".org/jpmml-model/InlineTable\" version=\"4.4\">\n"
                        + "\t<Header>\n"
                        + "\t\t<Application name=\"JPMML-SkLearn\" version=\"1"
                        +".6.34\"/>\n"
                        + "\t\t<Timestamp>2022-01-15T22:20:46Z</Timestamp>\n"
                        + "\t</Header>\n"
                        + "\t<MiningBuildTask>\n"
                        + "\t\t<Extension name=\"repr\">PMMLPipeline(steps=["
                        + "('Regression', LinearRegression())])</Extension>\n"
                        + "\t</MiningBuildTask>\n"
                        + "\t<DataDictionary>\n"
                        + "\t\t<DataField name=\"pesoPerso\" "
                        + "optype=\"continuous\" dataType=\"double\"/>\n"
                        + "\t\t<DataField name=\"kcal\" optype=\"continuous\""
                        + " dataType=\"double\"/>\n"
                        + "\t\t<DataField name=\"sesso\" "
                        + "optype=\"continuous\" dataType=\"double\"/>\n"
                        + "\t\t<DataField name=\"peso\" optype=\"continuous\""
                        + " dataType=\"double\"/>\n"
                        + "\t\t<DataField name=\"eta\" optype=\"continuous\" "
                        + "dataType=\"double\"/>\n"
                        + "\t</DataDictionary>\n"
                        + "\t<RegressionModel functionName=\"regression\" "
                        + "algorithmName=\"sklearn.linear_model._base"
                        + ".LinearRegression\">\n"
                        + "\t\t<MiningSchema>\n"
                        + "\t\t\t<MiningField name=\"pesoPerso\" "
                        + "usageType=\"target\"/>\n"
                        + "\t\t\t<MiningField name=\"kcal\"/>\n"
                        + "\t\t\t<MiningField name=\"sesso\"/>\n"
                        + "\t\t\t<MiningField name=\"peso\"/>\n"
                        + "\t\t\t<MiningField name=\"eta\"/>\n"
                        + "\t\t</MiningSchema>\n"
                        + "\t\t<RegressionTable intercept=\"1"
                        + ".7653201958573017\">\n"
                        + "\t\t\t<NumericPredictor name=\"kcal\" "
                        + "coefficient=\"-0.001150430807893662\"/>\n"
                        + "\t\t\t<NumericPredictor name=\"sesso\" "
                        + "coefficient=\"0.2453999639007302\"/>\n"
                        + "\t\t\t<NumericPredictor name=\"peso\" "
                        + "coefficient=\"0.02172492598894346\"/>\n"
                        + "\t\t\t<NumericPredictor name=\"eta\" "
                        + "coefficient=\"-0.0073789492137843055\"/>\n"
                        + "\t\t</RegressionTable>\n"
                        + "\t</RegressionModel>\n"
                        +"</PMML>");
    }

    /**
     * metodo che serve a calcolare il peso per il mese prossimo.
     *
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
