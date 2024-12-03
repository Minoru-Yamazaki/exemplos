package br.com.exemplos.inbound.service;

import br.com.exemplos.core.domain.entity.Professor;
import br.com.exemplos.core.domain.exception.DomainException;
import br.com.exemplos.core.domain.repository.ProfessorRepository;
import br.com.exemplos.inbound.dto.ProfessorDTO;
import br.com.exemplos.inbound.mapper.ProfessorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository repository;

    private final ProfessorMapper mapper;

    @Override
    @Cacheable(value = "professores", key = "#id")
    public ProfessorDTO buscarPorId(Long id) {
        try {
            Thread.sleep(3000); // Simula carregamento demorado
            Professor professor = repository.findById(id)
                    .orElseThrow(DomainException::professorNaoEncontrado);
            log.info("Professor encontrado: {}", professor);
            return mapper.toDto(professor);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProfessorDTO salvar(ProfessorDTO dto) {
        Professor professor = repository.save(mapper.toEntity(dto));
        log.info("Professor salvo: {}", professor);
        return mapper.toDto(professor);
    }

    @Override
    @CacheEvict(value = "professores", key = "#id")
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    @Override
    @CachePut(value = "professores", key = "#dto.id")
    public ProfessorDTO atualizar(ProfessorDTO dto) {
        Professor professor = repository.save(mapper.toEntity(dto));
        log.info("Professor atualizado: {}", professor);
        return mapper.toDto(professor);
    }

}
