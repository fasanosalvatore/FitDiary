package it.fitdiary.backend.gestioneprotocollo.adapter;

import it.fitdiary.backend.entity.Alimento;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface SchedaAlimentareAdapter {
    /**
     * @param file file della scheda alimentare
     * @return lista di alimenti
     * @throws IOException
     */
    List<Alimento> parse(File file)
            throws IOException, IllegalArgumentException;
}
