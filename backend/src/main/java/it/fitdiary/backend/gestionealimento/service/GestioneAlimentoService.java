package it.fitdiary.backend.gestionealimento.service;

import it.fitdiary.backend.entity.Alimento;

import java.util.List;

public interface GestioneAlimentoService {

  public Alimento getById(final Long idAlimento);

  public List<Alimento> getAllAlimenti();
}
