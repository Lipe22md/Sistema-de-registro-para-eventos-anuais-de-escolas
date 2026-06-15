// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.service;

// IMPORTA A CLASSE EVENTO
import br.com.unijorge.sistema_eventos.model.Evento;

// IMPORTA A CLASSE INSCRICAO
import br.com.unijorge.sistema_eventos.model.Inscricao;

// IMPORTA O STATUS DA INSCRICAO
import br.com.unijorge.sistema_eventos.model.Inscricao.StatusInscricao;

// IMPORTA A CLASSE USUARIO
import br.com.unijorge.sistema_eventos.model.Usuario;

// IMPORTA O REPOSITÓRIO DE EVENTO
import br.com.unijorge.sistema_eventos.repository.EventoRepository;

// IMPORTA O REPOSITÓRIO DE INSCRICAO
import br.com.unijorge.sistema_eventos.repository.InscricaoRepository;

// IMPORTA O REPOSITÓRIO DE USUARIO
import br.com.unijorge.sistema_eventos.repository.UsuarioRepository;

// IMPORTA A ANOTAÇÃO SERVICE DO SPRING
import org.springframework.stereotype.Service;

// IMPORTA LISTA DO JAVA
import java.util.List;

// DEFINE A CLASSE COMO UM SERVICE DO SPRING
@Service
public class InscricaoService {

    // REPOSITÓRIO RESPONSÁVEL PELAS INSCRIÇÕES
    private final InscricaoRepository inscricaoRepository;

    // REPOSITÓRIO RESPONSÁVEL PELOS USUÁRIOS
    private final UsuarioRepository usuarioRepository;

    // REPOSITÓRIO RESPONSÁVEL PELOS EVENTOS
    private final EventoRepository eventoRepository;

    // CONSTRUTOR QUE RECEBE OS REPOSITÓRIOS
    public InscricaoService(InscricaoRepository inscricaoRepository, UsuarioRepository usuarioRepository,
                            EventoRepository eventoRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    // MÉTODO PARA INSCREVER UM USUÁRIO EM UM EVENTO
    public Inscricao inscrever(String emailUsuario, Long eventoId) {

        // BUSCA O USUÁRIO PELO EMAIL
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario precisa estar cadastrado para se inscrever."));

        // BUSCA O EVENTO PELO ID
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento nao encontrado."));

        // VERIFICA SE O EVENTO ESTÁ ATIVO
        if (!Boolean.TRUE.equals(evento.getAtivo())) {
            throw new IllegalArgumentException("Este evento nao esta disponivel para inscricao.");
        }

        // VERIFICA SE O USUÁRIO JÁ ESTÁ INSCRITO NO EVENTO
        if (inscricaoRepository.existsByUsuarioIdAndEventoIdAndStatus(usuario.getId(), eventoId, StatusInscricao.ATIVA)) {
            throw new IllegalArgumentException("Voce ja esta inscrito neste evento.");
        }

        // CONTA QUANTOS USUÁRIOS ESTÃO INSCRITOS NO EVENTO
        long inscritos = inscricaoRepository.countByEventoIdAndStatus(eventoId, StatusInscricao.ATIVA);

        // VERIFICA SE AINDA EXISTEM VAGAS DISPONÍVEIS
        if (inscritos >= evento.getVagasTotais()) {
            throw new IllegalArgumentException("Nao ha vagas disponiveis para este evento.");
        }

        // BUSCA UMA INSCRIÇÃO EXISTENTE OU CRIA UMA NOVA
        Inscricao inscricao = inscricaoRepository.findByUsuarioIdAndEventoId(usuario.getId(), eventoId)
                .orElse(new Inscricao());

        // DEFINE O USUÁRIO DA INSCRIÇÃO
        inscricao.setUsuario(usuario);

        // DEFINE O EVENTO DA INSCRIÇÃO
        inscricao.setEvento(evento);

        // DEFINE O STATUS DA INSCRIÇÃO COMO ATIVA
        inscricao.setStatus(StatusInscricao.ATIVA);

        // LIMPA A DATA DE CANCELAMENTO
        inscricao.setDataCancelamento(null);

        // SALVA A INSCRIÇÃO NO BANCO DE DADOS
        return inscricaoRepository.save(inscricao);
    }

    // MÉTODO PARA CANCELAR UMA INSCRIÇÃO
    public void cancelar(Long inscricaoId, String emailUsuario) {

        // BUSCA O USUÁRIO PELO EMAIL
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado."));

        // BUSCA A INSCRIÇÃO PELO ID
        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new IllegalArgumentException("Inscricao nao encontrada."));

        // VERIFICA SE A INSCRIÇÃO PERTENCE AO USUÁRIO LOGADO
        if (!inscricao.getUsuario().getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("Esta inscricao pertence a outro usuario.");
        }

        // CANCELA A INSCRIÇÃO
        inscricao.cancelar();

        // SALVA A INSCRIÇÃO CANCELADA NO BANCO
        inscricaoRepository.save(inscricao);
    }

    // MÉTODO PARA LISTAR AS INSCRIÇÕES DE UM USUÁRIO
    public List<Inscricao> listarPorUsuario(String emailUsuario) {

        // BUSCA O USUÁRIO PELO EMAIL
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado."));

        // RETORNA AS INSCRIÇÕES DO USUÁRIO ORDENADAS PELA DATA
        return inscricaoRepository.findByUsuarioIdOrderByDataInscricaoDesc(usuario.getId());
    }

    // MÉTODO PARA LISTAR AS INSCRIÇÕES DE UM EVENTO
    public List<Inscricao> listarPorEvento(Long eventoId) {

        // RETORNA AS INSCRIÇÕES DO EVENTO ORDENADAS PELA DATA
        return inscricaoRepository.findByEventoIdOrderByDataInscricaoDesc(eventoId);
    }
}