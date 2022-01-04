package it.fitdiary.backend.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntityWithTimestamp {

    /**
     * La data creazione della tupla.
     */
    @Column(name = "data_creazione", updatable = false)
    @CreationTimestamp
    private LocalDateTime dataCreazione;

    /**
     * La data aggiornamento della tupla.
     */
    @Column(name = "data_aggiornamento")
    @UpdateTimestamp
    private LocalDateTime dataAggiornamento;
}
