package it.fitdiary.backend.gestionereport.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestionereport.service.GestioneReportService;
import it.fitdiary.backend.gestionestimaprogressi.service.GestioneStimaProgressiService;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.utility.FileUtility;
import it.fitdiary.backend.utility.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/reports")
@RequiredArgsConstructor
public class GestioneReportController {
    /**
     * Service di stima progressi.
     */
    private final GestioneStimaProgressiService gestioneStimaProgressiService;
    /**
     * Service di gestionereport.
     */
    private final GestioneReportService gestioneReportService;
    /**
     * Service di gestione utenza.
     */
    private final GestioneUtenzaService gestioneUtenzaService;
    /**
     * Environment per prendere le proprietà dall application yaml.
     */
    @Autowired
    private Environment env;

    /**
     * @param idCliente id del cliente di cui si vuole visualizzare lo storico
     *                  progressi
     * @return storico dei progressi del cliente o del cliente il quale
     * preparatore vuole vedere lo storico
     */
    @GetMapping
    public ResponseEntity<Object> visualizzazioneStoricoProgressi(
            @RequestParam(name =
                    "clienteId", required = false) final Long idCliente) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        Long idUtente = Long.parseLong(
                request.getUserPrincipal().getName());
        if (idCliente != null) {
            Utente preparatore = gestioneUtenzaService.getById(idUtente);
            if (!gestioneUtenzaService.existsByPreparatoreAndId(
                    preparatore, idCliente)) {
                return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                        (Object)
                                "Il preparatore non può accedere "
                                + "ai report di questo cliente");
            }
            return ResponseHandler.generateResponse(HttpStatus.OK,
                    "report",
                    gestioneReportService.visualizzazioneStoricoProgressi(
                            gestioneUtenzaService
                                    .getById(idCliente)));
        } else {
            return ResponseHandler.generateResponse(HttpStatus.OK,
                    "report",
                    gestioneReportService.visualizzazioneStoricoProgressi(
                            gestioneUtenzaService
                                    .getById(idUtente)));
        }
    }

    /**
     * metodo che permette a un cliente d'inserisce un report.
     *
     * @param peso            peso
     * @param crfBicipite     circonferenza bicipite
     * @param crfAddome       circonferenza addome
     * @param crfQuadricipite circonferenza quadricipite
     * @param immagini        immagini
     * @return report salvato
     */
    @PostMapping
    ResponseEntity<Object> inserisciReport(
            @RequestParam(name = "peso") final Float peso,
            @RequestParam(name = "crfBicipite") final Float crfBicipite,
            @RequestParam(name = "crfAddome") final Float crfAddome,
            @RequestParam(name = "crfQuadricipite") final Float crfQuadricipite,
            @RequestParam(name = "immagini") final MultipartFile[] immagini) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        Long idCliente = Long.parseLong(
                request.getUserPrincipal().getName());
        Cloudinary cloudinary = new Cloudinary(env.getProperty("cloudinary"
                + ".url"));
        var report = new Report();
        report.setPeso(peso);
        report.setCrfBicipite(crfBicipite);
        report.setCrfAddome(crfAddome);
        report.setCrfQuadricipite(crfQuadricipite);
        try {
            report.setCliente(gestioneUtenzaService.getById(idCliente));
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    (Object) e.getMessage());
        }
        var listaLinkFoto = new ArrayList<String>();
        for (MultipartFile immagine : immagini) {
            if (immagine.isEmpty()) {
                continue;
            }
            try {
                var img = cloudinary.uploader()
                        .upload(FileUtility.getFile(immagine),
                                ObjectUtils.asMap("access_mode",
                                        "authenticated",
                                        "access_type", "token"));
                listaLinkFoto.add((String) img.get("secure_url"));
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseHandler.generateResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "errore nel caricamento delle immagini");
            }
        }
        var newReport = gestioneReportService.inserimentoReport(
                gestioneStimaProgressiService.generazioneStimaProgressi(report),
                listaLinkFoto);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, "report",
                newReport);
    }

    /**
     * @param id id del report da visualizzare
     * @return report da visualizzare
     */
    @GetMapping("{id}")
    public ResponseEntity<Object> visualizzaReport(
            @PathVariable("id") final Long id) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        Long idUtente = Long.parseLong(request.getUserPrincipal().getName());
        Utente utente = gestioneUtenzaService.getById(idUtente);
        Report report = null;
        try {
            report = gestioneReportService.getById(id);
        } catch (IllegalArgumentException ex) {
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                    (Object)
                            "Il report da visualizzare non esiste");
        }
        if (utente.getRuolo().getNome().equals(Ruolo.RUOLOPREPARATORE)) {
            if (!gestioneUtenzaService.existsByPreparatoreAndId(
                    utente, report.getCliente().getId())) {
                return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                        (Object)
                                "Il preparatore non può accedere "
                                + "al report di questo cliente");
            }
        } else {
            if (idUtente != report.getCliente().getId()) {
                return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                        (Object)
                                "Il cliente non può accedere "
                                + "al report di questo cliente");
            }
        }
        return ResponseHandler.generateResponse(HttpStatus.OK, "report",
                report);

    }

    /**
     * cerca report per data.
     * @param idCliente id cliente
     * @param data data creazione report
     * @return report
     */
    @GetMapping("search")
    public ResponseEntity<Object> cercaReportPerData(
            @RequestParam(name =
                    "clienteId", required = false) final Long idCliente,
            @RequestParam("data") final String data) {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();

        Long idUtente = Long.parseLong(request.getUserPrincipal().getName());
        Utente utente = gestioneUtenzaService.getById(idUtente!=null
                ? idUtente
                : idCliente);
        Report report = null;
        if (utente.getRuolo().getNome().equals(Ruolo.RUOLOPREPARATORE)) {
            if (!gestioneUtenzaService.existsByPreparatoreAndId(
                    utente, idCliente)) {
                return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                        (Object)
                                "Il preparatore non può accedere "
                                + "al report di questo cliente");
            } else {
                report = gestioneReportService.search(idCliente,
                        LocalDateTime.parse(data+"T18:11:16.776"));
            }
        } else {
            report = gestioneReportService.search(idCliente,
                    LocalDateTime.parse(data+"T18:11:16.776"));
        }
        return ResponseHandler.generateResponse(HttpStatus.OK, "report",
                report);
    }

}
