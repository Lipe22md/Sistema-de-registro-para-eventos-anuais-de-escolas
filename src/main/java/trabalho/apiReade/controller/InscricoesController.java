package trabalho.apiReade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trabalho.apiReade.model.Inscricoes;
import trabalho.apiReade.repository.InscricoesRepository;

import java.util.List;

@RestController
@RequestMapping("/inscricoes")
@CrossOrigin("*")
public class InscricoesController {

    @Autowired
    private InscricoesRepository repository;

    @GetMapping
    public List<Inscricoes> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Inscricoes salvar(@RequestBody Inscricoes inscricao) {
        return repository.save(inscricao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}