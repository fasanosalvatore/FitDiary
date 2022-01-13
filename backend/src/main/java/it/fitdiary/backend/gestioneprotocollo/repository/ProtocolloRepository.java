package it.fitdiary.backend.gestioneprotocollo.repository;

import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProtocolloRepository extends JpaRepository<Protocollo, Long> {

    /**
     * @param cliente cliente per cui cercare i protocolli
     * @return lista di protocolli
     */
    List<Protocollo> findAllByCliente(Utente cliente);

    /**
     * @param preparatore preparatore
     * @param pageable    divisione in pagine
     * @return pagina del preparatore
     */
    Page<Protocollo> findByPreparatoreOrderByDataScadenzaDesc(
            Utente preparatore,
            Pageable pageable);
}
