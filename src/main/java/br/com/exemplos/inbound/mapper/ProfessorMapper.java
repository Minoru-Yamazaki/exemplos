package br.com.exemplos.inbound.mapper;

import br.com.exemplos.core.domain.entity.Professor;
import br.com.exemplos.inbound.dto.ProfessorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MateriaMapper.class)
public interface ProfessorMapper {

    ProfessorDTO toDto(Professor entity);

    Professor toEntity(ProfessorDTO dto);

}
