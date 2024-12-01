package br.com.exemplos.inbound.mapper;

import br.com.exemplos.core.domain.entity.Materia;
import br.com.exemplos.inbound.dto.MateriaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MateriaMapper {

    MateriaDTO toDto(Materia entity);

    Materia toEntity(MateriaDTO dto);

}
