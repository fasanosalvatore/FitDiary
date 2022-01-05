package it.fitdiary.backend.gestioneprotocollo.service;

import it.fitdiary.backend.entity.Protocollo;

import java.io.File;
import java.io.IOException;

public interface GestioneProtocolloService {
    /**
     * @param protocollo nuovo protocollo
     * @param schedaAlimentareFile file scheda alimentare del nuovo protocollo
     * @param schedaAllenamentoFile file scheda allenamento del nuovo protocollo
     * @return Protocollo creato
     * @throws IOException
     * @throws IllegalArgumentException
     */
    Protocollo creazioneProtocollo(Protocollo protocollo,
                                          File schedaAlimentareFile,
                                          File schedaAllenamentoFile)
            throws IOException, IllegalArgumentException;
}
