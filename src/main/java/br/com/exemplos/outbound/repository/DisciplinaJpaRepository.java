package br.com.exemplos.outbound.repository;

import br.com.exemplos.core.domain.entity.Disciplina;
import br.com.exemplos.core.repository.DisciplinaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaJpaRepository extends JpaRepository<Disciplina, Long>, DisciplinaRepository {

}
