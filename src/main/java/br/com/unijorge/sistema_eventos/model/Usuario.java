// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.model;

// IMPORTA ANOTAÇÃO PARA IGNORAR CAMPOS NO JSON
import com.fasterxml.jackson.annotation.JsonIgnore;

// IMPORTA ANOTAÇÕES DO JPA
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

// IMPORTA ANOTAÇÕES DE VALIDAÇÃO
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// IMPORTA ANOTAÇÕES DO LOMBOK
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// IMPORTA CLASSE PARA DATA E HORA
import java.time.LocalDateTime;

// IMPORTA LISTAS
import java.util.ArrayList;
import java.util.List;

// DEFINE A CLASSE COMO ENTIDADE DO BANCO DE DADOS
@Entity

// DEFINE O NOME DA TABELA NO BANCO
@Table(name = "usuarios")

// GERA OS MÉTODOS GET AUTOMATICAMENTE
@Getter

// GERA OS MÉTODOS SET AUTOMATICAMENTE
@Setter

// GERA CONSTRUTOR SEM ARGUMENTOS
@NoArgsConstructor

// GERA CONSTRUTOR COM TODOS OS ARGUMENTOS
@AllArgsConstructor
public class Usuario {

    // CHAVE PRIMÁRIA DA TABELA
    @Id

    // GERA O ID AUTOMATICAMENTE
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // VALIDA QUE O NOME NÃO PODE FICAR EM BRANCO
    @NotBlank(message = "O nome e obrigatorio")

    // DEFINE O TAMANHO MÍNIMO E MÁXIMO DO NOME
    @Size(min = 3, max = 120, message = "O nome deve ter entre 3 e 120 caracteres")

    // CONFIGURA A COLUNA NOME NO BANCO
    @Column(nullable = false, length = 120)
    private String nome;

    // VALIDA QUE O EMAIL NÃO PODE FICAR EM BRANCO
    @NotBlank(message = "O e-mail e obrigatorio")

    // VALIDA O FORMATO DO EMAIL
    @Email(message = "Informe um e-mail valido")

    // CONFIGURA A COLUNA EMAIL COMO ÚNICA
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    // VALIDA QUE A SENHA NÃO PODE FICAR EM BRANCO
    @NotBlank(message = "A senha e obrigatoria")

    // CONFIGURA A COLUNA SENHA
    @Column(nullable = false)
    private String senha;

    // CONFIGURA A COLUNA TELEFONE
    @Column(length = 20)
    private String telefone;

    // SALVA O ENUM COMO TEXTO NO BANCO
    @Enumerated(EnumType.STRING)

    // CONFIGURA A COLUNA PERFIL
    @Column(nullable = false, length = 20)
    private PerfilUsuario perfil = PerfilUsuario.PARTICIPANTE;

    // CONFIGURA A DATA DE CRIAÇÃO DO USUÁRIO
    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    // IGNORA A LISTA DE INSCRIÇÕES NO JSON
    @JsonIgnore

    // RELACIONAMENTO UM USUÁRIO PARA MUITAS INSCRIÇÕES
    @OneToMany(mappedBy = "usuario")
    private List<Inscricao> inscricoes = new ArrayList<>();

    // IGNORA A LISTA DE MENSAGENS NO JSON
    @JsonIgnore

    // RELACIONAMENTO UM USUÁRIO PARA MUITAS MENSAGENS DE ATENDIMENTO
    @OneToMany(mappedBy = "usuario")
    private List<MensagemAtendimento> mensagens = new ArrayList<>();

    // EXECUTA ANTES DE SALVAR O USUÁRIO NO BANCO
    @PrePersist
    public void prePersist() {

        // DEFINE A DATA DE CRIAÇÃO COMO A DATA E HORA ATUAL
        criadoEm = LocalDateTime.now();

        // VERIFICA SE O PERFIL ESTÁ NULO
        if (perfil == null) {

            // DEFINE O PERFIL PADRÃO COMO PARTICIPANTE
            perfil = PerfilUsuario.PARTICIPANTE;
        }
    }

    // ENUM COM OS TIPOS DE PERFIL DO USUÁRIO
    public enum PerfilUsuario {

        // PERFIL DE ADMINISTRADOR
        ADMIN,

        // PERFIL DE PARTICIPANTE
        PARTICIPANTE
    }
}