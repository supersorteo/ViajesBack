package viajes.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReservaRequest(
        @NotNull Long destinoId,
        @Min(1) int numeroAsiento,
        @NotBlank String nombrePasajero,
        @NotBlank @Pattern(regexp = "^[+\\d][\\d\\s\\-\\.\\(\\)]{6,18}$") String telefono,
        @NotBlank @Pattern(regexp = "^\\d{7,8}$") String dni
) {}
