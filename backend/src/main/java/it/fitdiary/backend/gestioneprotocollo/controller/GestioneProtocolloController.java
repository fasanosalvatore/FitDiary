package it.fitdiary.backend.gestioneprotocollo.controller;

import it.fitdiary.backend.entity.Protocollo;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneprotocollo.service.GestioneProtocolloService;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.utility.FileUtility;
import it.fitdiary.backend.utility.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/protocolli")
public class GestioneProtocolloController {
    /**
     * max byte per la dimensione dei file.
     */
    public static final int MAX_FILE_UPLOAD = 52428800;
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
            @RequestParam(value = "schedaAlimentare", required = false) final MultipartFile schedaAlimentareMultipartFile,
            @RequestParam(value = "schedaAllenamento", required = false) final MultipartFile schedaAllenamentoMultipartFile) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        Long idPreparatore = Long.parseLong(
                request.getUserPrincipal().getName());

        Utente preparatore = gestioneUtenzaService.getById(idPreparatore);
        if (!gestioneUtenzaService.existsByPreparatoreAndId(
                preparatore, idCliente)) {
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                    (Object) "Il preparatore non può creare "
                            + "un protocollo per questo cliente");
        }
        if ((schedaAllenamentoMultipartFile == null
                && schedaAlimentareMultipartFile == null)
                || (schedaAllenamentoMultipartFile == null
                || schedaAllenamentoMultipartFile.isEmpty())
                && (schedaAlimentareMultipartFile == null
                || schedaAlimentareMultipartFile.isEmpty())) {
            return ResponseHandler.generateResponse(BAD_REQUEST, (Object)
                    "file assenti o corrotti ");
        }

        Protocollo protocollo = new Protocollo();
        protocollo.setDataScadenza(LocalDate.parse(dataScadenza));
        protocollo.setCliente(gestioneUtenzaService.getById(idCliente));
        protocollo.setPreparatore(gestioneUtenzaService.getById(idPreparatore));
        File schedaAlimentareFile;
        File schedaAllenamentoFile;
        try {
            schedaAlimentareFile =
                    FileUtility.getFile(schedaAlimentareMultipartFile);
            schedaAllenamentoFile =
                    FileUtility.getFile(schedaAllenamentoMultipartFile);
            if ((schedaAlimentareFile != null)
                    && (schedaAlimentareFile.length() > MAX_FILE_UPLOAD)) {
                return ResponseHandler.generateResponse(
                        HttpStatus.BAD_REQUEST,
                        (Object) "il file " + schedaAlimentareFile
                                .getName()
                                + " ha dimensioni elevate");
            } else if ((schedaAllenamentoFile != null)
                    && (schedaAllenamentoFile.length() > MAX_FILE_UPLOAD)) {
                return ResponseHandler.generateResponse(
                        HttpStatus.BAD_REQUEST,
                        (Object) "il file " + schedaAllenamentoFile
                                .getName()
                                + " ha dimensioni elevate");
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
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
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Errore nella lettura dei file");
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(BAD_REQUEST,
                    (Object) e.getMessage());
        }
    }

    @PutMapping("{idProtocollo}")
    private ResponseEntity<Object> modificaProtocollo(
            @PathVariable("idProtocollo") final Long idProtocollo,
            @RequestParam(value = "schedaAlimentare", required = false) final MultipartFile schedaAlimentareMultipartFile,
            @RequestParam(value = "schedaAllenamento", required = false) final MultipartFile schedaAllenamentoMultipartFile) {
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
                    (Object)
                            "Il protocollo da modificare non esiste");
        }
        Long idCliente = protocollo.getCliente().getId();
        if (!gestioneUtenzaService.existsByPreparatoreAndId(preparatore,
                idCliente)) {
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                    (Object)
                            "Il preparatore non può modificare "
                            + "un protocollo per questo cliente");
        }
        if ((schedaAllenamentoMultipartFile == null
                && schedaAlimentareMultipartFile == null)
                || (schedaAllenamentoMultipartFile == null
                || schedaAllenamentoMultipartFile.isEmpty())
                && (schedaAlimentareMultipartFile == null
                || schedaAlimentareMultipartFile.isEmpty())) {
            return ResponseHandler.generateResponse(BAD_REQUEST, (Object)
                    "file assenti o corrotti ");
        }

        File schedaAlimentareFile;
        File schedaAllenamentoFile;
        try {
            schedaAlimentareFile =
                    FileUtility.getFile(schedaAlimentareMultipartFile);
            schedaAllenamentoFile =
                    FileUtility.getFile(schedaAllenamentoMultipartFile);
            if ((schedaAlimentareFile != null)
                    && (schedaAlimentareFile.length() > MAX_FILE_UPLOAD)) {
                return ResponseHandler.generateResponse(
                        HttpStatus.BAD_REQUEST,
                        (Object) "il file " + schedaAlimentareFile
                                .getName()
                                + " ha dimensioni elevate");
            } else if ((schedaAllenamentoFile != null)
                    && (schedaAllenamentoFile.length() > MAX_FILE_UPLOAD)) {
                return ResponseHandler.generateResponse(
                        HttpStatus.BAD_REQUEST,
                        (Object) "il file " + schedaAllenamentoFile
                                .getName()
                                + " ha dimensioni elevate");
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "errore nella lettura dei file");
        }
        try {
            if (schedaAlimentareFile != null) {
                gestioneProtocolloService.inserisciSchedaAlimentare(
                        protocollo,
                        schedaAlimentareFile);
            }
            if (schedaAllenamentoFile != null) {
                gestioneProtocolloService.inserisciSchedaAllenamento(
                        protocollo,
                        schedaAllenamentoFile);
            }
            return ResponseHandler.generateResponse(HttpStatus.CREATED,
                    "protocollo", protocollo);
        } catch (IOException e) {
            return ResponseHandler.generateResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Errore nella lettura dei file");
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(BAD_REQUEST,
                    "protocollo",
                    e.getMessage());
        }
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
                (Object) "l'utente non ha accesso a questo protocollo");
    }


    /**
     * Questa funzione permette di visualizzare una lista di protocolli.
     *
     * @param idCliente indica l' identificativo del cliente
     * @param page      indica la pagina della lista di protocolli da prendere
     * @return lista protocolli
     */
    @GetMapping
    public ResponseEntity<Object> visualizzaStoricoProtocolli(
            @RequestParam(name =
                    "clienteId", required = false) final Long idCliente,
            @RequestParam(name = "page", required = false) final Integer page) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        Long idUtente = Long.parseLong(
                request.getUserPrincipal().getName());

        if (idCliente != null) {
            return visualizzaStoricoProtocolliPreparatore(idCliente);
        } else {
            Utente user;
            try {
                user = gestioneUtenzaService.getById(idUtente);
            } catch (IllegalArgumentException e) {
                return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                        (Object) e.getMessage());
            }
            if (user.getRuolo().getNome().equals(
                    Ruolo.RUOLOCLIENTE)) {
                return visualizzaStoricoProtocolliClienti();
            } else {
                return visualizzaListaProtocollo(page == null ? 1 : page);
            }
        }
    }


    private ResponseEntity<Object> visualizzaListaProtocollo(final int page) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        Long idPreparatore = Long.parseLong(
                request.getUserPrincipal().getName());
        Utente preparatore = gestioneUtenzaService.getById(idPreparatore);
        return ResponseHandler.generateResponse(HttpStatus.OK,
                "protocollo",
                gestioneProtocolloService.getAllProtocolliPreparatore(
                        preparatore, page));
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
                    (Object)
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
                    (Object) e.getMessage());
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
                    (Object) e.getMessage());
        }
    }

    /**
     * cattura dell'errore MissingServletRequestPartException.
     *
     * @param ex errore
     * @return messaggio di errore formato jsend
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Object> handleMissingRequestBody(
            final MissingServletRequestPartException ex) {
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                (Object) "file richiesto");
    }

}
