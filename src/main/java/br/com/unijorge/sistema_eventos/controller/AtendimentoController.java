// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.controller;

// IMPORTA A CLASSE DE MENSAGEM DE ATENDIMENTO
import br.com.unijorge.sistema_eventos.model.MensagemAtendimento;

// IMPORTA O SERVICE DE ATENDIMENTO
import br.com.unijorge.sistema_eventos.service.AtendimentoService;

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
import org.springframework.web.bind.annotation.RequestParam;

// IMPORTA O REDIRECTATTRIBUTES PARA ENVIAR MENSAGENS APÓS REDIRECIONAMENTO
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// IMPORTA O PRINCIPAL PARA PEGAR O USUÁRIO LOGADO
import java.security.Principal;

// DEFINE A CLASSE COMO CONTROLLER DO SPRING
@Controller
public class AtendimentoController {

    // SERVICE RESPONSÁVEL PELAS REGRAS DO ATENDIMENTO
    private final AtendimentoService atendimentoService;

    // CONSTRUTOR QUE RECEBE O SERVICE DE ATENDIMENTO
    public AtendimentoController(AtendimentoService atendimentoService) {
        this.atendimentoService = atendimentoService;
    }

    // ABRE A PÁGINA DE ATENDIMENTO DO ALUNO
    @GetMapping("/atendimento")
    public String atendimento(Principal principal, Model model) {

        // ENVIA UM OBJETO NOVO DE MENSAGEM PARA O FORMULÁRIO
        model.addAttribute("mensagem", new MensagemAtendimento());

        // LISTA AS MENSAGENS DO USUÁRIO LOGADO
        model.addAttribute("mensagens", atendimentoService.listarPorUsuario(principal.getName()));

        // RETORNA A PÁGINA ATENDIMENTO
        return "atendimento";
    }

    // RECEBE O ENVIO DE UMA MENSAGEM DO ALUNO
    @PostMapping("/atendimento")
    public String enviar(@Valid @ModelAttribute("mensagem") MensagemAtendimento mensagem, BindingResult result,
                         Principal principal, Model model, RedirectAttributes redirectAttributes) {

        // VERIFICA SE EXISTEM ERROS DE VALIDAÇÃO NO FORMULÁRIO
        if (result.hasErrors()) {

            // RECARREGA AS MENSAGENS DO USUÁRIO LOGADO
            model.addAttribute("mensagens", atendimentoService.listarPorUsuario(principal.getName()));

            // RETORNA PARA A PÁGINA DE ATENDIMENTO
            return "atendimento";
        }

        // ENVIA A MENSAGEM PARA O ATENDIMENTO
        atendimentoService.enviar(principal.getName(), mensagem);

        // ADICIONA MENSAGEM DE SUCESSO
        redirectAttributes.addFlashAttribute("sucesso", "Mensagem enviada para atendimento.");

        // REDIRECIONA PARA A PÁGINA DE ATENDIMENTO
        return "redirect:/atendimento";
    }

    // ABRE O PAINEL DE ATENDIMENTO DO ADMINISTRADOR
    @GetMapping("/admin/atendimento")
    public String adminAtendimento(Model model) {

        // LISTA TODAS AS MENSAGENS ENVIADAS
        model.addAttribute("mensagens", atendimentoService.listarTodas());

        // RETORNA A PÁGINA DO ADMINISTRADOR
        return "admin-atendimento";
    }

    // RECEBE A RESPOSTA DO ADMINISTRADOR PARA UMA MENSAGEM
    @PostMapping("/admin/atendimento/{id}/responder")
    public String responder(@PathVariable Long id, @RequestParam String resposta,
                            RedirectAttributes redirectAttributes) {

        // RESPONDE A MENSAGEM PELO ID
        atendimentoService.responder(id, resposta);

        // ADICIONA MENSAGEM DE SUCESSO
        redirectAttributes.addFlashAttribute("sucesso", "Resposta enviada.");

        // REDIRECIONA PARA O PAINEL DE ATENDIMENTO DO ADMIN
        return "redirect:/admin/atendimento";
    }
}