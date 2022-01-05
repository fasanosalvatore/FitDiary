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

    public static final int I3 = 3;
    public static final int I4 = 4;
    public static final int I5 = 5;

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
