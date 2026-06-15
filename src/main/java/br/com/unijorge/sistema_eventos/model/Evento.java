// PACOTE DA CLASSE
package br.com.unijorge.sistema_eventos.model;

// IMPORTA ANOTAÇÃO PARA IGNORAR CAMPOS NO JSON
import com.fasterxml.jackson.annotation.JsonIgnore;

// IMPORTA ANOTAÇÕES DO JPA
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

// IMPORTA ANOTAÇÕES DE VALIDAÇÃO
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// IMPORTA ANOTAÇÕES DO LOMBOK
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// IMPORTA FORMATAÇÃO DE DATA E HORA DO SPRING
import org.springframework.format.annotation.DateTimeFormat;

// IMPORTA CLASSE PARA DATA E HORA
import java.time.LocalDateTime;

// IMPORTA LISTAS
import java.util.ArrayList;
import java.util.List;

// DEFINE A CLASSE COMO ENTIDADE DO BANCO DE DADOS
@Entity

// DEFINE O NOME DA TABELA NO BANCO
@Table(name = "eventos")

// GERA OS MÉTODOS GET AUTOMATICAMENTE
@Getter

// GERA OS MÉTODOS SET AUTOMATICAMENTE
@Setter

// GERA CONSTRUTOR SEM ARGUMENTOS
@NoArgsConstructor

// GERA CONSTRUTOR COM TODOS OS ARGUMENTOS
@AllArgsConstructor
public class Evento {

    // CHAVE PRIMÁRIA DA TABELA
    @Id

    // GERA O ID AUTOMATICAMENTE
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // VALIDA QUE O TÍTULO NÃO PODE FICAR EM BRANCO
    @NotBlank(message = "O titulo e obrigatorio")

    // DEFINE O TAMANHO MÍNIMO E MÁXIMO DO TÍTULO
    @Size(min = 3, max = 120, message = "O titulo deve ter entre 3 e 120 caracteres")

    // CONFIGURA A COLUNA TÍTULO NO BANCO
    @Column(nullable = false, length = 120)
    private String titulo;

    // VALIDA QUE A DESCRIÇÃO NÃO PODE FICAR EM BRANCO
    @NotBlank(message = "A descricao e obrigatoria")

    // DEFINE O TAMANHO MÍNIMO E MÁXIMO DA DESCRIÇÃO
    @Size(min = 10, max = 1000, message = "A descricao deve ter entre 10 e 1000 caracteres")

    // CONFIGURA A COLUNA DESCRIÇÃO NO BANCO
    @Column(nullable = false, length = 1000)
    private String descricao;

    // VALIDA QUE O LOCAL NÃO PODE FICAR EM BRANCO
    @NotBlank(message = "O local e obrigatorio")

    // CONFIGURA A COLUNA LOCAL NO BANCO
    @Column(nullable = false, length = 120)
    private String local;

    // VALIDA QUE A DATA E HORA NÃO PODEM SER NULAS
    @NotNull(message = "A data e hora do evento sao obrigatorias")

    // VALIDA QUE A DATA DO EVENTO NÃO PODE ESTAR NO PASSADO
    @FutureOrPresent(message = "A data do evento nao pode estar no passado")

    // DEFINE O FORMATO DA DATA E HORA RECEBIDA DO FORMULÁRIO
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")

    // CONFIGURA A COLUNA DATA E HORA NO BANCO
    @Column(nullable = false)
    private LocalDateTime dataHora;

    // VALIDA QUE A QUANTIDADE DE VAGAS NÃO PODE SER NULA
    @NotNull(message = "A quantidade de vagas e obrigatoria")

    // VALIDA QUE O EVENTO PRECISA TER PELO MENOS UMA VAGA
    @Min(value = 1, message = "O evento precisa ter pelo menos uma vaga")

    // CONFIGURA A COLUNA VAGAS TOTAIS NO BANCO
    @Column(nullable = false)
    private Integer vagasTotais;

    // CONFIGURA A COLUNA ATIVO NO BANCO
    @Column(nullable = false)
    private Boolean ativo = true;

    // CONFIGURA A DATA DE CRIAÇÃO DO EVENTO
    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    // IGNORA A LISTA DE INSCRIÇÕES NO JSON
    @JsonIgnore

    // RELACIONAMENTO UM EVENTO PARA MUITAS INSCRIÇÕES
    @OneToMany(mappedBy = "evento")
    private List<Inscricao> inscricoes = new ArrayList<>();

    // EXECUTA ANTES DE SALVAR O EVENTO NO BANCO
    @PrePersist
    public void prePersist() {

        // DEFINE A DATA DE CRIAÇÃO COMO A DATA E HORA ATUAL
        criadoEm = LocalDateTime.now();

        // VERIFICA SE O STATUS ATIVO ESTÁ NULO
        if (ativo == null) {

            // DEFINE O EVENTO COMO ATIVO
            ativo = true;
        }
    }

    // INDICA QUE ESSE MÉTODO NÃO SERÁ SALVO COMO COLUNA NO BANCO
    @Transient
    public long getInscricoesAtivas() {

        // CONTA APENAS AS INSCRIÇÕES COM STATUS ATIVA
        return inscricoes.stream()
                .filter(inscricao -> inscricao.getStatus() == Inscricao.StatusInscricao.ATIVA)
                .count();
    }

    // INDICA QUE ESSE MÉTODO NÃO SERÁ SALVO COMO COLUNA NO BANCO
    @Transient
    public long getVagasDisponiveis() {

        // VERIFICA SE A QUANTIDADE DE VAGAS ESTÁ NULA
        if (vagasTotais == null) {

            // RETORNA ZERO CASO NÃO EXISTA QUANTIDADE DE VAGAS
            return 0;
        }

        // CALCULA AS VAGAS DISPONÍVEIS
        return vagasTotais - getInscricoesAtivas();
    }

    // INDICA QUE ESSE MÉTODO NÃO SERÁ SALVO COMO COLUNA NO BANCO
    @Transient
    public boolean temVagasDisponiveis() {

        // VERIFICA SE AINDA EXISTEM VAGAS DISPONÍVEIS
        return getVagasDisponiveis() > 0;
    }
}