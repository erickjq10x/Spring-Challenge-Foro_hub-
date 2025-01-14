package hub.Foro.Controlller;

import hub.Foro.domain.topico.*;
import hub.Foro.domain.usuario.DatosRespuestaUsuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestatopico> RegistrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));
        DatosRespuestatopico datosRespuestatopico = new DatosRespuestatopico(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFecha(), new DatosRespuestaUsuario(topico.getAutor().getId(), topico.getAutor().getLogin()), topico.getCurso());
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestatopico);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestatopico> ObtenerTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        DatosRespuestatopico datosRespuestatopico = new DatosRespuestatopico(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getFecha(), new DatosRespuestaUsuario(topico.getAutor().getId(), topico.getAutor().getLogin()), topico.getCurso());
        return ResponseEntity.ok(datosRespuestatopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> ListarTopicos(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(topicoRepository.findByStatusTrue(pageable).map(DatosListadoTopico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosActualizarTopico> ActualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(new DatosActualizarTopico(topico.getId(), topico.getTitulo(),
                topico.getMensaje(), topico.getCurso()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> EliminarTopico(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
