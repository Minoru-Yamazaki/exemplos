package br.com.exemplos.inbound.service;

import br.com.exemplos.core.domain.dto.DisciplinaDTO;
import br.com.exemplos.core.domain.entity.Disciplina;
import br.com.exemplos.core.domain.exception.DomainException;
import br.com.exemplos.core.domain.mapper.DisciplinaMapper;
import br.com.exemplos.core.domain.mapper.DisciplinaMapperImpl;
import br.com.exemplos.core.repository.DisciplinaRepository;
import br.com.exemplos.core.usecase.SalvarDisciplina;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisciplinaServiceImplTest {

    @Spy
    private DisciplinaMapper mapper = new DisciplinaMapperImpl();

    @Mock
    private SalvarDisciplina useCase;

    @Mock
    private DisciplinaRepository repository;

    @InjectMocks
    private DisciplinaServiceImpl service;

    @Test
    void salvar() {
        when(useCase.execute(any())).thenReturn(new Disciplina());

        service.salvar(new DisciplinaDTO());

        verify(useCase).execute(any());
    }

    @Test
    void buscarPorId() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Disciplina()));

        service.buscarPorId(1L);

        verify(repository).findById(1L);
        verify(mapper).toDto(any());
    }

    @Test
    void deveLancarDisciplinaNaoEncontradaException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        DomainException exception = assertThrows(DomainException.class, () -> service.buscarPorId(1L));

        assertEquals(exception.getCode(), DomainException.disciplinaNaoEncontrada().getCode());
    }

}