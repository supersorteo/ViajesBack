package viajes.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import viajes.demo.entity.Reserva;

import java.util.Collection;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r JOIN FETCH r.destino")
    List<Reserva> findAllWithDestino();

    @Query("SELECT r FROM Reserva r JOIN FETCH r.destino WHERE r.id = :id")
    java.util.Optional<Reserva> findByIdWithDestino(@Param("id") Long id);

    @Query("SELECT r FROM Reserva r JOIN FETCH r.destino WHERE r.destino.id = :destinoId")
    List<Reserva> findAllByDestinoIdWithDestino(@Param("destinoId") Long destinoId);

    boolean existsByDestinoIdAndNumeroAsientoAndEstadoIn(
            Long destinoId,
            int numeroAsiento,
            Collection<Reserva.ReservaEstado> estados
    );

    boolean existsByDestinoIdAndNumeroAsientoAndEstadoInAndIdNot(
            Long destinoId,
            int numeroAsiento,
            Collection<Reserva.ReservaEstado> estados,
            Long id
    );

    @Query("""
            SELECT r.numeroAsiento
            FROM Reserva r
            WHERE r.destino.id = :destinoId
              AND r.estado IN :estados
            """)
    List<Integer> findNumerosAsientoByDestinoIdAndEstadoIn(
            @Param("destinoId") Long destinoId,
            @Param("estados") Collection<Reserva.ReservaEstado> estados
    );

    List<Reserva> findByDestinoId(Long destinoId);

    boolean existsByDestinoId(Long destinoId);
}
