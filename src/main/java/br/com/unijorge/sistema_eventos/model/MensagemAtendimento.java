// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.model;

// IMPORTA ANOTAÇÕES DO JPA
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

// IMPORTA ANOTAÇÕES DE VALIDAÇÃO
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// IMPORTA ANOTAÇÕES DO LOMBOK
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// IMPORTA CLASSE PARA DATA E HORA
import java.time.LocalDateTime;

// DEFINE A CLASSE COMO ENTIDADE DO BANCO DE DADOS
@Entity

// DEFINE O NOME DA TABELA NO BANCO
@Table(name = "mensagens_atendimento")

// GERA OS MÉTODOS GET AUTOMATICAMENTE
@Getter

// GERA OS MÉTODOS SET AUTOMATICAMENTE
@Setter

// GERA CONSTRUTOR SEM ARGUMENTOS
@NoArgsConstructor

// GERA CONSTRUTOR COM TODOS OS ARGUMENTOS
@AllArgsConstructor
public class MensagemAtendimento {

    // CHAVE PRIMÁRIA DA TABELA
    @Id

    // GERA O ID AUTOMATICAMENTE
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELACIONAMENTO MUITAS MENSAGENS PARA UM USUÁRIO
    @ManyToOne(fetch = FetchType.LAZY, optional = false)

    // DEFINE A COLUNA DE LIGAÇÃO COM A TABELA DE USUÁRIOS
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // VALIDA QUE O ASSUNTO NÃO PODE FICAR EM BRANCO
    @NotBlank(message = "O assunto e obrigatorio")

    // DEFINE O TAMANHO MÍNIMO E MÁXIMO DO ASSUNTO
    @Size(min = 3, max = 120, message = "O assunto deve ter entre 3 e 120 caracteres")

    // CONFIGURA A COLUNA ASSUNTO NO BANCO
    @Column(nullable = false, length = 120)
    private String assunto;

    // VALIDA QUE A MENSAGEM NÃO PODE FICAR EM BRANCO
    @NotBlank(message = "A mensagem e obrigatoria")

    // DEFINE O TAMANHO MÍNIMO E MÁXIMO DA MENSAGEM
    @Size(min = 10, max = 1000, message = "A mensagem deve ter entre 10 e 1000 caracteres")

    // CONFIGURA A COLUNA CONTEÚDO NO BANCO
    @Column(nullable = false, length = 1000)
    private String conteudo;

    // CONFIGURA A COLUNA RESPOSTA NO BANCO
    @Column(length = 1000)
    private String resposta;

    // SALVA O ENUM COMO TEXTO NO BANCO
    @Enumerated(EnumType.STRING)

    // CONFIGURA A COLUNA STATUS NO BANCO
    @Column(nullable = false, length = 20)
    private StatusMensagem status = StatusMensagem.ABERTA;

    // CONFIGURA A DATA DE ENVIO DA MENSAGEM
    @Column(nullable = false, updatable = false)
    private LocalDateTime enviadaEm;

    // DATA EM QUE A MENSAGEM FOI RESPONDIDA
    private LocalDateTime respondidaEm;

    // EXECUTA ANTES DE SALVAR A MENSAGEM NO BANCO
    @PrePersist
    public void prePersist() {

        // DEFINE A DATA DE ENVIO COMO A DATA E HORA ATUAL
        enviadaEm = LocalDateTime.now();

        // VERIFICA SE O STATUS ESTÁ NULO
        if (status == null) {

            // DEFINE O STATUS PADRÃO COMO ABERTA
            status = StatusMensagem.ABERTA;
        }
    }

    // MÉTODO PARA RESPONDER UMA MENSAGEM
    public void responder(String resposta) {

        // DEFINE O TEXTO DA RESPOSTA
        this.resposta = resposta;

        // ALTERA O STATUS PARA RESPONDIDA
        this.status = StatusMensagem.RESPONDIDA;

        // DEFINE A DATA DA RESPOSTA COMO A DATA E HORA ATUAL
        this.respondidaEm = LocalDateTime.now();
    }

    // ENUM COM OS STATUS DA MENSAGEM
    public enum StatusMensagem {

        // MENSAGEM AINDA ABERTA
        ABERTA,

        // MENSAGEM JÁ RESPONDIDA
        RESPONDIDA,

        // MENSAGEM FECHADA
        FECHADA
    }
}