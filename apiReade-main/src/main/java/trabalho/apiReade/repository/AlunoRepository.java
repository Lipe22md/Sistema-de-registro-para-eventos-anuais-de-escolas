package trabalho.apiReade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trabalho.apiReade.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
