package viajes.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CambiarCredencialesRequest(
    @NotBlank(message = "El usuario no puede estar vacío")
    @Size(min = 3, message = "El usuario debe tener al menos 3 caracteres")
    String nuevoUsername,

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    String nuevaPassword
) {}
