package it.fitdiary.backend.gestioneprotocollo.adapter;

import it.fitdiary.backend.entity.Esercizio;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchedaAllenamentoAdapterImpl implements SchedaAllenamentoAdapter {

    /**
     * Indica il valore della posizione del campo Recupero.
     */
    public static final int I3 = 3;
    /**
     * Indica il valore della posizione del campo Numero allenamento.
     */
    public static final int I4 = 4;
    /**
     * Indica il valore della posizione del campo Categoria.
     */
    public static final int I5 = 5;

    /**
     * @param file file della scheda allenamento
     * @return lista di esercizi
     * @throws IOException
     */
    @Override
    public List<Esercizio> parse(final File file) throws IOException {
        List<Esercizio> esercizi = new ArrayList<Esercizio>();
        Iterable<CSVRecord> records =
                CSVFormat.EXCEL.withDelimiter(';').parse(new FileReader(file));
        int riga = 1;
        for (CSVRecord record : records) {
            if (riga == 1) {
                riga++;
                continue;
            }
            Esercizio esercizio = new Esercizio();
            esercizio.setNome(record.get(0));
            esercizio.setSerie(record.get(1));
            esercizio.setRipetizioni(record.get(2));
            esercizio.setRecupero(record.get(I3));
            esercizio.setNumeroAllenamento(record.get(I4));
            esercizio.setCategoria(record.get(I5));
            esercizi.add(esercizio);
        }
        return esercizi;
    }
}
