package it.fitdiary.backend.gestioneutenza.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.fitdiary.backend.utility.ResponseHandler;
import it.fitdiary.backend.utility.service.FitDiaryUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static it.fitdiary.backend.utility.service.FitDiaryUserDetails.createUserMap;

@Slf4j
public class CustomAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {

    /**
     * enviroment.
     */
    private Environment environment;
    /**
     * AuthenticationManager usato per l'autenticazione.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * @param authManager AuthenticationManager
     * @param env Environment
     */
    public CustomAuthenticationFilter(
            final AuthenticationManager authManager,
            final Environment env) {
        this.authenticationManager = authManager;
        this.environment = env;
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
        var utilityToken = new UtilityToken(user);
        utilityToken.setEnviroment(environment);
        utilityToken.generateNewToken(request, response);
        var respMap = Map.of(
                "userInfo", createUserMap(user),
                "accessTokenExpireAt", utilityToken.getAccessTokenExpire(),
                "refreshTokenExpireAt", utilityToken.getRefreshTokenExpire()
        );
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),
                ResponseHandler.generateResponse(HttpStatus.OK, respMap)
                        .getBody());
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
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        new ObjectMapper().writeValue(response.getOutputStream(),
                ResponseHandler.generateResponse(HttpStatus.UNAUTHORIZED,
                        (Object) "Autenticazione fallita").getBody());
    }
}
