package trabalho.apiReade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trabalho.apiReade.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
