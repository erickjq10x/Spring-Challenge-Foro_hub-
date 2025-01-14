package hub.Foro.domain.topico;


import hub.Foro.domain.usuario.DatosRespuestaUsuario;

import java.time.LocalDateTime;

public record DatosRespuestatopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fecha,
        DatosRespuestaUsuario autor,
        String curso
) {
}
