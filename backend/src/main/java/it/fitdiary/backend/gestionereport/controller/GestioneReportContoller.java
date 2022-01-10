package it.fitdiary.backend.gestionereport.controller;

import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestionereport.service.GestioneReportService;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.utility.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/reports")
@RequiredArgsConstructor
public class GestioneReportContoller {
    /**
     * Service di gestionereport.
     */
    private final GestioneReportService gestioneReportService;
    /**
     * Service di gestione utenza.
     */
    private final GestioneUtenzaService gestioneUtenzaService;

    /**
     *
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
                        "report",
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
}
