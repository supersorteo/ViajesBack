package viajes.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import viajes.demo.entity.Reserva;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByDestinoId(Long destinoId);
    boolean existsByDestinoIdAndNumeroAsiento(Long destinoId, int numeroAsiento);
}
