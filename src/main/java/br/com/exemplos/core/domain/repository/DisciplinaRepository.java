package br.com.exemplos.core.domain.repository;

import br.com.exemplos.core.domain.entity.Disciplina;

import java.util.Optional;

public interface DisciplinaRepository {

    Optional<Disciplina> findById(Long id);

    Disciplina save(Disciplina disciplina);

    void deleteById(Long id);

}
