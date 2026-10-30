package br.com.exemplos.inbound.controller;

import br.com.exemplos.core.domain.dto.DisciplinaDTO;
import br.com.exemplos.core.domain.exception.DomainException;
import br.com.exemplos.core.service.DisciplinaService;
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
class DisciplinaControllerTest {

    @Mock
    private DisciplinaService service;

    @InjectMocks
    private DisciplinaController controller;

    @Test
    void salvarDeveRetornarStatus201ComDTO() {
        DisciplinaDTO dto = new DisciplinaDTO();
        dto.setNome("Matemática");
        when(service.salvar(any())).thenReturn(dto);

        ResponseEntity<DisciplinaDTO> response = controller.salvar(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Matemática", response.getBody().getNome());
        verify(service).salvar(dto);
    }

    @Test
    void buscarPorIdDeveRetornarStatus200ComDTO() {
        DisciplinaDTO dto = new DisciplinaDTO();
        dto.setNome("Física");
        when(service.buscarPorId(anyLong())).thenReturn(dto);

        ResponseEntity<DisciplinaDTO> response = controller.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Física", response.getBody().getNome());
        verify(service).buscarPorId(1L);
    }

    @Test
    void buscarPorIdDevePropagrarExcecaoQuandoNaoEncontrado() {
        when(service.buscarPorId(anyLong())).thenThrow(DomainException.disciplinaNaoEncontrada());

        DomainException exception = assertThrows(DomainException.class,
                () -> controller.buscarPorId(99L));

        assertEquals(DomainException.disciplinaNaoEncontrada().getCode(), exception.getCode());
    }

}
