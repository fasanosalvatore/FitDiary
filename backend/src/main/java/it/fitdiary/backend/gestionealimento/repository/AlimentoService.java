package it.fitdiary.backend.gestionealimento.repository;

import it.fitdiary.backend.entity.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoService extends JpaRepository<Alimento, Long> {
}
