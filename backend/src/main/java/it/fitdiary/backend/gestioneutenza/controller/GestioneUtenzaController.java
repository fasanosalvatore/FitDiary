package it.fitdiary.backend.gestioneutenza.controller;

import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.utility.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/utenti")
public class GestioneUtenzaController {

    private GestioneUtenzaService service;

    @Autowired
    public GestioneUtenzaController(GestioneUtenzaService service) {
        this.service = service;
    }

    @PostMapping("create")
    ResponseEntity<Object> registrazione(@RequestBody Utente utente) {
        Utente newUtente = null;
        try {
            newUtente = service.registrazione(utente);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        return ResponseHandler.generateResponse(HttpStatus.CREATED,"Utente",
                newUtente);
    }
}
