package br.com.exemplos.tenant.core.domain.repository;

import br.com.exemplos.tenant.core.domain.entity.Professor;

import java.util.Optional;

public interface ProfessorRepository {

    Optional<Professor> findById(Long id);

    Professor save(Professor professor);

}
