package viajes.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank
    @Pattern(regexp = "^[+\\d][\\d\\s\\-\\.\\(\\)]{6,18}$")
    private String telefono;

    @NotBlank
    @Pattern(regexp = "^\\d{7,8}$")
    private String dni;

    @Enumerated(EnumType.STRING)
    private ReservaEstado estado = ReservaEstado.CONFIRMADA;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public enum ReservaEstado {
        PENDIENTE, CONFIRMADA, CANCELADA
    }
}
