package it.fitdiary.backend.gestioneutenza.service;

import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.gestioneutenza.repository.RuoloRepository;
import it.fitdiary.backend.gestioneutenza.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GestioneUtenzaServiceImpl implements GestioneUtenzaService, UserDetailsService {

    private final UtenteRepository utenteRepository;
    private final RuoloRepository ruoloRepository;

    @Override
    public Utente registrazione(Utente utente) throws IllegalArgumentException {
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        utente.setRuolo(ruoloRepository.findByNome("PREPARATORE"));
        utente.setAttivo(false);
        return utenteRepository.save(utente);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    /**
     * Questo metodo permette di inserire i dati nel sistema ad un cliente
     * @param utente rappresenta l'insieme dei dati personali di un utente
     * @return utente rappresenta l'utente con i nuovi dati inserito nel database
     * @throws IllegalArgumentException lancia l'errore generato da un input errato
     */
    @Override
    public Utente inserimentoDatiPersonaliCliente(Utente utente) throws IllegalArgumentException {
        Utente newUtente = utenteRepository.findById(utente.getId()).orElse(null);
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        if (newUtente == null) {
            throw new IllegalArgumentException("Utente non presente del Database");
        }
        newUtente.setDataNascita(utente.getDataNascita());
        newUtente.setTelefono(utente.getTelefono());
        newUtente.setCitta(utente.getCitta());
        newUtente.setVia(utente.getVia());
        newUtente.setCap(utente.getCap());
        return utenteRepository.save(newUtente);
    }
    /**
     * Questo metodo permette di inserire i dati da modificare nel sistema ad un cliente
     * @param utente rappresenta l'insieme dei dati personali di un utente
     * @return utente rappresenta l'utente con i nuovi dati inserito nel database
     * @throws IllegalArgumentException lancia l'errore generato da un input errato
     */
    @Override
    public Utente modificaDatiPersonaliCliente(Utente utente) throws IllegalArgumentException {
        Utente newUtente = utenteRepository.findById(utente.getId()).orElse(null);
        if (utente == null) {
            throw new IllegalArgumentException("Utente non valido");
        }
        if (newUtente == null) {
            throw new IllegalArgumentException("Utente non presente del Database");
        }
        newUtente.setNome(utente.getNome());
        newUtente.setCognome(utente.getCognome());
        newUtente.setDataAggiornamento(utente.getDataAggiornamento());
        newUtente.setEmail(utente.getEmail());
        newUtente.setPassword(passwordEncoder.encode(utente.getPassword()));
        newUtente.setDataNascita(utente.getDataNascita());
        newUtente.setTelefono(utente.getTelefono());
        newUtente.setCitta(utente.getCitta());
        newUtente.setVia(utente.getVia());
        newUtente.setCap(utente.getCap());
        return utenteRepository.save(newUtente);
    }
}
