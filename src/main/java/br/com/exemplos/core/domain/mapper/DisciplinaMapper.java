package br.com.exemplos.core.domain.mapper;

import br.com.exemplos.core.domain.entity.Disciplina;
import br.com.exemplos.core.domain.entity.Professor;
import br.com.exemplos.core.domain.dto.DisciplinaDTO;
import br.com.exemplos.core.domain.dto.ProfessorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DisciplinaMapper {

    @Mapping(target = "professor", qualifiedByName = "mapSemDisciplina")
    DisciplinaDTO toDto(Disciplina entity);

    Disciplina toEntity(DisciplinaDTO dto);

    @Named("mapSemDisciplina")
    @Mapping(target = "disciplinas", ignore = true)
    ProfessorDTO mapSemDisciplina(Professor professor);

}
