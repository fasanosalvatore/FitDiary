package it.fitdiary.backend.gestioneprotocollo.repository;

import it.fitdiary.backend.entity.Protocollo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProtocolloRepository extends JpaRepository<Protocollo, Long> {
}