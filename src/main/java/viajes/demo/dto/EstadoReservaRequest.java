package viajes.demo.dto;

import jakarta.validation.constraints.NotNull;
import viajes.demo.entity.Reserva;

public record EstadoReservaRequest(@NotNull Reserva.ReservaEstado estado) {}
