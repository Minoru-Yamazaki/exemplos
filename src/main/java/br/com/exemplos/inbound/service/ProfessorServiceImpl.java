package br.com.exemplos.inbound.service;

import br.com.exemplos.core.domain.entity.Professor;
import br.com.exemplos.core.domain.exception.DomainException;
import br.com.exemplos.core.domain.repository.ProfessorRepository;
import br.com.exemplos.inbound.dto.ProfessorDTO;
import br.com.exemplos.inbound.mapper.ProfessorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository repository;

    private final ProfessorMapper mapper;

    @Override
    public ProfessorDTO buscarPorId(Long id) {
        Professor professor = repository.findById(id)
                .orElseThrow(DomainException::professorNaoEncontrado);
        log.info("Professor encontrado: {}", professor);
        return mapper.toDto(professor);
    }

    @Override
    public ProfessorDTO salvar(ProfessorDTO dto) {
        Professor professor = repository.save(mapper.toEntity(dto));
        log.info("Professor salvo: {}", professor);
        return mapper.toDto(professor);
    }

}
