// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.config;

// IMPORTA A CLASSE EVENTO
import br.com.unijorge.sistema_eventos.model.Evento;

// IMPORTA A CLASSE USUARIO
import br.com.unijorge.sistema_eventos.model.Usuario;

// IMPORTA O PERFIL DO USUÁRIO
import br.com.unijorge.sistema_eventos.model.Usuario.PerfilUsuario;

// IMPORTA O REPOSITÓRIO DE EVENTOS
import br.com.unijorge.sistema_eventos.repository.EventoRepository;

// IMPORTA O REPOSITÓRIO DE USUÁRIOS
import br.com.unijorge.sistema_eventos.repository.UsuarioRepository;

// IMPORTA O COMMANDLINERUNNER DO SPRING BOOT
import org.springframework.boot.CommandLineRunner;

// IMPORTA A ANOTAÇÃO BEAN DO SPRING
import org.springframework.context.annotation.Bean;

// IMPORTA A ANOTAÇÃO CONFIGURATION DO SPRING
import org.springframework.context.annotation.Configuration;

// IMPORTA O CODIFICADOR DE SENHAS
import org.springframework.security.crypto.password.PasswordEncoder;

// IMPORTA LOCALDATETIME PARA TRABALHAR COM DATA E HORA
import java.time.LocalDateTime;

// DEFINE ESSA CLASSE COMO UMA CLASSE DE CONFIGURAÇÃO
@Configuration
public class DadosIniciais {

    // CRIA UM BEAN PARA EXECUTAR DADOS INICIAIS AO INICIAR A APLICAÇÃO
    @Bean
    public CommandLineRunner criarDadosIniciais(UsuarioRepository usuarioRepository,
                                                EventoRepository eventoRepository,
                                                PasswordEncoder passwordEncoder) {
        return args -> {

            // VERIFICA SE O USUÁRIO ADMIN AINDA NÃO EXISTE
            if (!usuarioRepository.existsByEmail("admin@email.com")) {

                // CRIA UM NOVO USUÁRIO ADMINISTRADOR
                Usuario admin = new Usuario();

                // DEFINE O NOME DO ADMINISTRADOR
                admin.setNome("Administrador");

                // DEFINE O EMAIL DO ADMINISTRADOR
                admin.setEmail("admin@email.com");

                // DEFINE A SENHA CRIPTOGRAFADA DO ADMINISTRADOR
                admin.setSenha(passwordEncoder.encode("123456"));

                // DEFINE O TELEFONE DO ADMINISTRADOR
                admin.setTelefone("71999999999");

                // DEFINE O PERFIL DO USUÁRIO COMO ADMIN
                admin.setPerfil(PerfilUsuario.ADMIN);

                // SALVA O ADMINISTRADOR NO BANCO DE DADOS
                usuarioRepository.save(admin);
            }

            // VERIFICA SE AINDA NÃO EXISTEM EVENTOS CADASTRADOS
            if (eventoRepository.count() == 0) {

                // CRIA O EVENTO FEIRA DE CIÊNCIAS
                Evento feira = new Evento();

                // DEFINE O TÍTULO DO EVENTO
                feira.setTitulo("Feira de Ciencias");

                // DEFINE A DESCRIÇÃO DO EVENTO
                feira.setDescricao("Apresentacao de trabalhos dos alunos para a comunidade escolar.");

                // DEFINE O LOCAL DO EVENTO
                feira.setLocal("Auditorio principal");

                // DEFINE A DATA E HORA DO EVENTO
                feira.setDataHora(LocalDateTime.now().plusDays(10));

                // DEFINE A QUANTIDADE TOTAL DE VAGAS
                feira.setVagasTotais(40);

                // SALVA O EVENTO NO BANCO DE DADOS
                eventoRepository.save(feira);

                // CRIA O EVENTO MOSTRA CULTURAL
                Evento cultura = new Evento();

                // DEFINE O TÍTULO DO EVENTO
                cultura.setTitulo("Mostra Cultural");

                // DEFINE A DESCRIÇÃO DO EVENTO
                cultura.setDescricao("Evento anual com apresentacoes artisticas, musica e exposicoes.");

                // DEFINE O LOCAL DO EVENTO
                cultura.setLocal("Quadra da escola");

                // DEFINE A DATA E HORA DO EVENTO
                cultura.setDataHora(LocalDateTime.now().plusDays(20));

                // DEFINE A QUANTIDADE TOTAL DE VAGAS
                cultura.setVagasTotais(60);

                // SALVA O EVENTO NO BANCO DE DADOS
                eventoRepository.save(cultura);
            }
        };
    }
}