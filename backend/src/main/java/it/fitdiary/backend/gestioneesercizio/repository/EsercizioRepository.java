package it.fitdiary.backend.gestioneesercizio.repository;

import it.fitdiary.backend.entity.Esercizio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EsercizioRepository extends JpaRepository<Esercizio,Long>{}
