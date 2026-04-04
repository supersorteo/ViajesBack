package viajes.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "destinos")
@Getter
@Setter
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    @Column(length = 500)
    private String descripcion;

    private String imagenUrl;

    @NotNull
    @Positive
    private Double precio;

    private LocalDate fechaSalida;
    private LocalTime horaSalida;
    private LocalTime horaLlegada;

    private String tipo;

    private boolean disponible = true;

    private int totalAsientos = 40;

    @OneToMany(mappedBy = "destino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asiento> asientos = new ArrayList<>();
}
