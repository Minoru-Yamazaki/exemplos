package br.com.exemplos.tenant.inbound.controller;

import br.com.exemplos.exception.DomainException;
import br.com.exemplos.tenant.core.domain.entity.Professor;
import br.com.exemplos.tenant.core.domain.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/professor")
public class ProfessorController {

    private ProfessorRepository repository;

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
