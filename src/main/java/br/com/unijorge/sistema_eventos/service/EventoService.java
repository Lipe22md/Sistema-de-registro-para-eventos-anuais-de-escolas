// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.service;

// IMPORTA A CLASSE EVENTO
import br.com.unijorge.sistema_eventos.model.Evento;

// IMPORTA O REPOSITÓRIO DE EVENTO
import br.com.unijorge.sistema_eventos.repository.EventoRepository;

// IMPORTA A ANOTAÇÃO SERVICE DO SPRING
import org.springframework.stereotype.Service;

// IMPORTA LOCALDATETIME PARA TRABALHAR COM DATA E HORA
import java.time.LocalDateTime;

// IMPORTA LISTA DO JAVA
import java.util.List;

// DEFINE A CLASSE COMO UM SERVICE DO SPRING
@Service
public class EventoService {

    // REPOSITÓRIO RESPONSÁVEL PELO ACESSO AOS EVENTOS NO BANCO
    private final EventoRepository eventoRepository;

    // CONSTRUTOR QUE RECEBE O REPOSITÓRIO DE EVENTOS
    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    // LISTA TODOS OS EVENTOS ATIVOS ORDENADOS PELA DATA
    public List<Evento> listarAtivos() {
        return eventoRepository.findByAtivoTrueOrderByDataHoraAsc();
    }

    // LISTA OS PRÓXIMOS EVENTOS ATIVOS A PARTIR DA DATA E HORA ATUAL
    public List<Evento> listarProximos() {
        return eventoRepository.findByAtivoTrueAndDataHoraAfterOrderByDataHoraAsc(LocalDateTime.now());
    }

    // LISTA TODOS OS EVENTOS CADASTRADOS
    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    // BUSCA UM EVENTO PELO ID
    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento nao encontrado."));
    }

    // SALVA OU ATUALIZA UM EVENTO NO BANCO DE DADOS
    public Evento salvar(Evento evento) {
        return eventoRepository.save(evento);
    }

    // EXCLUI UM EVENTO DE FORMA LÓGICA, MARCANDO COMO INATIVO
    public void excluir(Long id) {
        Evento evento = buscarPorId(id);
        evento.setAtivo(false);
        eventoRepository.save(evento);
    }
}