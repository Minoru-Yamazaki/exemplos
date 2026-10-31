package br.com.exemplos.inbound.controller;

import br.com.exemplos.core.domain.dto.DisciplinaDTO;
import br.com.exemplos.core.service.DisciplinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/disciplina")
public class DisciplinaController {

	private final DisciplinaService service;

	@PostMapping
	public ResponseEntity<DisciplinaDTO> salvar(@RequestBody @Valid DisciplinaDTO dto) {
		return ResponseEntity.status(201).body(service.salvar(dto));
	}

	@GetMapping("{id}")
	public ResponseEntity<DisciplinaDTO> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}

}
