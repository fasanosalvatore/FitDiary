package it.fitdiary.backend.gestioneprotocollo.repository;

import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProtocolloRepository extends JpaRepository<Protocollo, Long> {

    /**
     * @param cliente cliente per cui cercare i protocolli
     * @return lista di protocolli
     */
    List<Protocollo> findAllByCliente(Utente cliente);

}
