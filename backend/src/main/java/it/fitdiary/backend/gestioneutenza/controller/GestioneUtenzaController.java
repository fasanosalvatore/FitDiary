package it.fitdiary.backend.gestioneutenza.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.utility.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;


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
     *
     * @param utente rappresenta l'insieme dei dati personali di un utente
     * @return utente rappresenta l'utente con i nuovi dati inserito nel database
     */
    @PostMapping("cliente")
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
     *
     * @param utente rappresenta l'insieme dei dati personali di un utente
     * @return utente rappresenta l'utente con i nuovi dati inserito nel database
     */
    @PutMapping("cliente")
    ResponseEntity<Object> modificaDatiPersonaliCliente(@RequestBody Utente utente) {
        try {
            Utente newUtente = service.modificaDatiPersonaliCliente(utente);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, "utente",
                    newUtente);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Questo metodo prende in input un preparatore di tipo Utente che contiene i dati da modificare dalbody della richiesta http e li passa al service
     *
     * @param preparatore rappersenta l' insieme dei dati persoanli da aggoirnare di un preparatore
     * @return upadtedPreparatore rappresenta l' utente con i nuovi dati inseriti nel database
     */
    @PutMapping("preparatore")
    ResponseEntity<Object> modificaDatiPersonaliPreparatore(@RequestBody Utente preparatore) {
        try {
            Utente updatedPrepartore = service.modificaDatiPersonaliPreparatore(preparatore);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, "preparatore",
                    updatedPrepartore);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Questo metodo permette di effettuare il refresh del token per un utente
     *
     * @param request  richiesta Http
     * @param response risposta Http
     * @throws IOException
     */
    @GetMapping("token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                Utente utente = service.getUtenteByEmail(email);
                String access_token = JWT.create()
                        .withSubject(utente.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles", utente.getRuolo().getNome())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    /**
     * Questo metodo permette di effettuare l' expire del token per effettuare il logout
     *
     * @param request  richiesta Http
     * @param response risposta Http
     * @throws IOException
     */
    @GetMapping("token/expire")
    public void expireToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                Utente utente = service.getUtenteByEmail(email);
                String access_token = JWT.create()
                        .withSubject(utente.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles", utente.getRuolo().getNome())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
