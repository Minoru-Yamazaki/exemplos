package br.com.exemplos.inbound.service;

import br.com.exemplos.core.domain.dto.ProfessorDTO;
import br.com.exemplos.core.domain.entity.Professor;
import br.com.exemplos.core.domain.exception.DomainException;
import br.com.exemplos.core.domain.mapper.ProfessorMapper;
import br.com.exemplos.core.domain.mapper.ProfessorMapperImpl;
import br.com.exemplos.core.repository.ProfessorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceImplTest {

	@Spy
	private ProfessorMapper mapper = new ProfessorMapperImpl();

	@Mock
	private ProfessorRepository repository;

	@InjectMocks
	private ProfessorServiceImpl service;

	@Test
	void buscarPorIdDeveRetornarProfessorDTO() {
		Professor professor = new Professor();
		professor.setNome("Maria");
		when(repository.findById(anyLong())).thenReturn(Optional.of(professor));

		ProfessorDTO dto = service.buscarPorId(1L);

		assertNotNull(dto);
		verify(repository).findById(1L);
		verify(mapper).toDto(professor);
	}

	@Test
	void buscarPorIdDeveLancarExcecaoQuandoNaoEncontrado() {
		when(repository.findById(anyLong())).thenReturn(Optional.empty());

		DomainException exception = assertThrows(DomainException.class, () -> service.buscarPorId(99L));

		assertEquals(DomainException.professorNaoEncontrado().getCode(), exception.getCode());
	}

	@Test
	void salvarDevePersistirERetornarDTO() {
		Professor professor = new Professor();
		professor.setNome("João");
		when(repository.save(any())).thenReturn(professor);

		ProfessorDTO dto = new ProfessorDTO();
		dto.setNome("João");

		ProfessorDTO resultado = service.salvar(dto);

		assertNotNull(resultado);
		verify(repository).save(any());
	}

	@Test
	void excluirDeveInvocarDeleteById() {
		service.excluir(5L);

		verify(repository).deleteById(5L);
	}

	@Test
	void atualizarDevePersistirERetornarDTO() {
		Professor professor = new Professor();
		professor.setNome("Carlos");
		when(repository.save(any())).thenReturn(professor);

		ProfessorDTO dto = new ProfessorDTO();
		dto.setNome("Carlos");

		ProfessorDTO resultado = service.atualizar(dto);

		assertNotNull(resultado);
		verify(repository).save(any());
	}

	@Test
	void buscarPorIdDeveLancarRuntimeExceptionQuandoThreadForInterrompida() throws Exception {
		ProfessorServiceImpl serviceSpy = spy(service);
		doThrow(new InterruptedException("thread interrompida")).when(serviceSpy).sleep(1000);

		RuntimeException exception = assertThrows(RuntimeException.class, () -> serviceSpy.buscarPorId(1L));

		assertInstanceOf(InterruptedException.class, exception.getCause());
		assertTrue(Thread.currentThread().isInterrupted(), "A flag de interrupção da thread deve estar marcada");

		// limpa a flag de interrupção para não afetar outros testes
		Thread.interrupted();
	}

}
