package it.fitdiary.backend.gestioneutenza.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.fitdiary.backend.utility.ResponseHandler;
import it.fitdiary.backend.utility.service.FitDiaryUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {
    /**
     * Access Token expiring time in ms.
     */
    public static final int ACCESS_TOKEN_MS = 600000;
    /**
     * Refresh Token expiring time in ms.
     */
    public static final int REFRESH_TOKEN_MS = 1800000;
    /**
     * AuthenticationManager usato per l'autenticazione.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * @param authManager AuthenticationManager
     */
    public CustomAuthenticationFilter(
            final AuthenticationManager authManager) {
        this.authenticationManager = authManager;
    }


    /**
     * Questo metodo si occupa di tentare l'autenticazione di un utente.
     *
     * @param request  richiesta Http
     * @param response risposta Http
     * @return
     * @throws AuthenticationException
     */

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest
                                                        request,
                                                final HttpServletResponse
                                                        response)
            throws AuthenticationException {
        var email = request.getParameter("email");
        var password = request.getParameter("password");

        log.info("Email is: {}", email);
        log.info("Password is: {}", password);
        var authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * Questo metodo gestisce un'autenticazione avvenuta con successo.
     *
     * @param request        richiesta Http
     * @param response       risposta Http
     * @param chain          catena di filtri
     * @param authentication autenticazione
     * @throws IOException
     */
    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authentication)
            throws IOException {
        var user = (FitDiaryUserDetails) authentication.getPrincipal();
        var alg = Algorithm.HMAC256("secret".getBytes());
        long accessExpireAt = System.currentTimeMillis() + ACCESS_TOKEN_MS;
        long refreshExpireAt = System.currentTimeMillis() + REFRESH_TOKEN_MS;
        var tokens = Map.of(
                "accessToken", Map.of(
                        "token",
                        createToken(user, request, alg, accessExpireAt),
                        "expiresAt", accessExpireAt
                ),
                "refreshToken", Map.of(
                        "token",
                        createToken(user, request, alg, refreshExpireAt),
                        "expiresAt", refreshExpireAt
                ),
                "userInfo", Map.of(
                        "email", user.getUsername(),
                        "name", user.getName(),
                        "surname", user.getSurname(),
                        "phoneNumber", user.getPhoneNumber(),
                        "trainerId", user.getTrainer(),
                        "gender", user.getGender(),
                        "roles", user.getAuthorities()
                                .stream()
                                .map(p -> p.getAuthority())
                                .toArray()
                )
        );
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),
                ResponseHandler.generateResponse(HttpStatus.OK, tokens));
    }

    private String createToken(final FitDiaryUserDetails user,
                               final HttpServletRequest request,
                               final Algorithm alg,
                               final long expiresAt) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(expiresAt))
                .withIssuer(request.getRequestURI())
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(alg);
    }

    /**
     * Questo metodo gestisce un'autenticazione fallita.
     *
     * @param request  richiesta Http
     * @param response risposta Http
     * @param failed   eccezione dell'autenticazione
     * @throws IOException
     */
    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest
                                                      request,
                                              final HttpServletResponse
                                                      response,
                                              final AuthenticationException
                                                      failed)
            throws IOException {
        log.info("Autenticazione fallita");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader("error", "Autenticazione fallita");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter()
                .write("{\"message\": \"Autenticazione fallita\", "
                        + "\"status\": \"error\"}");
    }
}
