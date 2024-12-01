package br.com.exemplos.inbound.controller;

import br.com.exemplos.core.domain.exception.DomainException;
import br.com.exemplos.core.domain.entity.Professor;
import br.com.exemplos.core.domain.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/professor")
public class ProfessorController {

    private final ProfessorRepository repository;

    @GetMapping("{id}")
    public ResponseEntity<Professor> buscarPorId(@PathVariable Long id){
        Professor professor = repository.findById(id).orElseThrow(DomainException::professorNaoEncontrado);
        return ResponseEntity.ok(professor);
    }

    @PostMapping
    public ResponseEntity<Professor> salvarProfessor(@RequestBody Professor professor){
        return ResponseEntity.ok(repository.save(professor));
    }

}
