package br.com.exemplos.core.domain.entity;

import br.com.exemplos.core.domain.entity.multitenancy.MultiTenancyEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Entity
@ToString
public class Professor extends MultiTenancyEntity {

    private String nome;

    private String cpf;

    @OneToMany(mappedBy = "professor")
    private List<Materia> materias;

}
