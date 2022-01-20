package it.fitdiary.backend.gestioneutenza.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;
    /**
     * Ruolo Admin.
     */
    public static final String ADMIN = "Admin";
    /**
     * Ruolo Preparatore.
     */
    public static final String PREPARATORE = "Preparatore";
    /**
     * Ruolo Cliente.
     */
    public static final String CLIENTE = "Cliente";
    /**
     * UserDetailsService utilizzato per l'autenticazione.
     */
    private final UserDetailsService userDetailsService;
    /**
     * BCryptPasswordEncoder utilizzato per codificare le password.
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * @param auth AuthenticationManagerBuilder
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * @param http HttpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        /* Routes:
            POST    /utenti/acquista      //guest acquista abbonamento
            POST    /abbonamento/create   //guest registra preparatore
            GET     /utenti/token/refresh //utente refresha il token
            GET     /utenti/token/refresh //utente espira il token
            GET     /utenti/profilo       //utente visualizza il profilo
            POST    /utenti/cliente       //cliente inserisce dati cliente
            PUT     /utenti/cliente       //cliente modifica dati cliente
            PUT     /utenti/preparatore   //preparatore modifica dati
            POST    /utenti/createcliente //preparatore crea cliente
            POST    /abbonamento/acquista //preparatore acquista abbonamento
         */
        //Disabilita CSRF Token per i form
        http.csrf().disable();
        //Abilita Cross origin requests
        http.cors().configurationSource(this::corsConfigurer);
        //Abilita avvio senza sessione (JWT Only)
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        //Creazione dei filtri per le richieste
        var loginFilter =
                new CustomAuthenticationFilter(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/api/v1/utenti/login");
        //Applicazione dei filtri alle richieste
        http
                //filtro Login
                .addFilter(loginFilter)
                //filtro JWT
                .addFilterBefore(new CustomAuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                //handling accesso negato/JWT Token
                .exceptionHandling(e ->
                        e.accessDeniedHandler(this::handleAccessDenied));

        //Sicurezza Routes
        http
                //Routes pubbliche
                .authorizeRequests()
                .antMatchers(POST,
                        "/api/v1/utenti/login",
                        "/api/v1/utenti/preparatore",
                        "/api/v1/abbonamento/acquista"
                ).permitAll()

                //Routes per qualsiasi Utente Autenticato
                .and().authorizeRequests()
                .antMatchers(GET,
                        "/api/v1/utenti/token/expires",
                        "/api/v1/utenti/profilo"
                ).authenticated()
                .antMatchers(PUT, "/api/v1/utenti").authenticated()

                //Routes Cliente con Ruolo
                .and().authorizeRequests()
                .antMatchers(POST, "/api/v1/utenti/cliente")
                .hasAuthority(CLIENTE)
                .antMatchers(PUT, "/api/v1/utenti/cliente")
                .hasAuthority(CLIENTE)
                .antMatchers(POST, "/api/v1/utenti/reports")
                .hasAuthority(CLIENTE)

                //Routes Preparatore e Admin con Ruolo
                .antMatchers(GET, "/api/v1/utenti")
                .hasAnyAuthority(PREPARATORE, ADMIN)

                //Routes Admin con Ruolo
                .antMatchers(DELETE, "/api/v1/utenti/**")
                .hasAuthority(ADMIN)


                //Routes Preparatore e Cliente con Ruolo
                .antMatchers(GET, "/api/v1/protocolli/**")
                .hasAnyAuthority(PREPARATORE, CLIENTE)
                .antMatchers(GET, "/api/v1/protocolli")
                .hasAnyAuthority(PREPARATORE, CLIENTE)
                .antMatchers(GET, "/api/v1/reports")
                .hasAnyAuthority(PREPARATORE, CLIENTE)
                .antMatchers(GET, "/api/v1/reports/**")
                .hasAnyAuthority(PREPARATORE, CLIENTE)
                .antMatchers(GET, "/api/v1/reports/search")
                .hasAnyAuthority(PREPARATORE, CLIENTE)

                //Routes Preparatore con Ruolo
                .and().authorizeRequests()
                .antMatchers(PUT, "/api/v1/utenti/preparatore")
                .hasAuthority(PREPARATORE)
                .antMatchers(POST, "/api/v1/protocolli")
                .hasAuthority(PREPARATORE)
                .antMatchers(PUT, "/api/v1/protocolli/**")
                .hasAuthority(PREPARATORE)
                .antMatchers(POST, "/api/v1/utenti")
                .hasAuthority(PREPARATORE)
                .antMatchers(GET, "/api/v1/utenti/**")
                .hasAuthority(PREPARATORE)
                .antMatchers(PUT, "/api/v1/utenti/**")
                .hasAuthority(PREPARATORE)
                .anyRequest().authenticated();
    }

    private CorsConfiguration corsConfigurer(final HttpServletRequest request) {
        var corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(List.of("*"));
        if (env.getActiveProfiles()[0] != "dev") {
            System.out.println("Setting allowed origin on prod");
            corsConfig.setAllowedOrigins(
                    List.of("https://fitdiary.it",
                            "https://api.fitdiary.it",
                            "https://www.fitdiary.it"));
            corsConfig.setAllowCredentials(true);
        } else {
            corsConfig.setAllowedOrigins(List.of("*"));
        }
        corsConfig.setAllowedMethods(List.of("*"));
        return corsConfig;
    }

    private void handleAccessDenied(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final AccessDeniedException exception)
            throws IOException {
        //TODO usare Responsehandler JSend Convention
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader("error", "Autorizzazione fallita");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"message\": " + "\"Non sei autorizzato "
                + "per questa funzionalità\", " + "\"status\": \"error\"}");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(POST,
                "/api/v1/utenti/preparatore",
                "/api/v1/abbonamento/acquista"
        );
    }
}
