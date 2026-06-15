// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.controller;

// IMPORTA O SERVICE DE INSCRIÇÃO
import br.com.unijorge.sistema_eventos.service.InscricaoService;

// IMPORTA A ANOTAÇÃO CONTROLLER DO SPRING
import org.springframework.stereotype.Controller;

// IMPORTA O MODEL PARA ENVIAR DADOS PARA A VIEW
import org.springframework.ui.Model;

// IMPORTA AS ANOTAÇÕES DE MAPEAMENTO DO SPRING
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// IMPORTA O REDIRECTATTRIBUTES PARA ENVIAR MENSAGENS APÓS REDIRECIONAMENTO
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// IMPORTA O PRINCIPAL PARA PEGAR O USUÁRIO LOGADO
import java.security.Principal;

// DEFINE A CLASSE COMO CONTROLLER DO SPRING
@Controller
public class InscricaoController {

    // SERVICE RESPONSÁVEL PELAS REGRAS DE INSCRIÇÃO
    private final InscricaoService inscricaoService;

    // CONSTRUTOR QUE RECEBE O SERVICE DE INSCRIÇÃO
    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    // REALIZA A INSCRIÇÃO DO USUÁRIO EM UM EVENTO
    @PostMapping("/eventos/{id}/inscrever")
    public String inscrever(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        try {

            // CHAMA O SERVICE PARA INSCREVER O USUÁRIO LOGADO NO EVENTO
            inscricaoService.inscrever(principal.getName(), id);

            // ADICIONA MENSAGEM DE SUCESSO
            redirectAttributes.addFlashAttribute("sucesso", "Inscrição realizada com sucesso.");

        } catch (IllegalArgumentException erro) {

            // ADICIONA MENSAGEM DE ERRO CASO A INSCRIÇÃO NÃO SEJA REALIZADA
            redirectAttributes.addFlashAttribute("erro", erro.getMessage());
        }

        // REDIRECIONA PARA A PÁGINA DE MINHAS INSCRIÇÕES
        return "redirect:/minhas-inscricoes";
    }

    // ABRE A PÁGINA DE MINHAS INSCRIÇÕES
    @GetMapping("/minhas-inscricoes")
    public String minhasInscricoes(Principal principal, Model model) {

        // ENVIA PARA A VIEW AS INSCRIÇÕES DO USUÁRIO LOGADO
        model.addAttribute("inscricoes", inscricaoService.listarPorUsuario(principal.getName()));

        // RETORNA A PÁGINA DE INSCRIÇÃO
        return "inscricao";
    }

    // CANCELA UMA INSCRIÇÃO DO USUÁRIO
    @PostMapping("/inscricoes/{id}/cancelar")
    public String cancelar(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        try {

            // CHAMA O SERVICE PARA CANCELAR A INSCRIÇÃO DO USUÁRIO LOGADO
            inscricaoService.cancelar(id, principal.getName());

            // ADICIONA MENSAGEM DE SUCESSO
            redirectAttributes.addFlashAttribute("sucesso", "Inscrição cancelada.");

        } catch (IllegalArgumentException erro) {

            // ADICIONA MENSAGEM DE ERRO CASO NÃO CONSIGA CANCELAR
            redirectAttributes.addFlashAttribute("erro", erro.getMessage());
        }

        // REDIRECIONA PARA A PÁGINA DE MINHAS INSCRIÇÕES
        return "redirect:/minhas-inscricoes";
    }
}