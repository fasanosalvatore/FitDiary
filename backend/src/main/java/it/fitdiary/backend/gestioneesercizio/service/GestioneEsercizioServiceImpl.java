package it.fitdiary.backend.gestioneesercizio.service;

import it.fitdiary.backend.entity.Esercizio;
import it.fitdiary.backend.gestioneesercizio.repository.EsercizioRepository;
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
public class GestioneEsercizioServiceImpl implements GestioneEsercizioService
{
    private final EsercizioRepository esercizioRepository;

    @Override
    public Optional<Esercizio> getById(Long idEsercizio)
    {
        return esercizioRepository.findById(idEsercizio);
    }

    @Override
    public List<Esercizio> getAll()
    {
        return esercizioRepository.findAll();
    }
}
