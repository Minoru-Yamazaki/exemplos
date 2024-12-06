package br.com.exemplos.inbound.service.disciplina;

import br.com.exemplos.inbound.dto.DisciplinaDTO;

public interface DisciplinaService {

    DisciplinaDTO salvar(DisciplinaDTO dto);

    DisciplinaDTO buscarPorId(Long id);
}
