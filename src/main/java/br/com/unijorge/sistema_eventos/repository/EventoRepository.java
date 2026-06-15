// PACOTE DA INTERFACE
package br.com.unijorge.sistema_eventos.repository;

// IMPORTA A CLASSE EVENTO
import br.com.unijorge.sistema_eventos.model.Evento;

// IMPORTA O JPAREPOSITORY DO SPRING DATA JPA
import org.springframework.data.jpa.repository.JpaRepository;

// IMPORTA LOCALDATETIME PARA TRABALHAR COM DATA E HORA
import java.time.LocalDateTime;

// IMPORTA LISTA DO JAVA
import java.util.List;

// INTERFACE RESPONSÁVEL PELO ACESSO AOS DADOS DA TABELA DE EVENTOS
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // BUSCA TODOS OS EVENTOS ATIVOS ORDENADOS PELA DATA E HORA
    List<Evento> findByAtivoTrueOrderByDataHoraAsc();

    // BUSCA EVENTOS ATIVOS COM DATA FUTURA ORDENADOS PELA DATA E HORA
    List<Evento> findByAtivoTrueAndDataHoraAfterOrderByDataHoraAsc(LocalDateTime dataHora);
}