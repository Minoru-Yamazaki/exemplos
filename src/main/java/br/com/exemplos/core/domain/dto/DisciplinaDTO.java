package br.com.exemplos.core.domain.dto;

import br.com.exemplos.core.domain.entity.enuns.DiaDaSemana;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
public class DisciplinaDTO implements Serializable {

	private String nome;

	@Enumerated(EnumType.STRING)
	private DiaDaSemana diaDaSemana;

	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime horarioInicio;

	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime horarioFim;

	private int cargaHoraria;

	private ProfessorDTO professor;

}
