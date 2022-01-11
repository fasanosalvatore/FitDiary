package it.fitdiary.backend.gestioneutenza.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.model.Customer;
import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.service.GestioneUtenzaService;
import it.fitdiary.backend.utility.ResponseHandler;
import it.fitdiary.backend.utility.service.FitDiaryUserDetails;
import it.fitdiary.backend.utility.service.NuovoCliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static it.fitdiary.backend.utility.service.FitDiaryUserDetails.createTokensMap;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


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
     * Access Token expiring time in ms.
     */
    public static final int ACCESS_TOKEN_MS = 1000 * 60 * 30;
    /**
     * Refresh Token expiring time in ms.
     */
    public static final int REFRESH_TOKEN_MS = 1000 * 60 * 60 * 24 * 7;
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
                    (Object)
                    "password non valida");
        }
        Utente newUtente;
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
     * Questo metodo prende i parametri per modoficare un utente inseriti
     * nel body della richiesta http e li passa al service.
     *
     * @param utente rappresenta l'insieme dei dati personali
     *               di un utente
     * @return utente rappresenta l'utente
     * con i nuovi dati inserito nel database
     */
    @PutMapping
    ResponseEntity<Object> modificaDatiPersonali(
            @RequestBody final Utente utente) {
        HttpServletRequest request =
                ((ServletRequestAttributes)
                        Objects.requireNonNull(RequestContextHolder
                                .getRequestAttributes()))
                        .getRequest();
        var idCliente = Long.parseLong(request.getUserPrincipal().getName());
        try {
            Utente newUtente =
                    service.modificaDatiPersonali(idCliente,
                            utente);
            return ResponseHandler.generateResponse(HttpStatus.CREATED,
                    "utente",
                    newUtente);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    (Object) e.getMessage());
        }
    }

    /**
     * Questo metodo permette di effettuare il refresh del token per un utente.
     *
     * @param request  richiesta Http
     * @param response risposta Http
     * @throws IOException eccezione
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
                Algorithm alg = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(alg).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                FitDiaryUserDetails user = service.loadUserByUsername(
                        decodedJWT.getClaim("email").asString());
                var tokens = createTokensMap(
                        request, alg, user, ACCESS_TOKEN_MS,
                        REFRESH_TOKEN_MS, null);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        ResponseHandler.generateResponse(HttpStatus.OK, tokens)
                                .getBody());
            } catch (Exception e) {
                e.printStackTrace();
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        ResponseHandler.generateResponse(
                                        HttpStatus.BAD_REQUEST,
                                        (Object) "Refresh del token fallito")
                                .getBody());
            }
        } else {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            new ObjectMapper().writeValue(response.getOutputStream(),
                    ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                            (Object) "Refresh token mancante").getBody());
        }
    }


    /**
     * Questo metodo permette di effettuare
     * l' expire del token per effettuare il logout.
     *
     * @param request  richiesta Http
     * @param response risposta Http
     * @throws IOException eccezione
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
                Algorithm alg = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(alg).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                FitDiaryUserDetails user = service.loadUserByUsername(
                        decodedJWT.getClaim("email").asString());
                var tokens = createTokensMap(request, alg, user, INT1000,
                        INT1000, null);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        ResponseHandler.generateResponse(HttpStatus.OK, tokens)
                                .getBody());
            } catch (Exception e) {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                new ObjectMapper().writeValue(response.getOutputStream(),
                        ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                                        (Object) "Refresh del token fallito")
                                .getBody());
            }
        } else {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            new ObjectMapper().writeValue(response.getOutputStream(),
                    ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                            (Object) "Refresh token mancante").getBody());
        }
    }

    /**
     * Questo metodo permette di creare un cliente da parte di un preparatore.
     *
     * @param cliente Nuovo cliente con nome, cognome ed email
     * @return cliente creato
     */
    @PostMapping
    ResponseEntity<Object> iscrizioneCliente(
            @RequestBody final NuovoCliente cliente) {
        var request = ((ServletRequestAttributes)
                Objects.requireNonNull(
                        RequestContextHolder.getRequestAttributes()))
                .getRequest();
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
                    (Object) "Errore nei parametri della richiesta");
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

        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                errors);
    }

    /**
     * Questo metodo permette di visualizzare
     * il profilo dell'utente autenticato.
     *
     * @param request richiesta Http
     * @return Utente autenticato
     */
    @GetMapping("profilo")
    public ResponseEntity<Object> visualizzaProfilo(final HttpServletRequest
                                                            request) {
        var idUtente = Long.parseLong(request.getUserPrincipal().getName());
        try {
            Utente utente = service.getById(idUtente);
            return ResponseHandler.generateResponse(HttpStatus.OK, "utente",
                    utente
            );
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                    (Object) e.getMessage());
        }
    }

    /**
     * @param idCliente id utente di cui visualizzare profilo
     * @return Utente di cui voglio visualizzare il profilo
     */
    @GetMapping("{id}")
    public ResponseEntity<Object> visualizzaProfiloUtente(
            @PathVariable("id") final Long idCliente) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes()).getRequest();
        Long idPreparatore = Long.parseLong(
                request.getUserPrincipal().getName());
        Utente preparatore = service.getById(idPreparatore);
        if (!service.existsByPreparatoreAndId(
                preparatore, idCliente)) {
            return ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                    (Object)
                    "Il preparatore non può accedere "
                            + "al profilo di questo cliente");
        }
        Utente cliente = service.getById(idCliente);
        map.put("cliente", cliente);
        map.put("protocollo", cliente.getListaProtocolli());
        map.put("report", cliente.getListaReport());
        return ResponseHandler.generateResponse(HttpStatus.OK, map);
    }


    /**
     * metodo per catturare l'errore HttpMessageNotReadableException.
     *
     * @param ex errore
     * @return messaggio di errore
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMissingRequestBody(
            final HttpMessageNotReadableException ex) {
        return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST,
                (Object)"Errore durante la lettura del body");
    }

    /**
     * restituisce la lista di clienti di un preparatore o admin.
     *
     * @param request richiesta http
     * @return lista clienti
     */
    @GetMapping
    public ResponseEntity<Object> visualizzaListaUtenti(
            final HttpServletRequest
                    request) {
        var idUtente = Long.parseLong(request.getUserPrincipal()
                .getName());
        Utente utente = service.getById(idUtente);
        if (utente.getRuolo().getNome().equals(Ruolo.RUOLOADMIN)) {
            return ResponseHandler.generateResponse(HttpStatus.OK,
                    "utenti",
                   service.visualizzaListaUtenti());
        }
        return ResponseHandler.generateResponse(HttpStatus.OK,
                "clienti",
                utente.getListaClienti());

    }
}
