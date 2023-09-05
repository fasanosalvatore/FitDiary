package it.fitdiary.backend.gestioneesercizio.service;

import it.fitdiary.backend.entity.Esercizio;

import java.util.List;
import java.util.Optional;

public interface GestioneEsercizioService
{
    Optional<Esercizio> getById(Long idEsercizio);

    List<Esercizio> getAll();
}
