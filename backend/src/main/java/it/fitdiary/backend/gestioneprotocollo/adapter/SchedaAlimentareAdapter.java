package it.fitdiary.backend.gestioneprotocollo.adapter;

import it.fitdiary.backend.entity.Alimento;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface SchedaAlimentareAdapter {
    List<Alimento> parse(File file) throws IOException;
}
