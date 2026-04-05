package viajes.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import viajes.demo.entity.Reserva;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r JOIN FETCH r.destino")
    List<Reserva> findAllWithDestino();

    @Query("SELECT r FROM Reserva r JOIN FETCH r.destino WHERE r.destino.id = :destinoId")
    List<Reserva> findAllByDestinoIdWithDestino(@Param("destinoId") Long destinoId);

    boolean existsByDestinoIdAndNumeroAsiento(Long destinoId, int numeroAsiento);
}
