package it.fitdiary.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotNull(message="Il nome non può essere nullo")
    @Column(length = 50)
    @Size(min=1, max=50, message="Lunghezza nome non valida")
    @NotBlank(message="Il nome non può essere vuoto")
    private String nome;
    @NotNull(message="Il cognome non può essere nullo")
    @Column(length = 50)
    @Size(min=1, max=50, message="Lunghezza cognome non valida")
    @NotBlank(message="Il cognome non può essere vuoto")
    private String cognome;
    @NotNull(message="L'email non può essere nulla")
    @Column(length = 50)
    @Size(min=1, max=50, message="Lunghezza email non valida")
    @Email(message="Formato email non valida")
    private String email;
    @NotNull(message="La password non può essere nulla")
    @Column(length = 255)
    @Size(min=8, max=255, message="Lunghezza password non valida")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message="Formato password non valido")
    private String password;
    @NotNull(message="Attivo non può essere nullo")
    private Boolean attivo;
    @NotNull(message="La data di nascita non può essere nulla")
    @Column(name = "data_nascita")
    @Past(message="La data di nascita non può essere successiva o coincidente alla data odierna")
    private LocalDate dataNascita;
    @NotNull(message="La data di creazione non può essere nulla")
    @Column(name = "data_creazione")
    private LocalDateTime dataCreazione;
    @NotNull(message="La data di aggiornamento non può essere nulla")
    @Column(name = "data_aggiornamento")
    private LocalDateTime dataAggiornamento;
    @Column(length = 1)
    @Size(min=1, max=1, message="Lunghezza sesso non valida")
    @NotBlank(message="Il sesso non può essere vuoto")
    private String sesso;
    @Column(length = 15)
    @Size(min=4, max=15, message="Lunghezza telefono non valida")
    @NotBlank(message="Il telefono non può essere vuoto")
    @Pattern(regexp = "^[+03][0-9]{3,14}", message="Formato telefono non valido")
    private String telefono;
    @Column(length = 50)
    @Size(min=1, max=50, message="Lunghezza via non valida")
    @NotBlank(message="La via non può essere vuota")
    private String via;
    @Column(length = 5)
    @Size(min=5, max=5, message="Lunghezza cap non valida")
    @NotBlank(message="Il cap non può essere vuoto")
    @Pattern(regexp = "[0-9]{5}", message="Formato cap non valido")
    private String cap;
    @Column(length = 20)
    @Size(min=1, max=20, message="Lunghezza città non valida")
    @NotBlank(message="La città non può essere vuota")
    private String citta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preparatore_id")
    private Utente preparatore;
    @NotNull(message="Il ruolo non può essere nullo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruolo_id")
    private Ruolo ruolo;
    @OneToMany(mappedBy = "cliente")
    private List<Protocollo> listaProtocolli;
    @OneToMany(mappedBy = "preparatore")
    private List<Utente> listaClienti;
    @OneToMany(mappedBy = "cliente")
    private List<Report> listaReport;
}
