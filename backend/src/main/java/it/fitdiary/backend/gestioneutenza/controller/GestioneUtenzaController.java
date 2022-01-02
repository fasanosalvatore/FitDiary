package it.fitdiary.backend.gestioneutenza.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.utility.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.security.Principal;
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

    @PostMapping("preparatore")
    ResponseEntity<Object> registrazione(@RequestBody Utente utente) {
        Utente newUtente = null;
        try {
            newUtente = service.registrazione(utente);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        Stripe.apiKey = "sk_test_Cp8braM9kf167P3ya1gaFSbZ00aZ3YfXjz";
        Map<String, Object> params = new HashMap<>();
        params.put("email", utente.getEmail());
        params.put("name", utente.getNome() + " " + utente.getCognome());
        Customer customer = null;
        try {
            customer = Customer.create(params);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_GATEWAY,
                    "la comunicazione con stripe ha avuto un errore");
        }
        Map<String, Object> response = new HashMap<>();
        System.out.println("customerId" + customer.getId());
        response.put("customerId", customer.getId());
        response.put("utente", newUtente);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, "response",
                response);
    }

    /**
     * Questo metodo prende i parametri inseriti nel body della richiesta http e li passa al service
     *
     *
     * @param utente rappresenta l'insieme dei dati personali di un utente
     * @return utente rappresenta l'utente con i nuovi dati inserito nel database
     */
    @PostMapping("cliente")
    ResponseEntity<Object> inserimentoDatiPersonaliCliente(@Valid @RequestBody Utente utente) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Principal principal = request.getUserPrincipal();
        String emailCliente = principal.getName();
        try {
            Utente newUtente = service.inserimentoDatiPersonaliCliente(utente,emailCliente);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, "utente",
                    newUtente);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Questo metodo prende i parametri inseriti per modificare nel body della richiesta http e li passa al service
     * @param  email
     * @param utente rappresenta l'insieme dei dati personali di un utente
     * @return utente rappresenta l'utente con i nuovi dati inserito nel database
     */
    @PutMapping("cliente")
    ResponseEntity<Object> modificaDatiPersonaliCliente(@Valid
                                                        @RequestBody Utente utente) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Principal principal = request.getUserPrincipal();
        String emailCliente = principal.getName();
        try {
            Utente newUtente = service.modificaDatiPersonaliCliente(utente,emailCliente);
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
    ResponseEntity<Object> modificaDatiPersonaliPreparatore(@Valid @RequestBody Utente preparatore) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Principal principal = request.getUserPrincipal();
        String emailPreparatore = principal.getName();
        try {
            Utente updatedPrepartore = service.modificaDatiPersonaliPreparatore(preparatore, emailPreparatore);
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
    public void expireToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    /**
     * Questo metodo permette di creare un cliente da parte di un preparatore
     *
     * @param nome    nome del cliente
     * @param cognome cognome del cliente
     * @param email   email del cliente
     * @return cliente creato
     */
    @PostMapping("createcliente")
    ResponseEntity<Object> iscrizioneCliente(@RequestParam(name = "nome")
                                             @NotNull(message = "Il nome non può essere nullo")
                                             @Size(min = 1, max = 50, message = "Lunghezza nome non valida")
                                             @NotBlank(message = "Il nome non può essere vuoto") String nome,
                                             @NotNull(message = "Il cognome non può essere nullo")
                                             @Size(min = 1, max = 50, message = "Lunghezza cognome non valida")
                                             @NotBlank(message = "Il cognome non può essere vuoto")
                                             @RequestParam(name = "cognome") String cognome,
                                             @NotNull(message = "L'email non può essere nulla")
                                             @Size(min = 1, max = 50, message = "Lunghezza email non valida")
                                             @Email(message = "Formato email non valida")
                                             @RequestParam(name = "email") String email) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        Principal principal = request.getUserPrincipal();
        String emailPreparatore = principal.getName();
        Utente newCliente = null;
        try {
            newCliente = service.inserisciCliente(nome, cognome, email, emailPreparatore);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseHandler.generateResponse(HttpStatus.CREATED, "cliente",
                newCliente);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();

        e.getConstraintViolations().forEach(constraintViolation -> errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));

        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "fail"
                , errors);
    }

    /**
     * Questo metodo permette di visualizzare il profilo dell'utente autenticato
     *
     * @param request richiesta Http
     * @return Utente autenticato
     * @throws IOException
     */
    @GetMapping("profilo")
    public ResponseEntity<Object> visualizzaProfilo(HttpServletRequest request) throws IOException {
        Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        try {
            Utente utente = service.getUtenteByEmail(email);
            return ResponseHandler.generateResponse(HttpStatus.OK, "utente", utente
            );
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }


    }
}
