package br.com.exemplos.core.service;

import br.com.exemplos.core.domain.dto.DisciplinaDTO;

public interface DisciplinaService {

	DisciplinaDTO salvar(DisciplinaDTO dto);

	DisciplinaDTO buscarPorId(Long id);

}
