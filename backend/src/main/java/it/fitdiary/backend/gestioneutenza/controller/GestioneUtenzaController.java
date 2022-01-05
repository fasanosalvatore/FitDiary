package it.fitdiary.backend.gestioneutenza.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.model.Customer;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.utility.ResponseHandler;
import it.fitdiary.backend.utility.service.NuovoCliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;


@RestController
@Slf4j
@RequestMapping(path = "api/v1/utenti")
public class GestioneUtenzaController {

    /**
     * Costante per valore intero di 10.
     */
    public static final int INT10 = 10;
    /**
     * Costante per valore intero di 60.
     */
    public static final int INT60 = 60;
    /**
     * Costante per valore intero di 1000.
     */
    public static final int INT1000 = 1000;
    /**
     * GestioneUtenzaService che si occupa della logica di business.
     */
    private GestioneUtenzaService service;

    /**
     * @param ser GestioneUtenzaService
     */
    @Autowired
    public GestioneUtenzaController(final GestioneUtenzaService ser) {
        this.service = ser;
    }

    /**
     * questa funzione permette di registrare un nuovo preparatore.
     *
     * @param utente nuovo preparatore
     * @return preparatore con l'id o errrori e fail
     */
    @PostMapping("preparatore")
    ResponseEntity<Object> registrazione(@RequestBody final Utente utente) {
        if (!utente.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)"
                + "(?=.*[@$!%*?^#()<>+&.])"
                + "[A-Za-z\\d@$!%*?^#()<>+&.]{8,}$")) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    "password",
                    "password non valida");
        }
        Utente newUtente = null;
        try {
            newUtente = service.registrazione(utente);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
        Stripe.apiKey = "sk_test_Cp8braM9kf167P3ya1gaFSbZ00aZ3YfXjz";
        Map<String, Object> params = new HashMap<>();
        params.put("email", newUtente.getEmail());
        params.put("name", newUtente.getNome() + " " + newUtente.getCognome());
        Customer customer;
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
     * Questo metodo prende i parametri inseriti
     * nel body della richiesta http e li passa al service.
     *
     * @param clienteModificato rappresenta l'insieme dei dati personali
     *                          di un utente
     * @return utente rappresenta l'utente
     * con i nuovi dati inserito nel database
     */
    @PostMapping("cliente")
    ResponseEntity<Object> inserimentoDatiPersonaliCliente(
            @RequestBody final Utente clienteModificato) {
        HttpServletRequest request =
                ((ServletRequestAttributes)
                        RequestContextHolder
                                .getRequestAttributes()).getRequest();
        var idCliente = Long.parseLong(request.getUserPrincipal().getName());
        try {
            Utente newUtente =
                    service.inserimentoDatiPersonaliCliente(idCliente,
                            clienteModificato);
            return ResponseHandler.generateResponse(HttpStatus.CREATED,
                    "utente",
                    newUtente);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
    }

    /**
     * Questo metodo prende i parametri inseriti per
     * modificare nel body della richiesta http e li passa al service.
     *
     * @param clienteModificato rappresenta l'insieme dei dati personali
     *                          di un utente
     * @return utente rappresenta l'utente
     * con i nuovi dati inserito nel database
     */
    @PutMapping("cliente")
    ResponseEntity<Object> modificaDatiPersonaliCliente(
            @RequestBody final Utente clienteModificato) {
        HttpServletRequest request =
                ((ServletRequestAttributes)
                        RequestContextHolder
                                .getRequestAttributes())
                        .getRequest();
        var idCliente = Long.parseLong(request.getUserPrincipal().getName());
        try {
            Utente newUtente =
                    service.modificaDatiPersonaliCliente(idCliente,
                            clienteModificato);
            return ResponseHandler.generateResponse(HttpStatus.CREATED,
                    "utente",
                    newUtente);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
    }

    /**
     * Questo metodo prende in input un preparatore di tipo Utente
     * contiene i dati da modificare dal body
     * della richiesta http e li passa al service.
     *
     * @param preparatore rappersenta l' insieme
     *                    dei dati persoanli da aggoirnare di un preparatore
     * @return upadtedPreparatore rappresenta l' utente
     * con i nuovi dati inseriti nel database
     */
    @PutMapping("preparatore")
    ResponseEntity<Object> modificaDatiPersonaliPreparatore(
            @Valid @RequestBody final Utente preparatore) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes())
                        .getRequest();
        var idPreparatore =
                Long.parseLong(request.getUserPrincipal().getName());
        try {
            Utente updatedPrepartore =
                    service.modificaDatiPersonaliPreparatore(
                            idPreparatore, preparatore
                    );
            return ResponseHandler.generateResponse(HttpStatus.CREATED,
                    "preparatore",
                    updatedPrepartore);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
    }

    /**
     * Questo metodo permette di effettuare il refresh del token per un utente.
     *
     * @param request  richiesta Http
     * @param response risposta Http
     * @throws IOException
     */
    @GetMapping("token/refresh")
    public void refreshToken(final HttpServletRequest
                                     request,
                             final HttpServletResponse
                                     response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null
                && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken =
                        authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                Long id = Long.parseLong(decodedJWT.getSubject());
                Utente utente = service.getById(id);
                var roles = new ArrayList<>();
                roles.add(utente.getRuolo().getNome());
                String accessToken = JWT.create()
                        .withSubject(utente.getId().toString())
                        .withExpiresAt(new Date(
                                System.currentTimeMillis() + INT10 * INT60
                                        * INT1000))
                        .withClaim("email", utente.getEmail())
                        .withIssuer(request.getRequestURI())
                        .withClaim("roles", roles)
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        error);
            }
        } else {
            response.setHeader("error", "Refresh token mancante");
            response.setStatus(FORBIDDEN.value());
            Map<String, String> error = new HashMap<>();
            error.put("error_message", "Refresh token mancante");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }

    /**
     * Questo metodo permette di effettuare
     * l' expire del token per effettuare il logout.
     *
     * @param request  richiesta Http
     * @param response risposta Http
     * @throws IOException
     */
    @GetMapping("token/expire")
    public void expireToken(final HttpServletRequest
                                    request,
                            final HttpServletResponse
                                    response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null
                && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken =
                        authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                Long id = Long.parseLong(decodedJWT.getSubject());
                Utente utente = service.getById(id);
                String accessToken = JWT.create()
                        .withSubject(utente.getId().toString())
                        .withExpiresAt(
                                new Date(System.currentTimeMillis() + INT1000))
                        .withClaim("email", utente.getEmail())
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles", utente.getRuolo().getNome())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    /**
     * Questo metodo permette di creare un cliente da parte di un preparatore.
     *
     * @param cliente Nuovo cliente con nome, cognome ed email
     * @return cliente creato
     */
    @PostMapping("createcliente")
    ResponseEntity<Object> iscrizioneCliente(
            @RequestBody final NuovoCliente cliente) {
        var request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        var idPreparatore =
                Long.parseLong(request.getUserPrincipal().getName());
        String email = cliente.getEmail();
        String nome = cliente.getNome();
        String cognome = cliente.getCognome();
        try {
            Utente newCliente =
                    service.inserisciCliente(idPreparatore, nome, cognome,
                            email);
            return ResponseHandler.generateResponse(HttpStatus.CREATED,
                    "cliente",
                    newCliente);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    "Errore nei parametri della richiesta");
        } catch (MailException e) {
            log.error(e.getMessage());
            return ResponseHandler.generateResponse(HttpStatus.BAD_GATEWAY,
                    "Errore durante l'invio della mail");
        }
    }

    /**
     * @param e ConstraintViolationException
     * @return Risposta formattata
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            final ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();

        e.getConstraintViolations().forEach(constraintViolation -> errors.put(
                constraintViolation.getPropertyPath().toString(),
                constraintViolation.getMessage()));

        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, "fail",
                errors);
    }

    /**
     * Questo metodo permette di visualizzare
     * il profilo dell'utente autenticato.
     *
     * @param request richiesta Http
     * @return Utente autenticato
     * @throws IOException
     */
    @GetMapping("profilo")
    public ResponseEntity<Object> visualizzaProfilo(final HttpServletRequest
                                                            request)
            throws IOException {
        var idUtente = Long.parseLong(request.getUserPrincipal().getName());
        try {
            Utente utente = service.getById(idUtente);
            return ResponseHandler.generateResponse(HttpStatus.OK, "utente",
                    utente
            );
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }


    }
}
