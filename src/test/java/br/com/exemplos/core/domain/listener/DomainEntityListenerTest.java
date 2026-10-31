package br.com.exemplos.core.domain.listener;

import br.com.exemplos.core.domain.entity.Disciplina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DomainEntityListenerTest {

	private DomainEntityListener listener;

	@BeforeEach
	void setUp() {
		listener = new DomainEntityListener();
	}

	@Test
	void prePersistDeveDefinirDataInsercaoQuandoNula() {
		Disciplina disciplina = new Disciplina();
		assertNull(disciplina.getDataInsercao());

		listener.prePersist(disciplina);

		assertNotNull(disciplina.getDataInsercao());
	}

	@Test
	void prePersistNaoDeveSubstituirDataInsercaoExistente() {
		Disciplina disciplina = new Disciplina();
		LocalDateTime dataOriginal = LocalDateTime.of(2023, 1, 1, 0, 0);
		disciplina.setDataInsercao(dataOriginal);

		listener.prePersist(disciplina);

		assertEquals(dataOriginal, disciplina.getDataInsercao());
	}

	@Test
	void prePersistDeveDefinirDataAtualizacao() {
		Disciplina disciplina = new Disciplina();

		LocalDateTime antes = LocalDateTime.now().minusSeconds(1);
		listener.prePersist(disciplina);
		LocalDateTime depois = LocalDateTime.now().plusSeconds(1);

		assertNotNull(disciplina.getDataAtualizacao());
		assertTrue(disciplina.getDataAtualizacao().isAfter(antes));
		assertTrue(disciplina.getDataAtualizacao().isBefore(depois));
	}

	@Test
	void preUpdateDeveAtualizarDataAtualizacao() {
		Disciplina disciplina = new Disciplina();
		LocalDateTime antiga = LocalDateTime.of(2020, 1, 1, 0, 0);
		disciplina.setDataAtualizacao(antiga);

		LocalDateTime antes = LocalDateTime.now().minusSeconds(1);
		listener.preUpdate(disciplina);

		assertTrue(disciplina.getDataAtualizacao().isAfter(antes));
	}

	@Test
	void preUpdateNaoDeveAlterarDataInsercao() {
		Disciplina disciplina = new Disciplina();
		LocalDateTime dataInsercao = LocalDateTime.of(2021, 6, 15, 10, 0);
		disciplina.setDataInsercao(dataInsercao);

		listener.preUpdate(disciplina);

		assertEquals(dataInsercao, disciplina.getDataInsercao());
	}

}
