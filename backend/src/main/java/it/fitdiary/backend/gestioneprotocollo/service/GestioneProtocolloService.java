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
     * @param idPreparatore id del preparatore
     * @return preparatore
     */
    Utente getPreparatoreById(Long idPreparatore);

    /**
     * @param idCliente id del cliente
     * @return cliente
     */
    Utente getClienteById(Long idCliente);

    /**
     * @param cliente cliente di cui visualizzare lo storico protocollo
     * @return lista protocolli del cliente
     */
    List<Protocollo> visualizzaStoricoProtocolliCliente(Utente cliente);
}
