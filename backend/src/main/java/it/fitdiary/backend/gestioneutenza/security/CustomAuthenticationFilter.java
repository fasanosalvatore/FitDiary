package it.fitdiary.backend.gestioneutenza.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {

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
     * Costante per valore intero di 30.
     */
    public static final int INT30 = 30;
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        log.info("Email is: {}", email);
        log.info("Password is: {}", password);
        UsernamePasswordAuthenticationToken authenticationToken =
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
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authentication)
            throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(
                        new Date(System.currentTimeMillis()
                                + INT10 * INT60 * INT1000))
                .withIssuer(request.getRequestURI().toString())
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(
                        new Date(System.currentTimeMillis()
                                + INT30 * INT60 * INT1000))
                .withIssuer(request.getRequestURI().toString())
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    /**
     * Questo metodo gestisce un'autenticazione fallita.
     *
     * @param request  richiesta Http
     * @param response risposta Http
     * @param failed   eccezione dell'autenticazione
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest
                                                          request,
                                              final HttpServletResponse
                                                      response,
                                              final AuthenticationException
                                                          failed)
            throws IOException, ServletException {
        log.info("Autenticazione fallita");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader("error", "Autenticazione fallita");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter()
                .write("{\"message\": \"Autenticazione fallita\", "
                        + "\"status\": \"error\"}");
    }
}
