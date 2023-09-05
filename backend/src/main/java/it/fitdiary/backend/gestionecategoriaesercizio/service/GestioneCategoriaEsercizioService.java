package it.fitdiary.backend.gestionecategoriaesercizio.service;

import it.fitdiary.backend.entity.CategoriaEsercizio;

import java.util.List;
import java.util.Optional;

public interface GestioneCategoriaEsercizioService
{
    Optional<CategoriaEsercizio> getById(final Long idCategoria);
    List<CategoriaEsercizio> getAllCategorie();
}
