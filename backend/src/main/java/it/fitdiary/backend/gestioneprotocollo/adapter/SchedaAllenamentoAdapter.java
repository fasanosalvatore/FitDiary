package it.fitdiary.backend.gestioneprotocollo.adapter;

import it.fitdiary.backend.entity.Esercizio;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface SchedaAllenamentoAdapter {
    List<Esercizio> parse(File file) throws IOException;
}
