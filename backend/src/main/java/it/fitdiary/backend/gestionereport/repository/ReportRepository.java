package it.fitdiary.backend.gestionereport.repository;

import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    /**
     * @param cliente Cliente per cui restituire tutti i report
     * @return lista dei report del cliente
     */
    List<Report> findAllByCliente(Utente cliente);

    /**
     * cerca report per data.
     * @param idCliente id cliente
     * @param dataCreazione data creazione report
     * @return report
     */
    Report findFirstByCliente_IdAndDataCreazioneIsBeforeOrderByDataCreazioneDesc
    (
            Long idCliente, LocalDateTime dataCreazione
    );
}
