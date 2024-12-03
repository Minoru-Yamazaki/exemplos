package br.com.exemplos.inbound.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
public class MateriaDTO implements Serializable {

    private String nome;

    private String diaDaSemana;

    private LocalTime horarioInicio;

    private LocalTime horarioFim;

    private int cargaHoraria;

}
