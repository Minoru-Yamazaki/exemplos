package br.com.exemplos.core.repository;

import br.com.exemplos.core.domain.entity.Professor;

import java.util.Optional;

public interface ProfessorRepository {

	Optional<Professor> findById(Long id);

	Professor save(Professor professor);

	void deleteById(Long id);

}
