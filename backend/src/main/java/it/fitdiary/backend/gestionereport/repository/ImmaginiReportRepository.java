package it.fitdiary.backend.gestionereport.repository;

import it.fitdiary.backend.entity.ImmaginiReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImmaginiReportRepository
        extends JpaRepository<ImmaginiReport, Long> {
}
