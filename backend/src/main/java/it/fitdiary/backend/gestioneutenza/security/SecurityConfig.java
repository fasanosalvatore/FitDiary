package it.fitdiary.backend.gestioneutenza.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Ruolo Admin.
     */
    public static final String ADMIN = "Preparatore";
    /**
     * Ruolo Preparatore.
     */
    public static final String PREPARATORE = "Preparatore";
    /**
     * Ruolo Cliente.
     */
    public static final String CLIENTE = "CLIENTE";
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
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl(
                "/api/v1/utenti/login");
        http.csrf().disable()
                .cors().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(POST, "/api/v1/utenti"
                                + "/preparatore"
                                + "/**", "/api/v1/abbonamento/acquista/**",
                        "/api/v1/utenti/login").permitAll()
                .antMatchers(POST, "/api/v1/utenti/cliente/**")
                .hasAuthority(CLIENTE)
                .antMatchers(PUT, "/api/v1/utenti/cliente/**")
                .hasAuthority(CLIENTE)
                .antMatchers("/api/v1/utenti/preparatore/**")
                .hasAuthority(PREPARATORE)
                .antMatchers("/api/v1/utenti/createcliente/**")
                .hasAuthority(PREPARATORE)
                .antMatchers("/api/v1/utenti/profilo/**").authenticated()
                .antMatchers("/api/v1/utenti/login/**",
                        "/api/v1/utenti/token/refresh/**",
                        "/api/v1/utenti/token/expire/**",
                        "/api/v1/utenti/preparatore/**",
                        "/api/v1/abbonamento/acquista/**").permitAll()
                .anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((e) -> e.accessDeniedHandler(
                        (request, response, accessDeniedException) -> {
                            response.setStatus(
                                    HttpStatus.UNAUTHORIZED.value());
                            response.setHeader("error",
                                    "Autorizzazione fallita");
                            response.setContentType(
                                    MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter()
                                    .write("{\"message\": "
                                            + "\"Non sei autorizzato "
                                            + "per questa funzionalità\", "
                                            + "\"status\": \"error\"}");
                        })
                );
    }

    /**
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
