package it.fitdiary.backend.getsioneschedaalimentare.controller;

import it.fitdiary.backend.entity.IstanzaAlimento;
import it.fitdiary.backend.entity.SchedaAlimentare;
import it.fitdiary.backend.getsioneschedaalimentare.controller.dto.CreaSchedaAlimentareDTO;
import it.fitdiary.backend.getsioneschedaalimentare.controller.dto.ModificaSchedaDTO;
import it.fitdiary.backend.getsioneschedaalimentare.service.GestioneSchedaAlimentareService;
import it.fitdiary.backend.utility.ResponseHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/schedaalimentare")
public class GestioneSchedaAlimentareController {

  private final GestioneSchedaAlimentareService service;

  @Autowired
  public GestioneSchedaAlimentareController(
      GestioneSchedaAlimentareService service) {
    this.service = service;
  }
  @PostMapping("creaScheda")
  public ResponseEntity<Object> creaSchedaAlimentare(
      @Valid @RequestBody CreaSchedaAlimentareDTO creaSchedaAlimentareParametri) {

    if(creaSchedaAlimentareParametri.getName() == null || creaSchedaAlimentareParametri.getName().isEmpty())
    {
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
          "il nome non può essere vuoto");
    }
    if(creaSchedaAlimentareParametri.getIstanzeAlimenti() == null || creaSchedaAlimentareParametri.getIstanzeAlimenti().isEmpty())
    {
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
          "le istanze alimenti non possono essere vuote");
    }
    SchedaAlimentare schedaAlimentare;
    var request = ((ServletRequestAttributes)
        Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes()))
        .getRequest();
    Long idPreparatore =
        Long.parseLong(request.getUserPrincipal().getName());
    try {
      schedaAlimentare = service.creaSchedaAlimentare(creaSchedaAlimentareParametri.getIstanzeAlimenti(),creaSchedaAlimentareParametri.getName(),idPreparatore);
    }
    catch (Exception e)
    {
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
          e.getMessage());
    }
    Map<String,Object> response = new HashMap<>();
    response.put("scheda_alimentare_id",schedaAlimentare.getId());
    return ResponseHandler.generateResponse(HttpStatus.CREATED, "response",
        response);

  }

  @PostMapping("modificaScheda")
  public ResponseEntity<Object> modificaSchedaAlimentare(
      @Valid @RequestBody ModificaSchedaDTO modificaSchedaDTO) {

    if(modificaSchedaDTO.getName() == null || modificaSchedaDTO.getName().isEmpty())
    {
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
          "il nome non può essere vuoto");
    }

    if(modificaSchedaDTO.getIstanzeAlimenti() == null || modificaSchedaDTO.getIstanzeAlimenti().isEmpty())
    {
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
          "le istanze alimenti non possono essere vuote");
    }

    SchedaAlimentare schedaAlimentare;
    var request = ((ServletRequestAttributes)
        Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes()))
        .getRequest();
    Long idPreparatoreRichiedente =
        Long.parseLong(request.getUserPrincipal().getName());
    try {
      schedaAlimentare = service.modificaSchedaAlimentare(modificaSchedaDTO.getIstanzeAlimenti(),modificaSchedaDTO.getName(),idPreparatoreRichiedente,modificaSchedaDTO.getSchedaId());
    }
    catch (Exception e)
    {
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
          e.getMessage());
    }
    Map<String,Object> response = new HashMap<>();
    response.put("scheda_alimentare_id",schedaAlimentare.getId());
    return ResponseHandler.generateResponse(HttpStatus.OK, "response",
        response);

  }

}
