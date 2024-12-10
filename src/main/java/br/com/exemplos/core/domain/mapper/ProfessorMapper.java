package br.com.exemplos.core.domain.mapper;

import br.com.exemplos.core.domain.entity.Disciplina;
import br.com.exemplos.core.domain.entity.Professor;
import br.com.exemplos.core.domain.dto.DisciplinaDTO;
import br.com.exemplos.core.domain.dto.ProfessorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {

    @Mapping(target = "disciplinas", qualifiedByName = "mapSemProfessor")
    ProfessorDTO toDto(Professor entity);

    Professor toEntity(ProfessorDTO dto);

    @Named("mapSemProfessor")
    @Mapping(target = "professor", ignore = true)
    DisciplinaDTO mapSemProfessor(Disciplina disciplina);

}
