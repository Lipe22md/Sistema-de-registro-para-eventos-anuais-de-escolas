package trabalho.apiReade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trabalho.apiReade.model.Evento;
import trabalho.apiReade.repository.EventoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping
    public List<Evento> listarTodas() {
        return eventoRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscarPorId(@PathVariable Long id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        return evento.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Evento> criarEvento(@RequestBody Evento novaEvento) {
        Evento salvaEvento = eventoRepository.save(novaEvento);
        return ResponseEntity.ok(salvaEvento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> atualizarEvento(@PathVariable Long id, @RequestBody Evento eventoAtualizada) {
        return eventoRepository.findById(id)
                .map(eventoExistente -> {
                    eventoExistente.setNome(eventoAtualizada.getNome());
                    eventoExistente.setDescricao(eventoAtualizada.getDescricao());
                    Evento eventoSalva = eventoRepository.save(eventoExistente);
                    return ResponseEntity.ok(eventoSalva);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEvento(@PathVariable Long id) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
