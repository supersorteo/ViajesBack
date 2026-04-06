package viajes.demo.dto;

public record ResetAsientosResponse(
        int totalAsientos,
        int asientosDisponibles,
        int reservasEliminadas
) {
}
