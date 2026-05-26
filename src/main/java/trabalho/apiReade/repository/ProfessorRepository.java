package trabalho.apiReade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trabalho.apiReade.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
