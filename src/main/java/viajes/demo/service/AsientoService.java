package viajes.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import viajes.demo.entity.Asiento;
import viajes.demo.repository.AsientoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AsientoService {

    private final AsientoRepository asientoRepository;

    public List<Asiento> findByDestino(Long destinoId) {
        return asientoRepository.findByDestinoIdOrderByNumero(destinoId);
    }

    @Transactional
    public Asiento marcarOcupado(Long destinoId, int numero) {
        Asiento asiento = asientoRepository.findByDestinoIdAndNumero(destinoId, numero)
                .orElseThrow(() -> new RuntimeException("Asiento no encontrado: " + numero));
        asiento.setEstado(Asiento.AsientoEstado.OCUPADO);
        return asientoRepository.save(asiento);
    }
}
