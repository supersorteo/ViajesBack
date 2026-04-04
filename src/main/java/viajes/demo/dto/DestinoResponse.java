package viajes.demo.dto;

import viajes.demo.entity.Destino;

import java.time.LocalDate;
import java.time.LocalTime;

public record DestinoResponse(
        Long id,
        String nombre,
        String descripcion,
        String imagenUrl,
        Double precio,
        LocalDate fechaSalida,
        LocalTime horaSalida,
        LocalTime horaLlegada,
        String tipo,
        boolean disponible,
        int totalAsientos
) {
    public static DestinoResponse from(Destino d) {
        return new DestinoResponse(
                d.getId(),
                d.getNombre(),
                d.getDescripcion(),
                d.getImagenUrl(),
                d.getPrecio(),
                d.getFechaSalida(),
                d.getHoraSalida(),
                d.getHoraLlegada(),
                d.getTipo(),
                d.isDisponible(),
                d.getTotalAsientos()
        );
    }
}
