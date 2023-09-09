package it.fitdiary.backend.gestionecategoriaesercizio.service;

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.CategoriaEsercizio;
import it.fitdiary.backend.gestionecategoriaesercizio.repository.GestioneCategoriaEsercizioRepository;
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
public class GestioneCategoriaEsercizioServiceImplTest {

    @Mock
    private GestioneCategoriaEsercizioRepository gestioneCategoriaEsercizioRepository;

    @InjectMocks
    private GestioneCategoriaEsercizioServiceImpl gestioneCategoriaEsercizioService;

    private CategoriaEsercizio categoriaEsercizio;
    private List<CategoriaEsercizio> categorieEsercizi;
    @Test
    public void getByIdTest(){
        categoriaEsercizio = new CategoriaEsercizio();
        categoriaEsercizio.setId(1L);
        when(gestioneCategoriaEsercizioRepository.findById(categoriaEsercizio.getId())).
                thenReturn(Optional.ofNullable(categoriaEsercizio));
        assertEquals(categoriaEsercizio,
                gestioneCategoriaEsercizioService.getById(
                        categoriaEsercizio.getId()).get());
    }
    @Test
    public void getAllCategorieTest(){
        categorieEsercizi = new ArrayList<>();
        categoriaEsercizio = new CategoriaEsercizio();
        categoriaEsercizio.setId(1L);
        categorieEsercizi.add(categoriaEsercizio);
        when(gestioneCategoriaEsercizioRepository.findAll()).thenReturn(categorieEsercizi);
        assertEquals(categorieEsercizi,
                gestioneCategoriaEsercizioService.getAllCategorie());

    }

}
