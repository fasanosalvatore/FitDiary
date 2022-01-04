package it.fitdiary.backend.gestioneprotocollo.service;

import it.fitdiary.backend.entity.Protocollo;

import java.io.File;
import java.io.IOException;

public interface GestioneProtocolloService {
    Protocollo creazioneProtocollo(Protocollo protocollo,
                                          File schedaAlimentareFile,
                                          File schedaAllenamentoFile)
            throws IOException, IllegalArgumentException;
}
