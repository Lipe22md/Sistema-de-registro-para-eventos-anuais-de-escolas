package trabalho.apiReade.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Date diaInicio;
    private Date diaFim;
    private Date inscricaoInicio;
    private Date inscricaoFim;
    private String descricao;
    private Float tempoDisponivel;
    private Boolean disponibilidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDiaInicio() {
        return diaInicio;
    }

    public void setDiaInicio(Date diaInicio) {
        this.diaInicio = diaInicio;
    }

    public Date getDiaFim() {
        return diaFim;
    }

    public void setDiaFim(Date diaFim) {
        this.diaFim = diaFim;
    }

    public Date getInscricaoInicio() {
        return inscricaoInicio;
    }

    public void setInscricaoInicio(Date inscricaoInicio) {
        this.inscricaoInicio = inscricaoInicio;
    }

    public Date getInscricaoFim() {
        return inscricaoFim;
    }

    public void setInscricaoFim(Date inscricaoFim) {
        this.inscricaoFim = inscricaoFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getTempoDisponivel() {
        return tempoDisponivel;
    }

    public void setTempoDisponivel(Float tempoDisponivel) {
        this.tempoDisponivel = tempoDisponivel;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
}