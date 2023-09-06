package it.fitdiary.backend.gestioneschedaallenamento.repository;

import it.fitdiary.backend.entity.IstanzaAlimento;
import it.fitdiary.backend.entity.IstanzaEsercizio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IstanzaEsercizioRepository extends JpaRepository<IstanzaEsercizio,Long> {
}
