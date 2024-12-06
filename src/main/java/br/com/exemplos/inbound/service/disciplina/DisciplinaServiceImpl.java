package br.com.exemplos.inbound.service.disciplina;

import br.com.exemplos.core.domain.entity.Disciplina;
import br.com.exemplos.core.domain.exception.DomainException;
import br.com.exemplos.core.domain.repository.DisciplinaRepository;
import br.com.exemplos.inbound.dto.DisciplinaDTO;
import br.com.exemplos.inbound.mapper.DisciplinaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class DisciplinaServiceImpl implements DisciplinaService {

    private final DisciplinaRepository repository;

    private final DisciplinaMapper mapper;

    @Override
    public DisciplinaDTO salvar(DisciplinaDTO dto) {
        Disciplina disciplina = repository.save(mapper.toEntity(dto));
        log.info("Disciplina salva: {}", disciplina);
        return mapper.toDto(disciplina);
    }

    @Override
    public DisciplinaDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(DomainException::disciplinaNaoEncontrada);
    }

}
