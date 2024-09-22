package br.com.exemplos.tenant.core.domain.entity;

import br.com.caelum.stella.bean.validation.CPF;
import br.com.exemplos.tenant.core.domain.entity.multitenancy.MultiTenancyEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Aluno extends MultiTenancyEntity {

    private String nome;

    private int idade;

    @CPF
    private String cpf;

    @OneToMany
    private List<Materia> materias;

}
