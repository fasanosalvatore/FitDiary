package it.fitdiary.backend.gestioneprotocollo.controller;

import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.gestioneprotocollo.service.GestioneProtocolloService;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.utility.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/protocolli")
public class GestioneProtocolloController {
    /**
     * Service di gestione protocollo.
     */
    private final GestioneProtocolloService gestioneProtocolloService;
    /**
     * Service di gestione utenza.
     */
    private final GestioneUtenzaService gestioneUtenzaService;

    /**
     * @param gestioneProtocolloServ
     * @param gestioneUtenzaServ
     */
    @Autowired
    public GestioneProtocolloController(
            final GestioneProtocolloService gestioneProtocolloServ,
            final GestioneUtenzaService gestioneUtenzaServ) {
        this.gestioneProtocolloService = gestioneProtocolloServ;
        this.gestioneUtenzaService = gestioneUtenzaServ;
    }

    @PostMapping
    private ResponseEntity<Object> creazioneProtocollo(
            @RequestParam("dataScadenza") final String dataScadenza,
            @RequestParam("idCliente") final Long id,
            @RequestParam("schedaAlimentare")
            final MultipartFile schedaAlimentareMultipartFile,
            @RequestParam("schedaAllenamento")
            final MultipartFile schedaAllenamentoMultipartFile) {
        if (schedaAllenamentoMultipartFile.isEmpty()
                && schedaAlimentareMultipartFile.isEmpty()) {
            return ResponseHandler.generateResponse(BAD_REQUEST, "protocollo",
                    "Almeno uno dei due file deve essere presente");
        }
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        var idPrep = Long.parseLong(request.getUserPrincipal().getName());
        Protocollo protocollo = new Protocollo();
        protocollo.setDataScadenza(LocalDate.parse(dataScadenza));
        protocollo.setCliente(gestioneUtenzaService.getById(id));
        protocollo.setPreparatore(gestioneUtenzaService.getById(idPrep));
        File schedaAlimentareFile;
        File schedaAllenamentoFile;
        try {
            schedaAlimentareFile = getFile(schedaAlimentareMultipartFile);
            schedaAllenamentoFile = getFile(schedaAllenamentoMultipartFile);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR, "protocollo",
                    "errore nella lettura dei file");
        }
        try {
            Protocollo newProtocollo =
                    gestioneProtocolloService.creazioneProtocollo(protocollo,
                            schedaAlimentareFile, schedaAllenamentoFile);
            return ResponseHandler.generateResponse(HttpStatus.CREATED,
                    "protocollo", newProtocollo);
        } catch (IOException e) {
            return ResponseHandler.generateResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR, "protocollo",
                    "errore nella lettura dei file");
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(BAD_REQUEST, "protocollo",
                    e.getMessage());
        }
    }

    private File getFile(final MultipartFile multiPartFile)
            throws IOException {
        if (multiPartFile.isEmpty()) {
            return null;
        }
        var file = new File(multiPartFile.getOriginalFilename());
        file.createNewFile();
        var fos = new FileOutputStream(file);
        fos.write(multiPartFile.getBytes());
        fos.close();
        return file;
    }
}
