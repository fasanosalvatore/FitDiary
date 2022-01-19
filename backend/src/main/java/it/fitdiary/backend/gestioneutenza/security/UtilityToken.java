package it.fitdiary.backend.gestioneutenza.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.fitdiary.backend.utility.service.FitDiaryUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
public class UtilityToken {

    /**
     * Access Token expiring time in ms.
     */
    public static final int ACCESS_TOKEN_MS = 1000 * 60 * 30;
    /**
     * Refresh Token expiring time in ms.
     */
    public static final int REFRESH_TOKEN_MS = 1000 * 60 * 60 * 24 * 7;

    /** L'algoritmo per la costruzione del token. */
    private Algorithm algorithm;
    /** JWTVerifier per la verifica del token. */
    private JWTVerifier verifier;
    /** DecodedJWT per la decodifica del token. */
    private DecodedJWT jwt;
    /** id del soggetto del token. */
    private String id;
    /** email presente nel token. */
    private String email;
    /** authorities presenti nel token, come i ruoli. */
    private ArrayList<SimpleGrantedAuthority> authorities;
    /** il tempo in millisecondi dopo il quale il token sara scaduto. */
    private long accessTokenExpire;
    /** il tempo in millisecondi dopo il quale il token sara scaduto. */
    private long refreshTokenExpire;
    /** indica se il token è valido. */
    private boolean valid;
    /** indica se il token è espirato. */
    private boolean expired;

    /**
     * @return il tempo in millisecondi dopo il quale il token sara scaduto.
     */
    public final long getAccessTokenExpire() {
        return accessTokenExpire;
    }

    /**
     * @return il tempo in millisecondi dopo il quale il token sara scaduto.
     */
    public final long getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    /**
     * @param user l'utente per generare il token.
     */
    public UtilityToken(final FitDiaryUserDetails user) {
        this("");
        id = user.getId().toString();
        email = user.getEmail();
        user.getAuthorities().forEach(g -> {
            authorities.add(new SimpleGrantedAuthority(g.getAuthority()));
        });
    }

    /**
     * @return ritorna se il token è valido o meno.
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @return ritorna se il token è espirato.
     */
    public boolean isExpired() {
        return expired;
    }

    /**
     * @param token il token in formato stringa per inizializzare l'utility.
     */
    public UtilityToken(final String token) {
        algorithm = Algorithm.HMAC256("secret".getBytes());
        verifier = JWT.require(algorithm).build();
        authorities = new ArrayList<>();
        if (token == null || token.equals("")) {
            valid = false;
            expired = true;
        } else {
            try {
                jwt = verifier.verify(token);
                id = jwt.getSubject();
                email = jwt.getClaim("email").asString();
                String[] roles = jwt.getClaim("roles").asArray(String.class);
                authorities = new ArrayList<SimpleGrantedAuthority>();
                stream(roles).forEach(role ->
                        authorities.add(new SimpleGrantedAuthority(role))
                );
                valid = true;
                expired = false;
            } catch (TokenExpiredException e) {
                expired = true;
                valid = false;
            } catch (JWTVerificationException e) {
                valid = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return il soggetto/id presente nel token.
     */
    public String getSubject() {
        if (isValid() && !isExpired()) {
            return jwt.getSubject();
        }
        return null;
    }

    /**
     * @return le authorities presenti nel token, es. i ruoli.
     */
    public ArrayList<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * @param request  la richiesta da cui generare il token.
     * @param response la risposta in cui inserire come cookie il token
     *                 generato.
     */
    public void generateNewToken(
            final HttpServletRequest request,
            final HttpServletResponse response) {

        var accessTokenCookie =
                new Cookie("accessToken",
                        createToken(request, true)
                );
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);

        var refreshTokenCookie =
                new Cookie("refreshToken",
                        createToken(request, false)
                );
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        refreshTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        valid = true;
        expired = false;
    }


    /**
     * @param request la richiesta da cui generare il token.
     * @param isAccessToken indica se è un accessToken o un refreshToken.
     * @return ritorna il token creato come stringa.
     */
    public String createToken(final HttpServletRequest request,
                              final boolean isAccessToken) {
        var expire = getTokenExpire(isAccessToken);
        if (isAccessToken) {
            accessTokenExpire = expire;
        } else {
            refreshTokenExpire = expire;
        }
        return JWT.create()
                  .withSubject(id)
                  .withClaim("email", email)
                  .withExpiresAt(new Date(expire))
                  .withIssuer(request.getRequestURI())
                  .withClaim("roles",
                          authorities.stream()
                                     .map(GrantedAuthority::getAuthority)
                                     .collect(Collectors.toList()))
                  .sign(algorithm);
    }

    private long getTokenExpire(final boolean isAccessToken) {
        return System.currentTimeMillis()
                + (isAccessToken ? ACCESS_TOKEN_MS : REFRESH_TOKEN_MS);
    }
}
