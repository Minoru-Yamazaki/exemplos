package br.com.exemplos.inbound.service.professor;

import br.com.exemplos.inbound.dto.ProfessorDTO;

public interface ProfessorService {

    ProfessorDTO buscarPorId(Long id);

    ProfessorDTO salvar(ProfessorDTO dto);

    void excluir(Long id);

    ProfessorDTO atualizar(ProfessorDTO dto);

}
