package viajes.demo.dto;

import viajes.demo.entity.Asiento;

public record AsientoResponse(
        Long id,
        int numero,
        String estado,
        Long destinoId
) {
    public static AsientoResponse from(Asiento a) {
        return new AsientoResponse(
                a.getId(),
                a.getNumero(),
                a.getEstado().name(),
                a.getDestino().getId()
        );
    }
}
