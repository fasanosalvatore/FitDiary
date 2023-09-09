package it.fitdiary.backend.gestionealimento.service;

import it.fitdiary.BackendApplicationTest;
import it.fitdiary.backend.entity.Alimento;
import it.fitdiary.backend.gestionealimento.repository.AlimentoRepository;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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
public class GestioneAlimentoServiceImplTest {

    private Alimento alimento;
    @Mock
    private AlimentoRepository alimentoRepository;

    @InjectMocks
    private GestioneAlimentoServiceImpl gestioneAlimentoServiceImpl;
    @BeforeEach
    public void setUp(){

    }
    @Test
    public void getByIdTest(){
        alimento = new Alimento();
        alimento.setId(1L);
        when(alimentoRepository.findById(alimento.getId())).thenReturn(Optional.ofNullable(alimento));
        assertEquals(alimento,
                gestioneAlimentoServiceImpl.getById(
                        alimento.getId()));
    }

    @Test
    public void getAllAlimentiTest(){
        List<Alimento> alimenti =  new ArrayList<>();
        Alimento alimento = new Alimento(1L,"Pollo",100f,21f,46f,
                3f,"Alimenti/1.jpg");
        alimenti.add(alimento);
        when(alimentoRepository.findAll()).thenReturn(alimenti);
        assertEquals(alimenti,
                gestioneAlimentoServiceImpl.getAllAlimenti());

    }
}
