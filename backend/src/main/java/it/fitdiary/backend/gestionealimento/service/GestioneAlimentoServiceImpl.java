package it.fitdiary.backend.gestionealimento.service;

import it.fitdiary.backend.entity.Alimento;
import it.fitdiary.backend.gestionealimento.repository.AlimentoService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GestioneAlimentoServiceImpl implements GestioneAlimentoService{


  AlimentoService alimentoService;

  @Override
  public Alimento getById(final Long idAlimento) {
    if (idAlimento == null) {
      throw new IllegalArgumentException("Id alimento non valido");
    }
    Alimento alimento = alimentoService.getById(idAlimento);
    if (alimento == null) {
      throw new IllegalArgumentException("Alimento non trovato");
    }
    return alimento;
  }
}
