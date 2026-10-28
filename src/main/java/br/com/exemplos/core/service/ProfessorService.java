package br.com.exemplos.core.service;

import br.com.exemplos.core.domain.dto.ProfessorDTO;

public interface ProfessorService {

	ProfessorDTO buscarPorId(Long id);

	ProfessorDTO salvar(ProfessorDTO dto);

	void excluir(Long id);

	ProfessorDTO atualizar(ProfessorDTO dto);

}
