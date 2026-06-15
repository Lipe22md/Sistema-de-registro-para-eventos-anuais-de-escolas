// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.service;

// IMPORTA A CLASSE USUARIO
import br.com.unijorge.sistema_eventos.model.Usuario;

// IMPORTA O REPOSITÓRIO DE USUARIO
import br.com.unijorge.sistema_eventos.repository.UsuarioRepository;

// IMPORTA O CODIFICADOR DE SENHAS
import org.springframework.security.crypto.password.PasswordEncoder;

// IMPORTA A ANOTAÇÃO SERVICE DO SPRING
import org.springframework.stereotype.Service;

// IMPORTA LISTA DO JAVA
import java.util.List;

// DEFINE A CLASSE COMO UM SERVICE DO SPRING
@Service
public class UsuarioService {

    // REPOSITÓRIO RESPONSÁVEL PELOS USUÁRIOS
    private final UsuarioRepository usuarioRepository;

    // RESPONSÁVEL POR CRIPTOGRAFAR A SENHA DO USUÁRIO
    private final PasswordEncoder passwordEncoder;

    // CONSTRUTOR QUE RECEBE O REPOSITÓRIO E O CODIFICADOR DE SENHA
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // MÉTODO PARA CADASTRAR UM NOVO USUÁRIO
    public Usuario cadastrar(Usuario usuario) {

        // VERIFICA SE JÁ EXISTE UM USUÁRIO COM O MESMO EMAIL
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ja existe um usuario cadastrado com este e-mail.");
        }

        // CRIPTOGRAFA A SENHA DO USUÁRIO
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        // SALVA O USUÁRIO NO BANCO DE DADOS
        return usuarioRepository.save(usuario);
    }

    // MÉTODO PARA BUSCAR UM USUÁRIO PELO EMAIL
    public Usuario buscarPorEmail(String email) {

        // PROCURA O USUÁRIO PELO EMAIL OU LANÇA ERRO SE NÃO ENCONTRAR
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado."));
    }

    // MÉTODO PARA LISTAR TODOS OS USUÁRIOS
    public List<Usuario> listarTodos() {

        // RETORNA TODOS OS USUÁRIOS CADASTRADOS
        return usuarioRepository.findAll();
    }
}