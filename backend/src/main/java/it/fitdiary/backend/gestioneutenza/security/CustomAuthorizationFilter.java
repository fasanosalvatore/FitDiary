package it.fitdiary.backend.gestioneutenza.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.fitdiary.backend.utility.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    /**
     * Questo metodo si occupa di controllare l'autorizzazione di un utente per
     * effettuare le richieste HTTP.
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getServletPath().equals("/api/v1/utenti/login")) {
            filterChain.doFilter(request, response);
        } else {
            try {
                String refreshToken = "";
                String accessToken = "";
                if (WebUtils.getCookie(request, "accessToken") != null) {
                    accessToken = Objects.requireNonNull(
                            WebUtils.getCookie(request, "accessToken")
                    ).getValue();
                }
                if (WebUtils.getCookie(request, "refreshToken") != null) {
                    refreshToken = Objects.requireNonNull(
                            WebUtils.getCookie(request, "refreshToken")
                    ).getValue();
                }
                var utilityToken = new UtilityToken(accessToken);
                if (utilityToken.isExpired()) {
                    utilityToken = new UtilityToken(refreshToken);
                    if (utilityToken.isValid()) {
                        utilityToken.generateNewToken(request, response);
                    } else {
                        throw new TokenExpiredException("Session expired");
                    }
                }
                var token = new UsernamePasswordAuthenticationToken(
                        utilityToken.getSubject(), null,
                        utilityToken.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(token);
                filterChain.doFilter(request, response);
            } catch (NestedServletException e) {
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper()
                        .writeValue(response.getOutputStream(),
                                ResponseHandler
                                        .generateResponse(BAD_REQUEST,
                                                (Object) "file di "
                                                        + "dimensioni "
                                                        + "elevate")
                                        .getBody()
                        );
            } catch (Exception e) {
                e.printStackTrace();
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper()
                        .writeValue(response.getOutputStream(),
                                ResponseHandler
                                        .generateResponse(UNAUTHORIZED,
                                                e.getMessage())
                                        .getBody()
                        );
            }
        }
    }
}
