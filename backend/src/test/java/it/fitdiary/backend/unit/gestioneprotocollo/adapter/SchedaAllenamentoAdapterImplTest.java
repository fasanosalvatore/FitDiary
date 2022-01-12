package it.fitdiary.backend.unit.gestioneprotocollo.adapter;

import it.fitdiary.backend.entity.Esercizio;
import it.fitdiary.backend.gestioneprotocollo.adapter.SchedaAllenamentoAdapterImpl;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SchedaAllenamentoAdapterImplTest {

    SchedaAllenamentoAdapterImpl adapter = new SchedaAllenamentoAdapterImpl();

    @Test
    void parseSuccess() throws IOException {
        var esercizi = new ArrayList<Esercizio>();
        esercizi.add(new Esercizio(null, "pushup", "3", "10", "1", "1", "petto",
                null));
        esercizi.add(new Esercizio(null, "pushup", "3", "10", "1", "1", "petto",
                null));
        esercizi.add(new Esercizio(null, "pushup", "3", "10", "1", "1", "petto",
                null));
        esercizi.add(new Esercizio(null, "pushup", "3", "10", "1", "1", "petto",
                null));
        esercizi.add(new Esercizio(null, "pushup", "3", "10", "1", "2", "petto",
                null));
        esercizi.add(new Esercizio(null, "pushup", "3", "10", "1", "2", "petto",
                null));
        esercizi.add(new Esercizio(null, "pushup", "3", "10", "1", "2", "petto",
                null));
        esercizi.add(new Esercizio(null, "pushup", "3", "10", "1", "2", "petto",
                null));
        esercizi.add(new Esercizio(null, "pushup", "3", "10", "1", "3", "petto",
                null));
        esercizi.add(new Esercizio(null, "pushup", "3", "10", "1", "3", "petto",
                null));
        esercizi.add(new Esercizio(null, "pushup", "3", "10", "1", "3", "petto",
                null));
        File file=new File(
                getClass().getClassLoader().getResource("schedaAllenamento.csv")
                        .getFile());
        assertEquals(esercizi,adapter.parse(file));
    }
    @Test
    void parseTrownIllegalArgumentException() throws IOException {
        File file = new File(
                getClass().getClassLoader().getResource(
                                "schedaAllenamentoError" +
                                        ".csv")
                        .getFile());
        assertThrows(IllegalArgumentException.class,()->
                adapter.parse(file));
    }
}