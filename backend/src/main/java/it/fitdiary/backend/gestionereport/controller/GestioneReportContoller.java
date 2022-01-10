package it.fitdiary.backend.gestionereport.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import it.fitdiary.backend.entity.Report;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestionereport.service.GestioneReportService;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.utility.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import java.util.ArrayList;

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
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "hdjxm4zyg",
                "api_key", "988346186838798",
                "api_secret", "HkCMSqB99uwY8VaPv5a3y7h6Eiw"));
        var report = new Report();
        report.setPeso(peso);
        report.setCrfBicipite(crfBicipite);
        report.setCrfAddome(crfAddome);
        report.setCrfQuadricipite(crfQuadricipite);
        report.setPesoStimato(100f);
        try {
            report.setCliente(gestioneUtenzaService.getById(idCliente));
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    "cliente", e.getMessage());
        }
        var listaLinkFoto = new ArrayList<String>();
        for (MultipartFile immagine : immagini) {
            if (immagine.isEmpty()) {
                continue;
            }
            try {
                var img = cloudinary.uploader().upload(getFile(immagine),
                        ObjectUtils.asMap("access_mode", "authenticated",
                                "access_type", "token"));
                listaLinkFoto.add((String) img.get("secure_url"));
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseHandler.generateResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "errore nel caricamento delle immagini");
            }
        }
        var newReport = gestioneReportService.inserimentoReport(report,
                listaLinkFoto);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, "report",
                newReport);
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
}
