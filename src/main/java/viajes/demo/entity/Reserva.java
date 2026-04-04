package viajes.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destino_id", nullable = false)
    private Destino destino;

    private int numeroAsiento;

    @NotBlank
    private String nombrePasajero;

    @Email
    @NotBlank
    private String email;

    @Enumerated(EnumType.STRING)
    private ReservaEstado estado = ReservaEstado.CONFIRMADA;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public enum ReservaEstado {
        PENDIENTE, CONFIRMADA, CANCELADA
    }
}
