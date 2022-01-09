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
     * Indica il valore della posizione del campo Nome.
     */
    public static final int COLUMN_NOME = 0;
    /**
     * Indica il valore della posizione del campo Serie.
     */
    public static final int COLUMN_SERIE = 1;
    /**
     * Indica il valore della posizione del campo Ripetizioni.
     */
    public static final int COLUMN_RIPETIZIONI = 2;
    /**
     * Indica il valore della posizione del campo Recupero.
     */
    public static final int COLUMN_RECUPERO = 3;
    /**
     * Indica il valore della posizione del campo Numero allenamento.
     */
    public static final int COLUMN_NUMALLENAMENTO = 4;
    /**
     * Indica il valore della posizione del campo Categoria.
     */
    public static final int COLUMN_CATEGORIA = 5;

    /**
     * @param file file della scheda allenamento
     * @return lista di esercizi
     * @throws IOException
     */
    @Override
    public List<Esercizio> parse(final File file)
            throws IOException, IllegalArgumentException {
        List<Esercizio> esercizi = new ArrayList<Esercizio>();
        CSVFormat csvFormat =
                CSVFormat.Builder.create().setHeader(
                                "Nome", "Serie", "Ripetizioni", "Recupero",
                                "Numero Allenamento", "Categoria")
                        .setDelimiter(';').build();
        Iterable<CSVRecord> records =
                csvFormat.parse(new FileReader(file));
        int riga = 1;
        for (CSVRecord record : records) {
            if (!record.isConsistent()) {
                throw new IllegalArgumentException("errore nella compilazione "
                        + "della scheda allenamento");
            }
            if (riga == 1) {
                riga++;
                continue;
            }
            Esercizio esercizio = new Esercizio();
            esercizio.setNome(record.get(COLUMN_NOME));
            esercizio.setSerie(record.get(COLUMN_SERIE));
            esercizio.setRipetizioni(record.get(COLUMN_RIPETIZIONI));
            esercizio.setRecupero(record.get(COLUMN_RECUPERO));
            esercizio.setNumeroAllenamento(record.get(COLUMN_NUMALLENAMENTO));
            esercizio.setCategoria(record.get(COLUMN_CATEGORIA));
            esercizi.add(esercizio);
        }
        return esercizi;
    }
}
