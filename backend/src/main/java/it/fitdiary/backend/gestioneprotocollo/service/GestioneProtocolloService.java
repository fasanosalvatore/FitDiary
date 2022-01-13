package it.fitdiary.backend.gestioneprotocollo.service;

import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Utente;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface GestioneProtocolloService {
    /**
     * @param protocollo            nuovo protocollo
     * @param schedaAlimentareFile  file scheda alimentare del nuovo protocollo
     * @param schedaAllenamentoFile file scheda allenamento del nuovo protocollo
     * @return Protocollo creato
     * @throws IOException
     * @throws IllegalArgumentException
     */
    Protocollo creazioneProtocollo(
            Protocollo protocollo,
            File schedaAlimentareFile,
            File schedaAllenamentoFile
    ) throws IOException, IllegalArgumentException;

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
     * @param protocollo protocollo
     * @param schedaAllenamentoFile file della scheda allenamento
     * @return nuovo protocollo
     * @throws IOException
     */
    Protocollo inserisciSchedaAllenamento(Protocollo protocollo,
                                          File schedaAllenamentoFile)
            throws IOException;

    /**
     * @param protocollo protocollo
     * @param schedaAlimentareFile file della scheda alimentare
     * @return nuovo protocollo
     * @throws IOException
     */
    Protocollo inserisciSchedaAlimentare(Protocollo protocollo,
                                         File schedaAlimentareFile)
            throws IOException;

    /**
     * @param preparatore preparatore
     * @param page numero pagine
     * @return lista protocolli creati da un preparatore divisi in pagine
     */
    List<Protocollo> getAllProtocolliPreparatore(Utente preparatore, int page);
}
