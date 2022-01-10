package it.fitdiary.backend.gestioneprotocollo.adapter;

import it.fitdiary.backend.entity.Esercizio;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface SchedaAllenamentoAdapter {
    /**
     * @param file file della scheda allenamento
     * @return lista di esercizi
     * @throws IOException
     */
    List<Esercizio> parse(File file)
            throws IOException, IllegalArgumentException;
}
