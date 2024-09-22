package br.com.exemplos.tenant.outbound.repository;

import br.com.exemplos.tenant.core.domain.entity.Professor;
import br.com.exemplos.tenant.core.domain.repository.ProfessorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorJpaRepository extends JpaRepository<Professor, Long>, ProfessorRepository {

}
