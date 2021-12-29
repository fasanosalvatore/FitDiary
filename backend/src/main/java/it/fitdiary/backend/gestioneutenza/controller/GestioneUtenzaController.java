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
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseHandler.generateResponse(HttpStatus.CREATED, "utente",
                newUtente);
    }

    /**
     * Questo metodo prende i parametri inseriti nel body della richiesta http e li passa al service
     * @param utente rappresenta l'insieme dei dati personali di un utente
     * @return utente rappresenta l'utente con i nuovi dati inserito nel database
     */
    @PostMapping("cliente/create")
    ResponseEntity<Object> inserimentoDatiPersonaliCliente(@RequestBody Utente utente) {
        try {
            Utente newUtente = service.inserimentoDatiPersonaliCliente(utente);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, "utente",
                    newUtente);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Questo metodo prende i parametri inseriti per modificare nel body della richiesta http e li passa al service
     * @param utente rappresenta l'insieme dei dati personali di un utente
     * @return utente rappresenta l'utente con i nuovi dati inserito nel database
     */
    @PostMapping("cliente/update")
    ResponseEntity<Object> modificaDatiPersonaliCliente(@RequestBody Utente utente) {
        try {
            Utente newUtente = service.inserimentoDatiPersonaliCliente(utente);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, "utente",
                    newUtente);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Questo metodo prende in input un preparatore di tipo Utente che contiene i dati da modificare dalbody della richiesta http e li passa al service
     * @param preparatore rappersenta l' insieme dei dati persoanli da aggoirnare di un preparatore
     * @return upadtedPreparatore rappresenta l' utente con i nuovi dati inseriti nel database
     */
    @PostMapping("preparatore/update")
    ResponseEntity<Object> modificaDatiPersonaliCliente(@RequestBody Utente preparatore){
        try{
            Utente updatedPrepartore = service.modificaDatiPersonaliPreparatore(preparatore);
            return ResponseHandler.generateResponse(HttpStatus.CREATED,"preparatore",
                    updatedPrepartore);
        }catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
