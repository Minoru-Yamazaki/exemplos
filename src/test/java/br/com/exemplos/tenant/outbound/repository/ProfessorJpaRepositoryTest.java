package br.com.exemplos.tenant.outbound.repository;

import br.com.exemplos.core.domain.exception.DomainException;
import br.com.exemplos.core.context.TenantContext;
import br.com.exemplos.core.domain.entity.Professor;
import br.com.exemplos.core.domain.entity.multitenancy.Persona;
import br.com.exemplos.core.repository.ProfessorRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ProfessorJpaRepositoryTest {

	@Autowired
	private ProfessorRepository professorRepository;

	private static final String TENANT_ID = "19293132095";

	@BeforeEach
	void setup() {
		TenantContext.setIdentificadorUsuario(TENANT_ID);
		Persona persona = new Persona();
		persona.setTenantId(TENANT_ID);
		TenantContext.setPerfilEmUso(persona);
	}

	@AfterAll
	static void close() {
		TenantContext.clear();
	}

	@Test
	void deveSalvarUmProfessor() {
		Professor professor = professorRepository.save(getProfessor());

		Professor professorSalvo = professorRepository.findById(1L)
			.orElseThrow(DomainException::professorNaoEncontrado);

		assertEquals(professor.getNome(), professorSalvo.getNome());
	}

	private Professor getProfessor() {
		Professor professor = new Professor();
		professor.setNome("Professor");
		professor.setCpf("123");
		return professor;
	}

}