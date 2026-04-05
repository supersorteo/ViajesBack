package viajes.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record DestinoRequest(
        @NotBlank String nombre,
        @NotBlank @Size(max = 500) String descripcion,
        String imagenUrl,
        @NotNull @Positive Double precio,
        LocalDate fechaSalida,
        LocalTime horaSalida,
        LocalTime horaLlegada,
        String tipo,
        boolean disponible,
        @Min(1) int totalAsientos
) {}
