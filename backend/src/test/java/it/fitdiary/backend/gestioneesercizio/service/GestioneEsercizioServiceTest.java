package it.fitdiary.backend.gestioneesercizio.service;

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.CategoriaEsercizio;
import it.fitdiary.backend.entity.Esercizio;
import it.fitdiary.backend.gestioneesercizio.repository.EsercizioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BackendApplicationTest.class)
@ActiveProfiles("test")
public class GestioneEsercizioServiceTest {

    @Mock
    private EsercizioRepository esercizioRepository;

    @InjectMocks
    private GestioneEsercizioServiceImpl gestioneEsercizioService;

    private Esercizio esercizio;
    private List<Esercizio> listaEsercizi;
    @Test
    public void getByIdTest(){
        esercizio = new Esercizio();
        esercizio.setId(1L);

        when(esercizioRepository.findById(esercizio.getId())).thenReturn(Optional.of(esercizio));
        assertEquals(esercizio,
                gestioneEsercizioService.getById(
                        esercizio.getId()).get());
    }

    @Test
    public void getAllEserciziTest(){
        listaEsercizi = new ArrayList<>();
        esercizio = new Esercizio();
        esercizio.setId(1L);
        listaEsercizi.add(esercizio);
        when(esercizioRepository.findAll()).thenReturn(listaEsercizi);
        assertEquals(listaEsercizi,
                gestioneEsercizioService.getAll());

    }

}
