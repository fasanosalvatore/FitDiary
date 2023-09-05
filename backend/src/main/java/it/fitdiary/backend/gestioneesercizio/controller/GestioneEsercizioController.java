package it.fitdiary.backend.gestioneesercizio.controller;

import it.fitdiary.backend.entity.Esercizio;
import it.fitdiary.backend.gestioneesercizio.service.GestioneEsercizioService;
import it.fitdiary.backend.utility.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(path = "api/v1/esercizi")
public class GestioneEsercizioController
{
    private final GestioneEsercizioService service;

    @Autowired
    public GestioneEsercizioController(GestioneEsercizioService service)
    {
        this.service=service;
    }

    @GetMapping("getEsercizio")
    private ResponseEntity<Object> VisualizzaAlimento(@RequestParam("idAlimento") Long idAlimento)
    {
        Optional<Esercizio> esercizio=service.getById(idAlimento);
        if(esercizio.isPresent())
        {
            return ResponseHandler.generateResponse(HttpStatus.OK,"esercizio",esercizio);
        }
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,"Esercizio non trovato");
    }

    @GetMapping("getAllEsercizi")
    private ResponseEntity<Object> VisualizzaListaAlimenti()
    {
        List<Esercizio> listEsercizi=service.getAll();
        return ResponseHandler.generateResponse(HttpStatus.OK,"esercizi",listEsercizi);
    }
}
