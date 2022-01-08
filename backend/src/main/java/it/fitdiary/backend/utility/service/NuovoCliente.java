package it.fitdiary.backend.utility.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class NuovoCliente {
    /**
     * int 50.
     */
    public static final int INT50 = 50;
    /**
     * nome cliente.
     */
    @NotNull(message = "Il nome non può essere nullo")
    @Column(length = INT50)
    @Size(min = 1, max = INT50, message = "Lunghezza nome non valida")
    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;
    /**
     * cognome cliente.
     */
    @NotNull(message = "Il cognome non può essere nullo")
    @Column(length = INT50)
    @Size(min = 1, max = INT50, message = "Lunghezza cognome non valida")
    @NotBlank(message = "Il cognome non può essere vuoto")
    private String cognome;
    /**
     * email cliente.
     */
    @NotNull(message = "L'email non può essere nulla")
    @Column(length = INT50, unique = true)
    @Size(min = 1, max = INT50, message = "Lunghezza email non valida")
    @Email(message = "Formato email non valida")
    private String email;
}
