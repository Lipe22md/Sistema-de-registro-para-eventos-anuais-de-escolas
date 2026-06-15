// PACOTE DA INTERFACE
package br.com.unijorge.sistema_eventos.repository;

// IMPORTA A CLASSE USUARIO
import br.com.unijorge.sistema_eventos.model.Usuario;

// IMPORTA O JPAREPOSITORY DO SPRING DATA JPA
import org.springframework.data.jpa.repository.JpaRepository;

// IMPORTA OPTIONAL DO JAVA
import java.util.Optional;

// INTERFACE RESPONSÁVEL PELO ACESSO AOS DADOS DOS USUÁRIOS
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // BUSCA UM USUÁRIO PELO EMAIL
    Optional<Usuario> findByEmail(String email);

    // VERIFICA SE JÁ EXISTE UM USUÁRIO COM ESSE EMAIL
    boolean existsByEmail(String email);
}