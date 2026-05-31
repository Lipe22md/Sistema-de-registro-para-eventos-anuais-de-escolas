package trabalho.apiReade.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataEvento;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Inscricoes> inscricoes;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public LocalDate getDataEvento() {return dataEvento;}
    public void setDataEvento(LocalDate dataEvento) {this.dataEvento = dataEvento;}
    public Professor getProfessor() {return professor;}
    public void setProfessor(Professor professor) {this.professor = professor;}
    public List<Inscricoes> getInscricoes() {return inscricoes;}
    public void setInscricoes(List<Inscricoes> inscricoes) {this.inscricoes = inscricoes;}
}