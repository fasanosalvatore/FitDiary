package it.fitdiary.backend.gestionereport.repository;

import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    /**
     * @param cliente Cliente per cui restituire tutti i report
     * @return lista dei report del cliente
     */
    List<Report> findAllByCliente(Utente cliente);
}
