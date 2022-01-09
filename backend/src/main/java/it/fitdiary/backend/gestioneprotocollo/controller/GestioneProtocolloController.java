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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

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
            @RequestParam("idCliente") final Long idCliente,
            @RequestParam(value = "schedaAlimentare", required = false)
            final MultipartFile schedaAlimentareMultipartFile,
            @RequestParam(value = "schedaAllenamento", required = false)
            final MultipartFile schedaAllenamentoMultipartFile) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        Long idPreparatore = Long.parseLong(
                request.getUserPrincipal().getName());

        Utente preparatore = gestioneUtenzaService.getById(idPreparatore);
        if (!gestioneUtenzaService.existsByPreparatoreAndId(
                preparatore, idCliente)) {
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                    "protocollo",
                    "Il preparatore non può creare "
                            + "un protocollo per questo cliente");
        }
        if ((schedaAllenamentoMultipartFile == null
                || schedaAllenamentoMultipartFile.isEmpty())
                && (schedaAlimentareMultipartFile == null
                || schedaAlimentareMultipartFile.isEmpty())) {
            return ResponseHandler.generateResponse(BAD_REQUEST, "protocollo",
                    "Almeno uno dei due file deve essere presente");
        }

        Protocollo protocollo = new Protocollo();
        protocollo.setDataScadenza(LocalDate.parse(dataScadenza));
        protocollo.setCliente(gestioneUtenzaService.getById(idCliente));
        protocollo.setPreparatore(gestioneUtenzaService.getById(idPreparatore));
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
                    "Errore nella lettura dei file");
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(BAD_REQUEST, "protocollo",
                    e.getMessage());
        }
    }

    @PutMapping("{idProtocollo}")
    private ResponseEntity<Object> modificaProtocollo(
            @PathVariable("idProtocollo") final Long idProtocollo,
            @RequestParam(value = "schedaAlimentare", required = false)
            final MultipartFile schedaAlimentareMultipartFile,
            @RequestParam(value = "schedaAllenamento", required = false)
            final MultipartFile schedaAllenamentoMultipartFile) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        Long idPreparatore = Long.parseLong(
                request.getUserPrincipal().getName());
        Utente preparatore = gestioneUtenzaService.getById(idPreparatore);
        Protocollo protocollo = null;
        try {
            protocollo =
                    gestioneProtocolloService.getByIdProtocollo(idProtocollo);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                    "protocollo",
                    "Il protocollo da modificare non esiste");
        }
        Long idCliente = protocollo.getCliente().getId();
        if (!gestioneUtenzaService.existsByPreparatoreAndId(preparatore,
                idCliente)) {
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                    "protocollo",
                    "Il preparatore non può modificare "
                            + "un protocollo per questo cliente");
        }
        if ((schedaAllenamentoMultipartFile == null
                || schedaAllenamentoMultipartFile.isEmpty())
                && (schedaAlimentareMultipartFile == null
                || schedaAlimentareMultipartFile.isEmpty())) {
            return ResponseHandler.generateResponse(BAD_REQUEST,
                    "protocollo",
                    "Almeno uno dei due file deve essere presente");
        }

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
            if (schedaAlimentareFile != null) {
                gestioneProtocolloService.inserisciSchedaAlimentare(
                        protocollo,
                        schedaAlimentareFile);
                schedaAlimentareFile.delete();
            }
            if (schedaAllenamentoFile != null) {
                gestioneProtocolloService.inserisciSchedaAllenamento(
                        protocollo,
                        schedaAllenamentoFile);
                schedaAllenamentoFile.delete();
            }
            return ResponseHandler.generateResponse(HttpStatus.CREATED,
                    "protocollo", protocollo);
        } catch (IOException e) {
            return ResponseHandler.generateResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR, "protocollo",
                    "Errore nella lettura dei file");
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(BAD_REQUEST,
                    "protocollo",
                    e.getMessage());
        }
    }

    /**
     * funzione per creare file da MultipartFile.
     *
     * @param multiPartFile input dal form
     * @return file
     * @throws IOException eccezione in caso di errore con il file
     */
    public File getFile(final MultipartFile multiPartFile)
            throws IOException {
        if (multiPartFile == null || multiPartFile.isEmpty()) {
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
     * @param id indica l' identificativo del protocollo
     * @return protocollo selezionato
     * @throws IOException
     */
    @GetMapping("{id}")
    public ResponseEntity<Object> visualizzaProtocollo(
            @PathVariable("id") final Long id)
            throws IOException {
        var request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        var principal =
                Long.parseLong(request.getUserPrincipal().getName());
        Protocollo protocollo =
                gestioneProtocolloService.getByIdProtocollo(id);
        if (protocollo.getCliente().getId() == principal
                || protocollo.getPreparatore().getId() == principal) {
            return ResponseHandler.generateResponse(HttpStatus.OK,
                    "protocollo",
                    protocollo);
        }
        return ResponseHandler.generateResponse(BAD_REQUEST,
                "l'utente non ha accesso a questo protocollo");
    }


    /**
     * Questa funzione permette di visualizzare una lista di protocolli.
     *
     * @param idCliente indica l' identificativo del cliente
     * @return lista protocolli
     */
    @GetMapping
    public ResponseEntity<Object> visualizzaStoricoProtocolli(
            @RequestParam(name =
                    "clienteId", required = false) final Long idCliente) {
        if (idCliente != null) {
            return visualizzaStoricoProtocolliPreparatore(idCliente);
        } else {
            return visualizzaStoricoProtocolliClienti();
        }
    }

    /**
     * @param idCliente id del cliente di
     *                  cui si vuole visualizzare il protocollo
     * @return lista di protocolli del cliente vuota o piena
     */
    public ResponseEntity<Object> visualizzaStoricoProtocolliPreparatore(
            final Long idCliente) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        Long idPreparatore = Long.parseLong(
                request.getUserPrincipal().getName());
        Utente preparatore = gestioneUtenzaService.getById(idPreparatore);
        if (!gestioneUtenzaService.existsByPreparatoreAndId(
                preparatore, idCliente)) {
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                    "protocollo",
                    "Il preparatore non può vedere "
                            + "la lista dei protocolli per questo cliente");
        }
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


    /**
     * @return storico dei protocolli del cliente autenticato
     */
    public ResponseEntity<Object> visualizzaStoricoProtocolliClienti() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        Long idCliente = Long.parseLong(
                request.getUserPrincipal().getName());
        Utente cliente = null;
        try {
            cliente = gestioneUtenzaService.getById(idCliente);

            return ResponseHandler.generateResponse(HttpStatus.OK,
                    "protocollo",
                    gestioneProtocolloService
                            .visualizzaStoricoProtocolliCliente(
                                    cliente));
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
    }

    /**
     * cattura dell'errore MissingServletRequestPartException.
     * @param ex errore
     * @return messaggio di errore formato jsend
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Object> handleMissingRequestBody(
            final MissingServletRequestPartException ex) {
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                ex.getMessage());
    }
}
