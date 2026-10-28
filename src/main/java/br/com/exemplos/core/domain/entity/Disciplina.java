package br.com.exemplos.core.domain.entity;

import br.com.exemplos.core.domain.entity.enuns.DiaDaSemana;
import br.com.exemplos.core.domain.entity.multitenancy.MultiTenancyEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Optional;

@Getter
@Setter
@Entity
@ToString(exclude = "professor")
public class Disciplina extends MultiTenancyEntity {

	private String nome;

	@Enumerated(EnumType.STRING)
	private DiaDaSemana diaDaSemana;

	private LocalTime horarioInicio;

	private LocalTime horarioFim;

	private int cargaHoraria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "professorId", referencedColumnName = "id", nullable = false)
	private Professor professor;

	public Long getProfessorId() {
		return Optional.ofNullable(this.professor).map(Professor::getId).orElse(null);
	}

}
