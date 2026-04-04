package viajes.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "asientos", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"destino_id", "numero"})
})
@Getter
@Setter
public class Asiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;

    @Enumerated(EnumType.STRING)
    private AsientoEstado estado = AsientoEstado.DISPONIBLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destino_id", nullable = false)
    private Destino destino;

    public enum AsientoEstado {
        DISPONIBLE, OCUPADO
    }
}
