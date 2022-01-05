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
import java.security.Principal;
import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/protocolli")
public class GestioneProtocolloController {
    private final GestioneProtocolloService gestioneProtocolloService;
    private final GestioneUtenzaService gestioneUtenzaService;

    @Autowired
    public GestioneProtocolloController(
            final GestioneProtocolloService gestioneProtocolloServ,
            final GestioneUtenzaService gestioneUtenzaServ) {
        this.gestioneProtocolloService = gestioneProtocolloServ;
        this.gestioneUtenzaService = gestioneUtenzaServ;
    }

    @PostMapping
    private ResponseEntity<Object> creazioneProtocollo(@RequestParam(
            "dataScadenza") final String dataScadenza,
            @RequestParam("emailCliente") final String email,
            @RequestParam("schedaAlimentareMultipartFile")
                final MultipartFile schedaAlimentareMultipartFile,
            @RequestParam("schedaAllenamentoMultipartFile")
                final MultipartFile schedaAllenamentoMultipartFile) {
        if (schedaAllenamentoMultipartFile.isEmpty()
                && schedaAlimentareMultipartFile.isEmpty()) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    "protocollo", "Almeno uno dei due file"
                            + " deve essere presente");
        }
        HttpServletRequest request =
                ((ServletRequestAttributes)
                        RequestContextHolder
                                .getRequestAttributes()).getRequest();
        Principal principal = request.getUserPrincipal();
        String emailPreparatore = principal.getName();
        Protocollo protocollo = new Protocollo();
        protocollo.setDataScadenza(LocalDate.parse(dataScadenza));
        protocollo.setCliente(gestioneUtenzaService.getUtenteByEmail(email));
        protocollo.setPreparatore(
                gestioneUtenzaService.getUtenteByEmail(emailPreparatore));
        File schedaAlimentareFile = null;

        File schedaAllenamentoFile = null;

        try {
            if (!schedaAlimentareMultipartFile.isEmpty()) {
                schedaAlimentareFile = new File(
                        schedaAlimentareMultipartFile.getOriginalFilename());
                schedaAlimentareFile.createNewFile();
                FileOutputStream fos = null;
                fos = new FileOutputStream(schedaAlimentareFile);
                fos.write(schedaAlimentareMultipartFile.getBytes());
                fos.close();
            }
            if (!schedaAllenamentoMultipartFile.isEmpty()) {
                schedaAllenamentoFile = new File(
                        schedaAllenamentoMultipartFile.getOriginalFilename());
                schedaAllenamentoFile.createNewFile();
                FileOutputStream fos1 = null;
                fos1 = new FileOutputStream(schedaAllenamentoFile);
                fos1.write(schedaAllenamentoMultipartFile.getBytes());
                fos1.close();
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "protocollo", "errore nella lettura dei file");
        }
        try {
            Protocollo newProtocollo =
                    gestioneProtocolloService.creazioneProtocollo(protocollo,
                            schedaAlimentareFile, schedaAllenamentoFile);
        } catch (IOException e) {
            return ResponseHandler.generateResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "protocollo", "errore nella lettura dei file");
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    "protocollo", e.getMessage());
        }
        return ResponseHandler.generateResponse(HttpStatus.CREATED,
                "protocollo", protocollo);
    }
}
