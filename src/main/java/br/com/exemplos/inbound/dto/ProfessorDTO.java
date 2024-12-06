package br.com.exemplos.inbound.dto;

import br.com.caelum.stella.bean.validation.CPF;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProfessorDTO implements Serializable {

    private Long id;

    private String nome;

    @CPF
    private String cpf;

    private List<DisciplinaDTO> disciplinas;

}
