// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.service;

// IMPORTA A ENTIDADE DE MENSAGEM DE ATENDIMENTO
import br.com.unijorge.sistema_eventos.model.MensagemAtendimento;

// IMPORTA O ENUM DE STATUS DA MENSAGEM
import br.com.unijorge.sistema_eventos.model.MensagemAtendimento.StatusMensagem;

// IMPORTA A ENTIDADE DE USUÁRIO
import br.com.unijorge.sistema_eventos.model.Usuario;

// IMPORTA O REPOSITÓRIO DE MENSAGEM DE ATENDIMENTO
import br.com.unijorge.sistema_eventos.repository.MensagemAtendimentoRepository;

// IMPORTA O REPOSITÓRIO DE USUÁRIO
import br.com.unijorge.sistema_eventos.repository.UsuarioRepository;

// IMPORTA A ANOTAÇÃO SERVICE DO SPRING
import org.springframework.stereotype.Service;

// IMPORTA A LISTA DO JAVA
import java.util.List;

// INDICA QUE ESSA CLASSE É UM SERVICE DO SPRING
@Service
public class AtendimentoService {

    // REPOSITÓRIO DAS MENSAGENS DE ATENDIMENTO
    private final MensagemAtendimentoRepository mensagemRepository;

    // REPOSITÓRIO DOS USUÁRIOS
    private final UsuarioRepository usuarioRepository;

    // CONSTRUTOR PARA INJETAR OS REPOSITÓRIOS
    public AtendimentoService(MensagemAtendimentoRepository mensagemRepository, UsuarioRepository usuarioRepository) {
        this.mensagemRepository = mensagemRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // MÉTODO PARA ENVIAR UMA MENSAGEM DE ATENDIMENTO
    public MensagemAtendimento enviar(String emailUsuario, MensagemAtendimento mensagem) {

        // BUSCA O USUÁRIO PELO EMAIL
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado."));

        // DEFINE O USUÁRIO DA MENSAGEM
        mensagem.setUsuario(usuario);

        // DEFINE O STATUS DA MENSAGEM COMO ABERTA
        mensagem.setStatus(StatusMensagem.ABERTA);

        // SALVA A MENSAGEM NO BANCO DE DADOS
        return mensagemRepository.save(mensagem);
    }

    // MÉTODO PARA RESPONDER UMA MENSAGEM
    public void responder(Long mensagemId, String resposta) {

        // BUSCA A MENSAGEM PELO ID
        MensagemAtendimento mensagem = mensagemRepository.findById(mensagemId)
                .orElseThrow(() -> new IllegalArgumentException("Mensagem nao encontrada."));

        // ADICIONA A RESPOSTA NA MENSAGEM
        mensagem.responder(resposta);

        // SALVA A MENSAGEM RESPONDIDA NO BANCO DE DADOS
        mensagemRepository.save(mensagem);
    }

    // MÉTODO PARA LISTAR AS MENSAGENS DE UM USUÁRIO
    public List<MensagemAtendimento> listarPorUsuario(String emailUsuario) {

        // BUSCA O USUÁRIO PELO EMAIL
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado."));

        // RETORNA AS MENSAGENS DO USUÁRIO ORDENADAS PELA DATA DE ENVIO
        return mensagemRepository.findByUsuarioIdOrderByEnviadaEmDesc(usuario.getId());
    }

    // MÉTODO PARA LISTAR TODAS AS MENSAGENS
    public List<MensagemAtendimento> listarTodas() {

        // RETORNA TODAS AS MENSAGENS ORDENADAS PELA DATA DE ENVIO
        return mensagemRepository.findAllByOrderByEnviadaEmDesc();
    }
}