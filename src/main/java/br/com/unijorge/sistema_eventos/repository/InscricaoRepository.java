// PACOTE DA INTERFACE
package br.com.unijorge.sistema_eventos.repository;

// IMPORTA A CLASSE INSCRICAO
import br.com.unijorge.sistema_eventos.model.Inscricao;

// IMPORTA O STATUS DA INSCRICAO
import br.com.unijorge.sistema_eventos.model.Inscricao.StatusInscricao;

// IMPORTA O JPAREPOSITORY DO SPRING DATA JPA
import org.springframework.data.jpa.repository.JpaRepository;

// IMPORTA LISTA DO JAVA
import java.util.List;

// IMPORTA OPTIONAL DO JAVA
import java.util.Optional;

// INTERFACE RESPONSÁVEL PELO ACESSO AOS DADOS DAS INSCRIÇÕES
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    // VERIFICA SE JÁ EXISTE INSCRIÇÃO DE UM USUÁRIO EM UM EVENTO
    boolean existsByUsuarioIdAndEventoId(Long usuarioId, Long eventoId);

    // VERIFICA SE JÁ EXISTE INSCRIÇÃO DE UM USUÁRIO EM UM EVENTO COM DETERMINADO STATUS
    boolean existsByUsuarioIdAndEventoIdAndStatus(Long usuarioId, Long eventoId, StatusInscricao status);

    // CONTA QUANTAS INSCRIÇÕES EXISTEM EM UM EVENTO COM DETERMINADO STATUS
    long countByEventoIdAndStatus(Long eventoId, StatusInscricao status);

    // BUSCA AS INSCRIÇÕES DE UM USUÁRIO ORDENADAS PELA DATA DE INSCRIÇÃO
    List<Inscricao> findByUsuarioIdOrderByDataInscricaoDesc(Long usuarioId);

    // BUSCA AS INSCRIÇÕES DE UM EVENTO ORDENADAS PELA DATA DE INSCRIÇÃO
    List<Inscricao> findByEventoIdOrderByDataInscricaoDesc(Long eventoId);

    // BUSCA UMA INSCRIÇÃO PELO ID DO USUÁRIO E PELO ID DO EVENTO
    Optional<Inscricao> findByUsuarioIdAndEventoId(Long usuarioId, Long eventoId);
}