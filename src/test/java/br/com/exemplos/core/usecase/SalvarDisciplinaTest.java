package br.com.exemplos.core.usecase;

import br.com.exemplos.core.domain.entity.Disciplina;
import br.com.exemplos.core.domain.entity.Professor;
import br.com.exemplos.core.event.LimparCache;
import br.com.exemplos.core.repository.DisciplinaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalvarDisciplinaTest {

	@Mock
	private DisciplinaRepository repository;

	@Mock
	private ApplicationEventPublisher applicationEventPublisher;

	@InjectMocks
	private SalvarDisciplina useCase;

	@Test
	void deveRetornarDisciplinaSalva() {
		Disciplina disciplina = new Disciplina();
		when(repository.save(any())).thenReturn(disciplina);

		Disciplina resultado = useCase.execute(disciplina);

		assertSame(disciplina, resultado);
		verify(repository).save(disciplina);
	}

	@Test
	void devePublicarEventoLimparCacheQuandoProfessorIdNaoNulo() {
		Professor professor = new Professor();
		professor.setId(10L);

		Disciplina disciplina = new Disciplina();
		disciplina.setProfessor(professor);

		when(repository.save(any())).thenReturn(disciplina);

		useCase.execute(disciplina);

		ArgumentCaptor<LimparCache> captor = ArgumentCaptor.forClass(LimparCache.class);
		verify(applicationEventPublisher).publishEvent(captor.capture());

		LimparCache evento = captor.getValue();
		assertEquals("professores", evento.getCache().getCacheName());
		assertEquals("10", evento.getCache().getKey());
	}

	@Test
	void naoDevePublicarEventoQuandoProfessorIdNulo() {
		Disciplina disciplina = new Disciplina(); // sem professor
		when(repository.save(any())).thenReturn(disciplina);

		useCase.execute(disciplina);

		verify(applicationEventPublisher, never()).publishEvent(any());
	}

}
