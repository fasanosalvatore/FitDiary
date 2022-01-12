package it.fitdiary.backend.gestioneutenza.repository;

import it.fitdiary.backend.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    /**
     *
     * @param email
     * @return utente
     */
    Utente findByEmail(String email);

    /**
     *
     * @param email
     * @return vero se esiste, altrimenti false
     */
    boolean existsByEmail(String email);

    /**
     *
     * @param preparatore preparatore
     * @param idCliente id del cliente
     * @return vero se il cliente fa parte della lista
     * dei clienti di quel preparatore, falso altrimenti
     */
    boolean existsByPreparatoreAndId(Utente preparatore, Long idCliente);

    /**
     *
     * @param idCliente id del cliente
     */
    @Modifying
    @Query("delete from Utente u where u.id = :idCliente")
    void deleteUtenteById(@Param("idCliente") Long idCliente);
}
