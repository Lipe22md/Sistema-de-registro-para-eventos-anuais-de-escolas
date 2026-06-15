// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.controller;

// IMPORTA A CLASSE EVENTO
import br.com.unijorge.sistema_eventos.model.Evento;

// IMPORTA O SERVICE DE EVENTO
import br.com.unijorge.sistema_eventos.service.EventoService;

// IMPORTA O SERVICE DE INSCRIÇÃO
import br.com.unijorge.sistema_eventos.service.InscricaoService;

// IMPORTA A ANOTAÇÃO DE VALIDAÇÃO
import jakarta.validation.Valid;

// IMPORTA O RESPONSEENTITY PARA RETORNAR RESPOSTAS HTTP
import org.springframework.http.ResponseEntity;

// IMPORTA AS ANOTAÇÕES DE MAPEAMENTO DO SPRING
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// IMPORTA O PRINCIPAL PARA PEGAR O USUÁRIO LOGADO
import java.security.Principal;

// IMPORTA LISTA DO JAVA
import java.util.List;

// DEFINE A CLASSE COMO CONTROLLER REST
@RestController

// DEFINE O CAMINHO BASE DA API DE EVENTOS
@RequestMapping("/api/eventos")
public class EventoApiController {

    // SERVICE RESPONSÁVEL PELAS REGRAS DE EVENTO
    private final EventoService eventoService;

    // SERVICE RESPONSÁVEL PELAS REGRAS DE INSCRIÇÃO
    private final InscricaoService inscricaoService;

    // CONSTRUTOR QUE RECEBE OS SERVICES
    public EventoApiController(EventoService eventoService, InscricaoService inscricaoService) {
        this.eventoService = eventoService;
        this.inscricaoService = inscricaoService;
    }

    // LISTA OS EVENTOS ATIVOS
    @GetMapping
    public List<Evento> listar() {
        return eventoService.listarAtivos();
    }

    // BUSCA UM EVENTO PELO ID
    @GetMapping("/{id}")
    public Evento buscar(@PathVariable Long id) {
        return eventoService.buscarPorId(id);
    }

    // CADASTRA UM NOVO EVENTO
    @PostMapping
    public Evento cadastrar(@Valid @RequestBody Evento evento) {
        return eventoService.salvar(evento);
    }

    // REALIZA A INSCRIÇÃO DO USUÁRIO LOGADO EM UM EVENTO
    @PostMapping("/{id}/inscricao")
    public ResponseEntity<String> inscrever(@PathVariable Long id, Principal principal) {
        inscricaoService.inscrever(principal.getName(), id);
        return ResponseEntity.ok("Inscricao realizada com sucesso.");
    }
}