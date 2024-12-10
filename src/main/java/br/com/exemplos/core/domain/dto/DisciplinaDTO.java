package br.com.exemplos.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
public class DisciplinaDTO implements Serializable {

    private String nome;

    private String diaDaSemana;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horarioInicio;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horarioFim;

    private int cargaHoraria;

    private ProfessorDTO professor;

}
