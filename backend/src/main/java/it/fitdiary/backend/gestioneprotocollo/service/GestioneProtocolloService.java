package it.fitdiary.backend.gestioneprotocollo.service;

import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Utente;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface GestioneProtocolloService {
    /**
     * @param  cliente il cliente del protocollo
     * @param preparatore il preparatore del protocollo
     * @param dataScadenza            la data di scadenza
     * @param idSchedaAlimentare  id scheda alimentare del nuovo protocollo
     * @param idSchedaAllenamento  id scheda allenamento del nuovo protocollo
     * @return Protocollo creato
     * @throws IOException
     * @throws IllegalArgumentException
     */
    Protocollo creazioneProtocollo(LocalDate dataScadenza, Utente cliente, Utente preparatore,
                                          final Long idSchedaAlimentare,
                                          final Long idSchedaAllenamento)
        throws IOException, IllegalArgumentException;

    /**
     * @param idProtocollo id del protocollo
     * @return protocollo trovato
     */
    Protocollo getByIdProtocollo(Long idProtocollo);

    /**
     * @param cliente cliente di cui visualizzare lo storico protocollo
     * @return lista protocolli del cliente
     */
    List<Protocollo> visualizzaStoricoProtocolliCliente(Utente cliente);


    /**
     * @param preparatore preparatore
     * @param page numero pagine
     * @return lista protocolli creati da un preparatore divisi in pagine
     */
    List<Protocollo> getAllProtocolliPreparatore(Utente preparatore, int page);

    void modificaSchedaAlimentare(Protocollo protocollo, Long idSchedaAlimentare);
    void modificaSchedaAllenamento(Protocollo protocollo, Long idSchedaAllenamento);
}
