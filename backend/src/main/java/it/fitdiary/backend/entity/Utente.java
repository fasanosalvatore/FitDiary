package it.fitdiary.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Utente {
    /**
     * Costante per valore intero di 50.
     */
    public static final int MAX_NAME_LENGTH = 50;
    /**
     * Costante per valore intero di 255.
     */
    public static final int MAX_PASSWORD_LENGTH = 255;
    /**
     * Costante per valore intero di 8.
     */
    public static final int MIN_PASSWORD_LENGTH = 8;
    /**
     * Costante per valore intero di 15.
     */
    public static final int MAX_PHONE_LENGTH = 15;
    /**
     * Costante per valore intero di 4.
     */
    public static final int MIN_PHONE_LENGTH = 4;
    /**
     * Costante per valore intero di 1.
     */
    public static final int MIN_NAME_LENGTH = 1;
    /**
     * Costante per valore intero di 5.
     */
    public static final int CAP_LENGTH = 5;
    /**
     * Costante per valore intero di 20.
     */
    public static final int MAX_CITY_LENGTH = 20;
    /**
     * Lunghezza campo sesso.
     */
    public static final int SEX_LENGTH = 1;
    /**
     * Id dell'utente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nome dell'utente.
     */
    @NotNull(message = "Il nome non può essere nullo")
    @Column(length = MAX_NAME_LENGTH)
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH,
            message = "Lunghezza nome non valida")
    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;
    /**
     * Cognome dell'utente.
     */
    @NotNull(message = "Il cognome non può essere nullo")
    @Column(length = MAX_NAME_LENGTH)
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH,
            message = "Lunghezza cognome non valida")
    @NotBlank(message = "Il cognome non può essere vuoto")
    private String cognome;
    /**
     * Email dell'utente.
     */
    @NotNull(message = "L'email non può essere nulla")
    @Column(length = MAX_NAME_LENGTH, unique = true)
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH,
            message = "Lunghezza email non valida")
    @Email(message = "Formato email non valida")
    private String email;
    /**
     * Password dell'utente.
     */
    @NotNull(message = "La password non può essere nulla")
    @Column(length = MAX_PASSWORD_LENGTH)
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH,
            message = "Lunghezza password non valida")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     * Indica se un utente è attivo.
     */
    @NotNull(message = "Attivo non può essere nullo")
    private Boolean attivo;
    /**
     * Data di nascita dell'utente.
     */
    @NotNull(message = "La data di nascita non può essere nulla")
    @Column(name = "data_nascita")
    @Past(message = "La data di nascita non può essere successiva "
            + "o coincidente alla data odierna")
    private LocalDate dataNascita = LocalDate.parse("1990-01-01");
    /**
     * Sesso dell'utente.
     */
    @Column(length = SEX_LENGTH)
    @Size(min = SEX_LENGTH, max = SEX_LENGTH,
            message = "Lunghezza sesso non valida")
    private String sesso;
    /**
     * Numero di telefono dell'utente.
     */
    @Column(length = MAX_PHONE_LENGTH)
    @Size(min = MIN_PHONE_LENGTH, max = MAX_PHONE_LENGTH,
            message = "Lunghezza telefono non valida")
    @Pattern(regexp = "^[+03][0-9]{3,14}",
            message = "Formato telefono non valido")
    private String telefono;
    /**
     * Via dell'utente.
     */
    @Column(length = MAX_NAME_LENGTH)
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH,
            message = "Lunghezza via non valida")
    private String via;
    /**
     * Cap dell'utente.
     */
    @Column(length = CAP_LENGTH)
    @Size(min = CAP_LENGTH, max = CAP_LENGTH,
            message = "Lunghezza cap non valida")
    @Pattern(regexp = "[0-9]{5}", message = "Formato cap non valido")
    private String cap;
    /**
     * Città dell'utente.
     */
    @Column(length = MAX_CITY_LENGTH)
    @Size(min = MIN_NAME_LENGTH, max = MAX_CITY_LENGTH,
            message = "Lunghezza città non valida")
    private String citta;
    /**
     * Preparatore associato ad un utente.
     */
    @ManyToOne
    @JoinColumn(name = "preparatore_id")
    private Utente preparatore;
    /**
     * Ruolo associato ad un utente. Può essere CLIENTE, PREPARATORE O ADMIN.
     */
    @NotNull(message = "Il ruolo non può essere nullo")
    @ManyToOne
    @JoinColumn(name = "ruolo_id")
    private Ruolo ruolo;
    /**
     * Lista di protocolli associati ad un utente.
     */
    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Protocollo> listaProtocolli;
    /**
     * Lista di clienti associati ad un utente.
     */
    @OneToMany(mappedBy = "preparatore")
    @JsonIgnore
    private List<Utente> listaClienti;
    /**
     * Lista di report associati ad un utente.
     */
    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Report> listaReport;

    /**
     * La data creazione della tupla.
     */
    @Column(name = "data_creazione", updatable = false, columnDefinition = "")
    @CreationTimestamp
    private LocalDateTime dataCreazione;

    /**
     * La data aggiornamento della tupla.
     */
    @Column(name = "data_aggiornamento", columnDefinition = "")
    @UpdateTimestamp
    private LocalDateTime dataAggiornamento;

    @Override
    public final String toString() {
        return "Utente{"
                + "id=" + id
                + ", nome='" + nome + '\''
                + ", cognome='" + cognome + '\''
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", attivo=" + attivo
                + ", dataNascita=" + dataNascita
                + ", dataCreazione=" + getDataCreazione()
                + ", dataAggiornamento=" + getDataAggiornamento()
                + ", sesso='" + sesso + '\''
                + ", telefono='" + telefono + '\''
                + ", via='" + via + '\''
                + ", cap='" + cap + '\''
                + ", citta='" + citta + '\''
                + '}';
    }
}
