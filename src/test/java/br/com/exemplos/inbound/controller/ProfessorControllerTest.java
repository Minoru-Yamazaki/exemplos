package br.com.exemplos.inbound.controller;

import br.com.exemplos.core.domain.dto.ProfessorDTO;
import br.com.exemplos.core.domain.exception.DomainException;
import br.com.exemplos.core.service.ProfessorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfessorControllerTest {

	@Mock
	private ProfessorService service;

	@InjectMocks
	private ProfessorController controller;

	@Test
	void buscarPorIdDeveRetornarStatus200ComDTO() {
		ProfessorDTO dto = new ProfessorDTO();
		dto.setNome("Ana");
		when(service.buscarPorId(anyLong())).thenReturn(dto);

		ResponseEntity<ProfessorDTO> response = controller.buscarPorId(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Ana", response.getBody().getNome());
		verify(service).buscarPorId(1L);
	}

	@Test
	void buscarPorIdDevePropagrarExcecaoQuandoNaoEncontrado() {
		when(service.buscarPorId(anyLong())).thenThrow(DomainException.professorNaoEncontrado());

		DomainException exception = assertThrows(DomainException.class, () -> controller.buscarPorId(99L));

		assertEquals(DomainException.professorNaoEncontrado().getCode(), exception.getCode());
	}

	@Test
	void salvarDeveRetornarStatus201ComDTO() {
		ProfessorDTO dto = new ProfessorDTO();
		dto.setNome("Bruno");
		when(service.salvar(any())).thenReturn(dto);

		ResponseEntity<ProfessorDTO> response = controller.salvar(dto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Bruno", response.getBody().getNome());
		verify(service).salvar(dto);
	}

	@Test
	void excluirDeveRetornarStatus204SemBody() {
		ResponseEntity<Void> response = controller.excluir(7L);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
		verify(service).excluir(7L);
	}

	@Test
	void atualizarDeveRetornarStatus204SemBody() {
		ProfessorDTO dto = new ProfessorDTO();
		dto.setNome("Carlos");

		ResponseEntity<Void> response = controller.atualizar(dto);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
		verify(service).atualizar(dto);
	}

}
