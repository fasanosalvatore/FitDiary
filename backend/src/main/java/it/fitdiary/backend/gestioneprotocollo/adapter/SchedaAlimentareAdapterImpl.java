package it.fitdiary.backend.gestioneprotocollo.adapter;

import it.fitdiary.backend.entity.Alimento;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SchedaAlimentareAdapterImpl implements SchedaAlimentareAdapter {

    /**
     * Indica il valore della posizione del campo Kcal.
     */
    public static final int I3 = 3;
    /**
     * Indica il valore della posizione del campo Grammi.
     */
    public static final int I4 = 4;

    /**
     * @param file file della scheda alimentare
     * @return
     * @throws IOException
     */
    @Override
    public List<Alimento> parse(final File file) throws IOException {
        List<Alimento> alimenti = new ArrayList<Alimento>();
        Iterable<CSVRecord> records =
                CSVFormat.EXCEL.withDelimiter(';').parse(new FileReader(file));
        int riga = 1;
        for (CSVRecord record : records) {
            if (riga == 1) {
                riga++;
                continue;
            }
            Alimento alimento = new Alimento();
            alimento.setNome(record.get(0));
            alimento.setPasto(record.get(1));
            alimento.setGiorno(record.get(2));
            alimento.setKcal(Integer.valueOf(record.get(I3)));
            alimento.setGrammi(Float.valueOf(record.get(I4)));
            alimenti.add(alimento);
        }
        return alimenti;
    }
}
