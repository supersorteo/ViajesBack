package viajes.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import viajes.demo.entity.Asiento;

import java.util.List;
import java.util.Optional;

public interface AsientoRepository extends JpaRepository<Asiento, Long> {
    List<Asiento> findByDestinoIdOrderByNumero(Long destinoId);
    Optional<Asiento> findByDestinoIdAndNumero(Long destinoId, int numero);
}
