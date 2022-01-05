package it.fitdiary.backend.gestioneprotocollo.service;

import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Utente;

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
    Protocollo creazioneProtocollo(
            Protocollo protocollo,
            File schedaAlimentareFile,
            File schedaAllenamentoFile
    ) throws IOException, IllegalArgumentException;

    Protocollo getByIdProtocollo(Long idProtocollo);
    Utente getPreparatoreById(Long idProtocollo);
    Utente getClienteById(Long idProtocollo);
}
