package it.fitdiary.backend.utility.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class FitDiaryUserDetails extends User implements UserDetails {

    /**
     * Id of the user.
     */
    private Long id;
    /**
     * String of the name.
     */
    private String name;
    /**
     * String of the phoneNumber.
     */
    private String phoneNumber;
    /**
     * String of 1 char representing the gender.
     */
    private String gender;
    /**
     * String of the surname.
     */
    private String surname;
    /**
     * Id of the trainer.
     */
    private long trainer;

    /**
     * @param username    User username.
     * @param password    User password.
     * @param authorities Authorities of the user.
     */
    public FitDiaryUserDetails(final String username,
                               final String password,
                               final Collection<?
                                       extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    /**
     * @return the id of the user.
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the Name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the email of the user.
     */
    public String getEmail() {
        return super.getUsername();
    }

    /**
     * @return the phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return the gender of the user.
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return the trained id of the user.
     */
    public long getTrainer() {
        return trainer;
    }

    /**
     * @return the surname of the user.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param nome of the user.
     */
    public void setName(final String nome) {
        this.name = nome;
    }

    /**
     * @param surn of the user.
     */
    public void setSurname(final String surn) {
        this.surname = surn;
    }

    /**
     * @param phoneNumb of the user.
     */
    public void setPhoneNumber(final String phoneNumb) {
        this.phoneNumber = phoneNumb;
    }

    /**
     * @param gend of the user.
     */
    public void setGender(final String gend) {
        this.gender = gend;
    }

    /**
     * @param trainerId of the user.
     */
    public void setTrainerId(final long trainerId) {
        this.trainer = trainerId;
    }

    /**
     * @param newId of the user
     */
    public void setId(final Long newId) {
        this.id = newId;
    }

    /**
     * @param user Dettagli dell'utente
     * @param request HttpServletRequest
     * @param alg Algoritmo di codifica
     * @param expiresAt Scadenza del token
     * @return JWT token
     */
    public static String createToken(final FitDiaryUserDetails user,
                                     final HttpServletRequest request,
                                     final Algorithm alg,
                                     final long expiresAt) {
        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withExpiresAt(new Date(expiresAt))
                .withIssuer(request.getRequestURI())
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(alg);
    }


    /**
     * @param request HttpServletRequest
     * @param alg Algoritmo di codifica
     * @param user Dettagli dell'utente
     * @param accessTokenMs Durata del token di accesso
     * @param refreshTokenMs Durata del token di refresh
     * @param currentRefreshToken Refresh token attuale
     * @return mappa di informazioni sull'utente per i token
     * @throws IOException
     */
    public static Map<String, Map<String, ? extends Serializable>>
    createTokensMap(final HttpServletRequest request,
                    final Algorithm alg, final FitDiaryUserDetails user,
                    final long accessTokenMs, final long refreshTokenMs,
                    final String currentRefreshToken)
            throws IOException {
        long accessExpireAt = System.currentTimeMillis() + accessTokenMs;
        long refreshExpireAt = System.currentTimeMillis() + refreshTokenMs;
        Map<String, Map<String, ? extends Serializable>> tokens = Map.of(
                "accessToken", Map.of(
                        "token",
                        createToken(user, request, alg, accessExpireAt),
                        "expiresAt", accessExpireAt
                ),
                "refreshToken", Map.of(
                        "token", currentRefreshToken == null
                                ? createToken(user, request, alg,
                                refreshExpireAt) : currentRefreshToken,
                        "expiresAt", refreshExpireAt
                ),
                "userInfo", Map.of(
                        "email", user.getUsername(),
                        "name", user.getName(),
                        "surname", user.getSurname(),
                        "trainerId", user.getTrainer(),
                        "gender", user.getGender() != null
                                ? user.getGender() : "N",
                        "roles", user.getAuthorities()
                                .stream()
                                .map(p -> p.getAuthority())
                                .toArray()
                )
        );
        return tokens;
    }
}
