package it.fitdiary.backend.gestionealimento.controller;

import it.fitdiary.backend.entity.Alimento;
import it.fitdiary.backend.gestionealimento.service.GestioneAlimentoService;
import it.fitdiary.backend.gestionealimento.service.GestioneAlimentoServiceImpl;
import it.fitdiary.backend.utility.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/alimenti")
public class GestioneAlimentoController {


  private GestioneAlimentoService service;

  @Autowired
  public GestioneAlimentoController(
      GestioneAlimentoService service) {
    this.service = service;
  }

  @PostMapping
  private ResponseEntity<Object> VisualizzaAlimento(
      @RequestParam("idAlimento") final Long idCliente) {

    try {
      Alimento alimento = service.getById(idCliente);
      return ResponseHandler.generateResponse(HttpStatus.OK, "alimento",
          alimento
      );
    } catch (IllegalArgumentException e) {
      return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
          (Object) e.getMessage());
    }

  }


}
