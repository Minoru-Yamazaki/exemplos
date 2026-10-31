package br.com.exemplos.inbound.controller;

import br.com.exemplos.core.domain.dto.ProfessorDTO;
import br.com.exemplos.core.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/professor")
public class ProfessorController {

	private final ProfessorService service;

	@GetMapping("{id}")
	public ResponseEntity<ProfessorDTO> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<ProfessorDTO> salvar(@RequestBody @Valid ProfessorDTO dto) {
		return ResponseEntity.status(201).body(service.salvar(dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		service.excluir(id);
		return ResponseEntity.status(204).build();
	}

	@PutMapping
	public ResponseEntity<Void> atualizar(@RequestBody @Valid ProfessorDTO dto) {
		service.atualizar(dto);
		return ResponseEntity.status(204).build();
	}

}
