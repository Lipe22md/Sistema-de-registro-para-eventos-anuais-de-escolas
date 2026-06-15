// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.controller;

// IMPORTA A CLASSE USUARIO
import br.com.unijorge.sistema_eventos.model.Usuario;

// IMPORTA O SERVICE DE EVENTO
import br.com.unijorge.sistema_eventos.service.EventoService;

// IMPORTA O SERVICE DE USUARIO
import br.com.unijorge.sistema_eventos.service.UsuarioService;

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
import org.springframework.web.bind.annotation.PostMapping;

// IMPORTA O REDIRECTATTRIBUTES PARA ENVIAR MENSAGENS APÓS REDIRECIONAMENTO
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// DEFINE A CLASSE COMO CONTROLLER DO SPRING
@Controller
public class UsuarioController {

    // SERVICE RESPONSÁVEL PELAS REGRAS DE USUÁRIO
    private final UsuarioService usuarioService;

    // SERVICE RESPONSÁVEL PELAS REGRAS DE EVENTO
    private final EventoService eventoService;

    // CONSTRUTOR QUE RECEBE OS SERVICES
    public UsuarioController(UsuarioService usuarioService, EventoService eventoService) {
        this.usuarioService = usuarioService;
        this.eventoService = eventoService;
    }

    // ABRE A PÁGINA INICIAL
    @GetMapping("/")
    public String index(Model model) {

        // ENVIA A LISTA DE PRÓXIMOS EVENTOS PARA A VIEW
        model.addAttribute("eventos", eventoService.listarProximos());

        // RETORNA A PÁGINA INDEX
        return "index";
    }

    // ABRE A PÁGINA DE LOGIN
    @GetMapping("/login")
    public String login() {

        // RETORNA A PÁGINA LOGIN
        return "login";
    }

    // ABRE A PÁGINA DE CADASTRO
    @GetMapping("/cadastro")
    public String cadastro(Model model) {

        // ENVIA UM OBJETO USUÁRIO VAZIO PARA O FORMULÁRIO
        model.addAttribute("usuario", new Usuario());

        // RETORNA A PÁGINA CADASTRO
        return "cadastro";
    }

    // RECEBE OS DADOS DO FORMULÁRIO DE CADASTRO
    @PostMapping("/cadastro")
    public String cadastrar(@Valid @ModelAttribute Usuario usuario, BindingResult result,
                            RedirectAttributes redirectAttributes) {

        // VERIFICA SE EXISTEM ERROS DE VALIDAÇÃO
        if (result.hasErrors()) {

            // RETORNA PARA A PÁGINA DE CADASTRO CASO TENHA ERRO
            return "cadastro";
        }

        // TENTA CADASTRAR O USUÁRIO
        try {

            // CHAMA O SERVICE PARA CADASTRAR O USUÁRIO
            usuarioService.cadastrar(usuario);

            // ADICIONA MENSAGEM DE SUCESSO
            redirectAttributes.addFlashAttribute("sucesso", "Cadastro realizado. Agora faca login.");

            // REDIRECIONA PARA A PÁGINA DE LOGIN
            return "redirect:/login";

        } catch (IllegalArgumentException erro) {

            // ADICIONA O ERRO NO CAMPO EMAIL
            result.rejectValue("email", null, erro.getMessage());

            // RETORNA PARA A PÁGINA DE CADASTRO
            return "cadastro";
        }
    }
}