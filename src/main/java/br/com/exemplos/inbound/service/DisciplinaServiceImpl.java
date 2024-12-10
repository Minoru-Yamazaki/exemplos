package br.com.exemplos.inbound.service;

import br.com.exemplos.core.domain.entity.Disciplina;
import br.com.exemplos.core.domain.exception.DomainException;
import br.com.exemplos.core.repository.DisciplinaRepository;
import br.com.exemplos.core.usecase.SalvarDisciplina;
import br.com.exemplos.core.domain.dto.DisciplinaDTO;
import br.com.exemplos.core.domain.mapper.DisciplinaMapper;
import br.com.exemplos.core.service.DisciplinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DisciplinaServiceImpl implements DisciplinaService {

    private final DisciplinaMapper mapper;

    private final SalvarDisciplina salvar;

    private final DisciplinaRepository repository;

    @Override
    public DisciplinaDTO salvar(DisciplinaDTO dto) {
        Disciplina disciplina = salvar.execute(mapper.toEntity(dto));
        return mapper.toDto(disciplina);
    }

    @Override
    public DisciplinaDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(DomainException::disciplinaNaoEncontrada);
    }

}
