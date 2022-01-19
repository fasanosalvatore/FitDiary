package it.fitdiary.backend.gestioneprotocollo.adapter;

import it.fitdiary.backend.entity.Alimento;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SchedaAlimentareAdapterImplTest {

    SchedaAlimentareAdapterImpl adapter=new SchedaAlimentareAdapterImpl();

    @Test
    void parseSucces() throws IOException {
        var schedaAlimentare=new ArrayList<Alimento>();
        schedaAlimentare.add(new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null));
        schedaAlimentare.add(new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null));
        schedaAlimentare.add(new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null));
        schedaAlimentare.add(new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null));
        schedaAlimentare.add(new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null));
        schedaAlimentare.add(new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null));
        schedaAlimentare.add(new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null));
        schedaAlimentare.add(new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null));
        schedaAlimentare.add(new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null));
        schedaAlimentare.add(new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null));
        schedaAlimentare.add(new Alimento(null,"Pasta","pranzo","1",200f,"100",
                null));
        File schedaAlimentareFile = new File(
                getClass().getClassLoader().getResource("schedaAlimentare.csv")
                        .getFile());
        assertEquals(schedaAlimentare,adapter.parse(schedaAlimentareFile));
    }

    @Test
    void parseTrownIllegalArgumentException() throws IOException {
        File schedaAlimentareFile = new File(
                getClass().getClassLoader().getResource(
                        "schedaAlimentareError" +
                                ".csv")
                        .getFile());
        assertThrows(IllegalArgumentException.class,()->
                adapter.parse(schedaAlimentareFile));
    }
}