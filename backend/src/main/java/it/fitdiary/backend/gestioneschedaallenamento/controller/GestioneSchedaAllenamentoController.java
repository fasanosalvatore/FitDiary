package it.fitdiary.backend.gestioneschedaallenamento.controller;

import it.fitdiary.backend.entity.SchedaAllenamento;
import it.fitdiary.backend.entity.enums.GIORNO_SETTIMANA;
import it.fitdiary.backend.gestioneschedaallenamento.controller.dto.CreaSchedaAllenamentoDTO;
import it.fitdiary.backend.gestioneschedaallenamento.controller.dto.ModificaSchedaAllenamentoDTO;
import it.fitdiary.backend.gestioneschedaallenamento.service.GestioneSchedaAllenamentoService;
import it.fitdiary.backend.utility.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/schedaAllenamento")
public class GestioneSchedaAllenamentoController {
    private final GestioneSchedaAllenamentoService service;

    public GestioneSchedaAllenamentoController(GestioneSchedaAllenamentoService service) {
        this.service = service;
    }

    @PostMapping("creaScheda")
    public ResponseEntity<Object> creaSchedaAllenamento(
            @Valid @RequestBody CreaSchedaAllenamentoDTO creaSchedaAllenamentoParametri
    ){
      if(creaSchedaAllenamentoParametri.getName() == null || creaSchedaAllenamentoParametri.getName().isEmpty()){
          return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                  "il nome non può essere vuoto");
      }
        if(creaSchedaAllenamentoParametri.getIstanzeEsercizi() == null || creaSchedaAllenamentoParametri.getIstanzeEsercizi().isEmpty()){
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    "la lista degli esercizi non può essere vuota");
        }
        SchedaAllenamento schedaAllenamento;
        var request = ((ServletRequestAttributes)
                Objects.requireNonNull(
                        RequestContextHolder.getRequestAttributes()))
                .getRequest();
        Long idPreparatore =
                Long.parseLong(request.getUserPrincipal().getName());
        try {
            schedaAllenamento = service.creaSchedaAllenamento(creaSchedaAllenamentoParametri.getIstanzeEsercizi(),
                    creaSchedaAllenamentoParametri.getName(),
                    idPreparatore,
                    creaSchedaAllenamentoParametri.getFrequenza());
        } catch (Exception e)
        {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
        Map<String,Object> response = new HashMap<>();
        response.put("scheda_allenamento_id",schedaAllenamento.getId());
        return ResponseHandler.generateResponse(HttpStatus.CREATED, "response",
                response);
    }

    @PostMapping("modificaScheda")
    public ResponseEntity<Object> ModificaSchedaAllenamento(
            @Valid @RequestBody ModificaSchedaAllenamentoDTO modificaSchedaDTO
    ){
        if(modificaSchedaDTO.getName() == null || modificaSchedaDTO.getName().isEmpty()){
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    "il nome non può essere vuoto");
        }
        if(modificaSchedaDTO.getIstanzeEsercizi() == null || modificaSchedaDTO.getIstanzeEsercizi().isEmpty()){
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    "la lista degli esercizi non può essere vuota");
        }

        SchedaAllenamento schedaAllenamento;
        var request = ((ServletRequestAttributes)
                Objects.requireNonNull(
                        RequestContextHolder.getRequestAttributes()))
                .getRequest();
        Long idPreparatore =
                Long.parseLong(request.getUserPrincipal().getName());
        try {
            schedaAllenamento = service.modificaSchedaAllenamento(modificaSchedaDTO.getIstanzeEsercizi(),
                    modificaSchedaDTO.getName(),
                    idPreparatore,
                    modificaSchedaDTO.getSchedaId(),
                    modificaSchedaDTO.getFrequenza());
        } catch (Exception e)
        {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
        Map<String,Object> response = new HashMap<>();
        response.put("scheda_allenamento_id",schedaAllenamento.getId());
        return ResponseHandler.generateResponse(HttpStatus.OK, "response",
                response);
    }

    @GetMapping("getMySchedeAllenamento")
    public ResponseEntity<Object> getMySchedeAllenamento() {
        var request = ((ServletRequestAttributes)
                Objects.requireNonNull(
                        RequestContextHolder.getRequestAttributes()))
                .getRequest();
        Long idPreparatoreRichiedente =
                Long.parseLong(request.getUserPrincipal().getName());

        List<SchedaAllenamento> mySchedeAllenamento;
        try {
            mySchedeAllenamento = service.getSchedeAllenamentoByPreparaore(idPreparatoreRichiedente);
        }
        catch (Exception e)
        {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
        Map<String,Object> response = new HashMap<>();
        response.put("schede_allenamento",mySchedeAllenamento);
        return ResponseHandler.generateResponse(HttpStatus.OK, "response",
                response);
    }
    @GetMapping("getSchedaAllenamentoById")
    public ResponseEntity<Object> getSchedaAllenamentoById(
            @RequestParam Long idScheda
    ) {

        SchedaAllenamento schedaAllenamento;
        try {
            schedaAllenamento = service.getSchedeAllenamentoById(idScheda);
        }
        catch (Exception e)
        {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
        return ResponseHandler.generateResponse(HttpStatus.OK, "scheda_allenamento",
                schedaAllenamento);

    }

    @GetMapping("getEserciziBySchedaAndGiorno")
    public ResponseEntity<Object> getEserciziBySchedaAndGiorno(
        @RequestParam Long idScheda,
        @RequestParam int giorno){

        SchedaAllenamento schedaAllenamento;
        try {
            schedaAllenamento = service.getSchedeAllenamentoById(idScheda);
        }
        catch (Exception e)
        {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                e.getMessage());
        }
        return ResponseHandler.generateResponse(HttpStatus.OK, "esercizi",
            service.getIstanzeEserciziBySchedaAndGiornoDellaSettimana(schedaAllenamento,giorno));

    }


}
