package it.fitdiary.backend.gestionealimento.service;

import it.fitdiary.backend.entity.Alimento;

import java.util.List;

public interface GestioneAlimentoService
{
  Alimento getById(final Long idAlimento);
  List<Alimento> getAllAlimenti();
}
