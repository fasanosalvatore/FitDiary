package it.fitdiary.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Utente {
    /**
     * Costante per valore intero di 50.
     */
    public static final int INT50 = 50;
    /**
     * Costante per valore intero di 255.
     */
    public static final int INT255 = 255;
    /**
     * Costante per valore intero di 8.
     */
    public static final int INT8 = 8;
    /**
     * Costante per valore intero di 15.
     */
    public static final int INT15 = 15;
    /**
     * Costante per valore intero di 4.
     */
    public static final int INT4 = 4;
    /**
     * Costante per valore intero di 1.
     */
    public static final int INT1 = 1;
    /**
     * Costante per valore intero di 5.
     */
    public static final int INT5 = 5;
    /**
     * Costante per valore intero di 20.
     */
    public static final int INT20 = 20;
    /**
     * Id dell'utente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Nome dell'utente.
     */
    @NotNull(message = "Il nome non può essere nullo")
    @Column(length = INT50)
    @Size(min = INT1, max = INT50, message = "Lunghezza nome non valida")
    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;
    /**
     * Cognome dell'utente.
     */
    @NotNull(message = "Il cognome non può essere nullo")
    @Column(length = INT50)
    @Size(min = INT1, max = INT50, message = "Lunghezza cognome non valida")
    @NotBlank(message = "Il cognome non può essere vuoto")
    private String cognome;
    /**
     * Email dell'utente.
     */
    @NotNull(message = "L'email non può essere nulla")
    @Column(length = INT50, unique = true)
    @Size(min = INT1, max = INT50, message = "Lunghezza email non valida")
    @Email(message = "Formato email non valida")
    private String email;
    /**
     * Password dell'utente.
     */
    @NotNull(message = "La password non può essere nulla")
    @Column(length = INT255)
    @Size(min = INT8, max = INT255, message = "Lunghezza password non valida")
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
     * Data di creazione dell'utente.
     */
    @Column(name = "data_creazione")
    private LocalDateTime dataCreazione;
    /**
     * Data dell'ultimo aggiornamento dell'utente.
     */
    @Column(name = "data_aggiornamento")
    private LocalDateTime dataAggiornamento;
    /**
     * Sesso dell'utente.
     */
    @Column(length = INT1)
    @Size(min = INT1, max = INT1, message = "Lunghezza sesso non valida")
    private String sesso;
    /**
     * Numero di telefono dell'utente.
     */
    @Column(length = INT15)
    @Size(min = INT4, max = INT15, message = "Lunghezza telefono non valida")
    @Pattern(regexp = "^[+03][0-9]{3,14}",
            message = "Formato telefono non valido")
    private String telefono;
    /**
     * Via dell'utente.
     */
    @Column(length = INT50)
    @Size(min = INT1, max = INT50, message = "Lunghezza via non valida")
    private String via;
    /**
     * Cap dell'utente.
     */
    @Column(length = INT5)
    @Size(min = INT5, max = INT5, message = "Lunghezza cap non valida")
    @Pattern(regexp = "[0-9]{5}", message = "Formato cap non valido")
    private String cap;
    /**
     * Città dell'utente.
     */
    @Column(length = INT20)
    @Size(min = INT1, max = INT20, message = "Lunghezza città non valida")
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
    @JsonManagedReference
    private List<Report> listaReport;
}
