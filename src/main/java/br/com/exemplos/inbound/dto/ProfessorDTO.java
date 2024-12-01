package br.com.exemplos.inbound.dto;

import br.com.caelum.stella.bean.validation.CPF;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfessorDTO {

    private String nome;

    @CPF
    private String cpf;

    private List<MateriaDTO> materias;

}
