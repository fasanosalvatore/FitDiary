package it.fitdiary.backend.gestioneprotocollo.repository;

import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Utente;
import org.apache.maven.lifecycle.internal.LifecycleStarter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProtocolloRepository extends JpaRepository<Protocollo, Long> {

    List<Protocollo> findAllByCliente(Utente cliente);

}
