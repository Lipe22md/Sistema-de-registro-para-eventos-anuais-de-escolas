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

// IMPORTA A ANOTAÇÃO CONTROLLER DO SPRING
import org.springframework.stereotype.Controller;

// IMPORTA O MODEL PARA ENVIAR DADOS PARA A VIEW
import org.springframework.ui.Model;

// IMPORTA O BINDINGRESULT PARA VERIFICAR ERROS DE VALIDAÇÃO
import org.springframework.validation.BindingResult;

// IMPORTA AS ANOTAÇÕES DE MAPEAMENTO DO SPRING
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// IMPORTA O REDIRECTATTRIBUTES PARA ENVIAR MENSAGENS APÓS REDIRECIONAMENTO
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// DEFINE A CLASSE COMO CONTROLLER DO SPRING
@Controller
public class EventoController {

    // SERVICE RESPONSÁVEL PELAS REGRAS DE EVENTO
    private final EventoService eventoService;

    // SERVICE RESPONSÁVEL PELAS REGRAS DE INSCRIÇÃO
    private final InscricaoService inscricaoService;

    // CONSTRUTOR QUE RECEBE OS SERVICES
    public EventoController(EventoService eventoService, InscricaoService inscricaoService) {
        this.eventoService = eventoService;
        this.inscricaoService = inscricaoService;
    }

    // ABRE A PÁGINA DE EVENTOS
    @GetMapping("/eventos")
    public String eventos(Model model) {

        // ENVIA A LISTA DE EVENTOS ATIVOS PARA A VIEW
        model.addAttribute("eventos", eventoService.listarAtivos());

        // RETORNA A PÁGINA EVENTOS
        return "eventos";
    }

    // ABRE A PÁGINA DE DETALHES DE UM EVENTO
    @GetMapping("/eventos/{id}")
    public String detalhes(@PathVariable Long id, Model model) {

        // ENVIA O EVENTO ENCONTRADO PELO ID PARA A VIEW
        model.addAttribute("evento", eventoService.buscarPorId(id));

        // ENVIA AS INSCRIÇÕES DO EVENTO PARA A VIEW
        model.addAttribute("inscricoes", inscricaoService.listarPorEvento(id));

        // RETORNA A PÁGINA DE DETALHE DO EVENTO
        return "evento-detalhe";
    }

    // ABRE A PÁGINA ADMINISTRATIVA DE EVENTOS
    @GetMapping("/admin/eventos")
    public String adminEventos(Model model) {

        // ENVIA TODOS OS EVENTOS PARA A VIEW
        model.addAttribute("eventos", eventoService.listarTodos());

        // RETORNA A PÁGINA ADMINISTRATIVA DE EVENTOS
        return "admin-eventos";
    }

    // ABRE O FORMULÁRIO PARA CADASTRAR NOVO EVENTO
    @GetMapping("/admin/eventos/novo")
    public String novoEvento(Model model) {

        // ENVIA UM OBJETO EVENTO VAZIO PARA O FORMULÁRIO
        model.addAttribute("evento", new Evento());

        // RETORNA A PÁGINA DO FORMULÁRIO DE EVENTO
        return "evento-form";
    }

    // SALVA UM NOVO EVENTO
    @PostMapping("/admin/eventos")
    public String salvarEvento(@Valid @ModelAttribute Evento evento, BindingResult result) {

        // VERIFICA SE EXISTEM ERROS DE VALIDAÇÃO
        if (result.hasErrors()) {

            // RETORNA PARA O FORMULÁRIO CASO TENHA ERRO
            return "evento-form";
        }

        // SALVA O EVENTO NO BANCO
        eventoService.salvar(evento);

        // REDIRECIONA PARA A LISTA ADMINISTRATIVA DE EVENTOS
        return "redirect:/admin/eventos";
    }

    // ABRE O FORMULÁRIO PARA EDITAR UM EVENTO
    @GetMapping("/admin/eventos/{id}/editar")
    public String editarEvento(@PathVariable Long id, Model model) {

        // ENVIA O EVENTO ENCONTRADO PELO ID PARA O FORMULÁRIO
        model.addAttribute("evento", eventoService.buscarPorId(id));

        // RETORNA A PÁGINA DO FORMULÁRIO DE EVENTO
        return "evento-form";
    }

    // ATUALIZA UM EVENTO EXISTENTE
    @PostMapping("/admin/eventos/{id}")
    public String atualizarEvento(@PathVariable Long id, @Valid @ModelAttribute Evento evento, BindingResult result) {

        // VERIFICA SE EXISTEM ERROS DE VALIDAÇÃO
        if (result.hasErrors()) {

            // RETORNA PARA O FORMULÁRIO CASO TENHA ERRO
            return "evento-form";
        }

        // DEFINE O ID DO EVENTO QUE SERÁ ATUALIZADO
        evento.setId(id);

        // SALVA AS ALTERAÇÕES DO EVENTO
        eventoService.salvar(evento);

        // REDIRECIONA PARA A LISTA ADMINISTRATIVA DE EVENTOS
        return "redirect:/admin/eventos";
    }

    // REMOVE UM EVENTO DA LISTA PÚBLICA
    @PostMapping("/admin/eventos/{id}/excluir")
    public String excluirEvento(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        // EXCLUI O EVENTO DE FORMA LÓGICA
        eventoService.excluir(id);

        // ADICIONA MENSAGEM DE SUCESSO
        redirectAttributes.addFlashAttribute("sucesso", "Evento removido da lista publica.");

        // REDIRECIONA PARA A LISTA ADMINISTRATIVA DE EVENTOS
        return "redirect:/admin/eventos";
    }
}