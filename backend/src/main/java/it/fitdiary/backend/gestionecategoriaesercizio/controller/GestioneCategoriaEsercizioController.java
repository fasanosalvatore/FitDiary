package it.fitdiary.backend.gestionecategoriaesercizio.controller;

import it.fitdiary.backend.entity.CategoriaEsercizio;
import it.fitdiary.backend.gestionecategoriaesercizio.service.GestioneCategoriaEsercizioService;
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
@RequestMapping(path = "api/v1/categorieEsercizi")
public class GestioneCategoriaEsercizioController
{
    private GestioneCategoriaEsercizioService service;

    @Autowired
    public GestioneCategoriaEsercizioController(GestioneCategoriaEsercizioService service)
    {
        this.service=service;
    }

    @GetMapping("getCategoriaEsercizio")
    private ResponseEntity<Object> VisualizzaAlimento(@RequestParam("idCategoria") final Long idCategoria)
    {
        Optional<CategoriaEsercizio> categoria=service.getById(idCategoria);
        if(categoria.isPresent())
        {
            return ResponseHandler.generateResponse(HttpStatus.OK,"categoria",categoria.get());
        }
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,"Categoria non trovata");
    }

    @GetMapping("getAllCategorie")
    private ResponseEntity<Object> VisualizzaListaAlimenti()
    {
        List<CategoriaEsercizio> listCategorie=service.getAllCategorie();
        return ResponseHandler.generateResponse(HttpStatus.OK,"categorie",listCategorie);
    }
}
