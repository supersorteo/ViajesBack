package viajes.demo.dto;

import viajes.demo.entity.Reserva;

import java.time.LocalDateTime;

public record ReservaResponse(
        Long id,
        Long destinoId,
        String destinoNombre,
        int numeroAsiento,
        String nombrePasajero,
        String email,
        String estado,
        LocalDateTime fechaCreacion
) {
    public static ReservaResponse from(Reserva r) {
        return new ReservaResponse(
                r.getId(),
                r.getDestino().getId(),
                r.getDestino().getNombre(),
                r.getNumeroAsiento(),
                r.getNombrePasajero(),
                r.getEmail(),
                r.getEstado().name(),
                r.getFechaCreacion()
        );
    }
}
