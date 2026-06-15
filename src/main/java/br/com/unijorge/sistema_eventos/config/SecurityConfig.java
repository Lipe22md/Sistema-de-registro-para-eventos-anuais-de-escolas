// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.config;

// IMPORTA A CLASSE USUARIO
import br.com.unijorge.sistema_eventos.model.Usuario;

// IMPORTA O REPOSITÓRIO DE USUARIO
import br.com.unijorge.sistema_eventos.repository.UsuarioRepository;

// IMPORTA A ANOTAÇÃO BEAN DO SPRING
import org.springframework.context.annotation.Bean;

// IMPORTA A ANOTAÇÃO CONFIGURATION DO SPRING
import org.springframework.context.annotation.Configuration;

// IMPORTA A CONFIGURAÇÃO DE SEGURANÇA HTTP
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

// IMPORTA A CLASSE USER DO SPRING SECURITY
import org.springframework.security.core.userdetails.User;

// IMPORTA O SERVIÇO DE DETALHES DO USUÁRIO
import org.springframework.security.core.userdetails.UserDetailsService;

// IMPORTA A EXCEÇÃO PARA USUÁRIO NÃO ENCONTRADO
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// IMPORTA O CRIPTOGRAFADOR BCRYPT
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// IMPORTA A INTERFACE DE CRIPTOGRAFIA DE SENHA
import org.springframework.security.crypto.password.PasswordEncoder;

// IMPORTA A CADEIA DE FILTROS DE SEGURANÇA
import org.springframework.security.web.SecurityFilterChain;

// DEFINE ESSA CLASSE COMO UMA CLASSE DE CONFIGURAÇÃO
@Configuration
public class SecurityConfig {

    // CONFIGURA AS REGRAS DE SEGURANÇA DA APLICAÇÃO
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // DESABILITA O CSRF APENAS PARA ROTAS DA API
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))

                // CONFIGURA AS PERMISSÕES DAS ROTAS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/index", "/cadastro", "/login", "/eventos", "/eventos/*", "/inscricao", "/confirmacao",
                                "/css/**", "/js/**", "/img/**", "/favicon.ico"
                        ).permitAll()

                        // PERMITE ACESSO AO ADMIN APENAS PARA USUÁRIOS COM PERFIL ADMIN
                        .requestMatchers("/admin/**", "/api/eventos").hasRole("ADMIN")

                        // EXIGE LOGIN PARA FAZER INSCRIÇÃO EM EVENTOS PELA API
                        .requestMatchers("/api/eventos/*/inscricao").authenticated()

                        // EXIGE LOGIN PARA QUALQUER OUTRA ROTA
                        .anyRequest().authenticated()
                )

                // CONFIGURA A TELA DE LOGIN
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        .defaultSuccessUrl("/eventos", true)
                        .permitAll()
                )

                // CONFIGURA O LOGOUT
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        // RETORNA A CONFIGURAÇÃO DE SEGURANÇA
        return http.build();
    }

    // CARREGA O USUÁRIO PELO EMAIL PARA O SPRING SECURITY
    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return email -> {

            // BUSCA O USUÁRIO NO BANCO PELO EMAIL
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado."));

            // RETORNA OS DADOS DO USUÁRIO PARA AUTENTICAÇÃO
            return User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getSenha())
                    .roles(usuario.getPerfil().name())
                    .build();
        };
    }

    // CRIA O CODIFICADOR DE SENHAS
    @Bean
    public PasswordEncoder passwordEncoder() {

        // USA BCRYPT PARA CRIPTOGRAFAR AS SENHAS
        return new BCryptPasswordEncoder();
    }
}