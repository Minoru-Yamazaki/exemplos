package br.com.exemplos.outbound.repository;

import br.com.exemplos.core.repository.ProfessorRepository;
import br.com.exemplos.core.domain.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorJpaRepository extends JpaRepository<Professor, Long>, ProfessorRepository {

}
