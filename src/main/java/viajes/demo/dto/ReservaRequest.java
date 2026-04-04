package viajes.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservaRequest(
        @NotNull Long destinoId,
        @Min(1) int numeroAsiento,
        @NotBlank String nombrePasajero,
        @Email @NotBlank String email
) {}
