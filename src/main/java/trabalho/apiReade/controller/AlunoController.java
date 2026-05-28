package trabalho.apiReade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trabalho.apiReade.model.Aluno;
import trabalho.apiReade.repository.AlunoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
@CrossOrigin("*")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @GetMapping
    public List<Aluno> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {

        Optional<Aluno> aluno = repository.findById(id);

        return aluno.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Aluno salvar(@RequestBody Aluno aluno) {
        return repository.save(aluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(
            @PathVariable Long id,
            @RequestBody Aluno dadosAtualizados
    ) {

        return repository.findById(id)
                .map(aluno -> {

                    aluno.setNome(dadosAtualizados.getNome());
                    aluno.setEmail(dadosAtualizados.getEmail());
                    aluno.setTelefone(dadosAtualizados.getTelefone());
                    aluno.setEndereco(dadosAtualizados.getEndereco());

                    Aluno atualizado = repository.save(aluno);

                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}