package viajes.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import viajes.demo.entity.Destino;

import java.util.List;

public interface DestinoRepository extends JpaRepository<Destino, Long> {
    List<Destino> findByDisponibleTrue();
}
