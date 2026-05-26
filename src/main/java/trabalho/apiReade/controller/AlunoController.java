package trabalho.apiReade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trabalho.apiReade.model.Aluno;
import trabalho.apiReade.repository.AlunoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping
    public Aluno criarAluno(@RequestBody Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @GetMapping
    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        Optional<Aluno> aluno = alunoRepository.findById(id);
        return aluno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable Long id, @RequestBody Aluno dadosAtualizados) {
        return alunoRepository.findById(id)
                .map(aluno -> {
                    aluno.setNome(dadosAtualizados.getNome());
                    aluno.setEndereco(dadosAtualizados.getEndereco());
                    aluno.setTelefone(dadosAtualizados.getTelefone());
                    aluno.setEmail(dadosAtualizados.getEmail());
                    aluno.setCursosInscritos(dadosAtualizados.getCursosInscritos());
                    return ResponseEntity.ok(alunoRepository.save(aluno));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        if (alunoRepository.existsById(id)) {
            alunoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
