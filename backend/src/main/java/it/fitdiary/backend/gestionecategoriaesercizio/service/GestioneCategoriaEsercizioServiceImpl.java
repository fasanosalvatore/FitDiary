package it.fitdiary.backend.gestionecategoriaesercizio.service;

import it.fitdiary.backend.entity.CategoriaEsercizio;
import it.fitdiary.backend.gestionecategoriaesercizio.repository.GestioneCategoriaEsercizioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GestioneCategoriaEsercizioServiceImpl implements GestioneCategoriaEsercizioService
{
    private final GestioneCategoriaEsercizioRepository categoriaRepository;

    @Override
    public Optional<CategoriaEsercizio> getById(Long idCategoria)
    {
        return categoriaRepository.findById(idCategoria);
    }
    @Override
    public List<CategoriaEsercizio> getAllCategorie()
    {
        return categoriaRepository.findAll();
    }
}
