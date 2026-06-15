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
import jakarta.persistence.UniqueConstraint;

// IMPORTA ANOTAÇÕES DO LOMBOK
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// IMPORTA CLASSE PARA DATA E HORA
import java.time.LocalDateTime;

// DEFINE A CLASSE COMO ENTIDADE DO BANCO DE DADOS
@Entity

// DEFINE A TABELA INSCRICOES E A REGRA DE INSCRIÇÃO ÚNICA POR USUÁRIO E EVENTO
@Table(
        name = "inscricoes",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_inscricao_usuario_evento",
                columnNames = {"usuario_id", "evento_id"}
        )
)

// GERA OS MÉTODOS GET AUTOMATICAMENTE
@Getter

// GERA OS MÉTODOS SET AUTOMATICAMENTE
@Setter

// GERA CONSTRUTOR SEM ARGUMENTOS
@NoArgsConstructor

// GERA CONSTRUTOR COM TODOS OS ARGUMENTOS
@AllArgsConstructor
public class Inscricao {

    // CHAVE PRIMÁRIA DA TABELA
    @Id

    // GERA O ID AUTOMATICAMENTE
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELACIONAMENTO MUITAS INSCRIÇÕES PARA UM USUÁRIO
    @ManyToOne(fetch = FetchType.LAZY, optional = false)

    // DEFINE A COLUNA USUARIO_ID NO BANCO
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // RELACIONAMENTO MUITAS INSCRIÇÕES PARA UM EVENTO
    @ManyToOne(fetch = FetchType.LAZY, optional = false)

    // DEFINE A COLUNA EVENTO_ID NO BANCO
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    // SALVA O ENUM COMO TEXTO NO BANCO
    @Enumerated(EnumType.STRING)

    // CONFIGURA A COLUNA STATUS NO BANCO
    @Column(nullable = false, length = 20)
    private StatusInscricao status = StatusInscricao.ATIVA;

    // CONFIGURA A DATA DA INSCRIÇÃO
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataInscricao;

    // GUARDA A DATA DE CANCELAMENTO DA INSCRIÇÃO
    private LocalDateTime dataCancelamento;

    // EXECUTA ANTES DE SALVAR A INSCRIÇÃO NO BANCO
    @PrePersist
    public void prePersist() {

        // DEFINE A DATA DA INSCRIÇÃO COMO A DATA E HORA ATUAL
        dataInscricao = LocalDateTime.now();

        // VERIFICA SE O STATUS ESTÁ NULO
        if (status == null) {

            // DEFINE O STATUS PADRÃO COMO ATIVA
            status = StatusInscricao.ATIVA;
        }
    }

    // MÉTODO PARA CANCELAR A INSCRIÇÃO
    public void cancelar() {

        // ALTERA O STATUS PARA CANCELADA
        status = StatusInscricao.CANCELADA;

        // DEFINE A DATA DE CANCELAMENTO COMO A DATA E HORA ATUAL
        dataCancelamento = LocalDateTime.now();
    }

    // ENUM COM OS STATUS DA INSCRIÇÃO
    public enum StatusInscricao {

        // INSCRIÇÃO ATIVA
        ATIVA,

        // INSCRIÇÃO CANCELADA
        CANCELADA
    }
}