package br.com.exemplos.core.usecase;

import br.com.exemplos.core.domain.entity.Disciplina;
import br.com.exemplos.core.domain.vo.DomainCache;
import br.com.exemplos.core.repository.DisciplinaRepository;
import br.com.exemplos.core.event.LimparCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class SalvarDisciplina {

	private final DisciplinaRepository repository;

	private final ApplicationEventPublisher applicationEventPublisher;

	public Disciplina execute(Disciplina disciplina) {
		disciplina = repository.save(disciplina);
		log.info("Disciplina salva: {}", disciplina);
		if (disciplina.getProfessorId() != null)
			applicationEventPublisher
				.publishEvent(new LimparCache(new DomainCache("professores", disciplina.getProfessorId().toString())));
		return disciplina;
	}

}
