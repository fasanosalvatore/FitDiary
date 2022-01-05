package it.fitdiary.backend.gestioneprotocollo.controller;

import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneprotocollo.service.GestioneProtocolloService;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.utility.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * Questa funzione permette di visualizzare
     * un proprio protocollo da parte di un cliente.
     *
     * @param request indica la risposta http inviata dal front-end
     * @return protocollo selezionato
     * @throws IOException
     */
    public ResponseEntity<Object> visualizzaProtocolloFromCliente(
            final HttpServletRequest
                    request)
            throws IOException {
        Principal principal = request.getUserPrincipal();
        var idProtocollo =
                Long.parseLong(request.getUserPrincipal().getName());
        try {
            Protocollo protocollo =
                    gestioneProtocolloService.getByIdProtocollo(idProtocollo);
            return ResponseHandler.generateResponse(HttpStatus.OK,
                    "protocollo",
                    protocollo
            );
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }

    }

    /**
     * Questa funzione permette di visualizzare un protocollo
     * assegnato ad un suo cliente da parte di un preparatore.
     *
     * @param request indica la risposta http inviata dal front-end
     * @return protocollo selezionato
     * @throws IOException
     */
    public ResponseEntity<Object> visualizzaProtocolloFromPreparatore(
            final HttpServletRequest
                    request)
            throws IOException {
        Principal principal = request.getUserPrincipal();
        var idProtocollo =
                Long.parseLong(request.getUserPrincipal().getName());
        Utente preparatore =
                gestioneProtocolloService.getPreparatoreById(idProtocollo);
        Utente cliente = gestioneProtocolloService.getClienteById(idProtocollo);
        int i;
        for (i = 0; i < preparatore.getListaClienti().size(); i++) {
            if (preparatore.getListaClienti().get(i).getId()
                    == cliente.getId()) {
                break;
            }
        }
        if (i >= preparatore.getListaClienti().size()) {
            return ResponseHandler.generateResponse(HttpStatus.NOT_ACCEPTABLE,
                    "Il cliente selezionato non appartiene al preparatore");
        }
        try {
            Protocollo protocollo =
                    gestioneProtocolloService.getByIdProtocollo(idProtocollo);
            return ResponseHandler.generateResponse(HttpStatus.OK,
                    "protocollo",
                    protocollo
            );
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }

    }

    /**
     * @param idCliente id del cliente di
     *                  cui si vuole visualizzare il protocollo
     * @return lista di protocolli del cliente vuota o piena
     */
    @GetMapping("cliente/{id}")
    public ResponseEntity<Object> visualizzaStoricoProtocolliCliente(
            @PathVariable("id") final Long idCliente) {
        try {
            Utente utenteCliente = gestioneUtenzaService.getById(idCliente);
            return ResponseHandler.generateResponse(HttpStatus.OK,
                    "protocollo",
                    gestioneProtocolloService
                            .visualizzaStoricoProtocolliCliente(
                            utenteCliente));
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
    }

}
