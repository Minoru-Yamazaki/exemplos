package br.com.exemplos.inbound.controller;

import br.com.exemplos.inbound.service.ProfessorService;
import br.com.exemplos.inbound.dto.ProfessorDTO;
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
    public ResponseEntity<ProfessorDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> salvarProfessor(@RequestBody @Valid ProfessorDTO dto){
        return ResponseEntity.ok(service.salvar(dto));
    }

}
