package br.com.exemplos.inbound.service;

import br.com.exemplos.inbound.dto.ProfessorDTO;

public interface ProfessorService {

    ProfessorDTO buscarPorId(Long id);

    ProfessorDTO salvar(ProfessorDTO dto);

}
