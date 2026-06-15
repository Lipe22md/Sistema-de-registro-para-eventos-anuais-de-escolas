// PACOTE DA INTERFACE
package br.com.unijorge.sistema_eventos.repository;

// IMPORTA A CLASSE MENSAGEM DE ATENDIMENTO
import br.com.unijorge.sistema_eventos.model.MensagemAtendimento;

// IMPORTA O STATUS DA MENSAGEM DE ATENDIMENTO
import br.com.unijorge.sistema_eventos.model.MensagemAtendimento.StatusMensagem;

// IMPORTA O JPAREPOSITORY DO SPRING DATA JPA
import org.springframework.data.jpa.repository.JpaRepository;

// IMPORTA LISTA DO JAVA
import java.util.List;

// INTERFACE RESPONSÁVEL PELO ACESSO AOS DADOS DAS MENSAGENS DE ATENDIMENTO
public interface MensagemAtendimentoRepository extends JpaRepository<MensagemAtendimento, Long> {

    // BUSCA AS MENSAGENS DE UM USUÁRIO ORDENADAS PELA DATA DE ENVIO
    List<MensagemAtendimento> findByUsuarioIdOrderByEnviadaEmDesc(Long usuarioId);

    // BUSCA AS MENSAGENS PELO STATUS ORDENADAS DA MAIS ANTIGA PARA A MAIS RECENTE
    List<MensagemAtendimento> findByStatusOrderByEnviadaEmAsc(StatusMensagem status);

    // BUSCA TODAS AS MENSAGENS ORDENADAS DA MAIS RECENTE PARA A MAIS ANTIGA
    List<MensagemAtendimento> findAllByOrderByEnviadaEmDesc();
}