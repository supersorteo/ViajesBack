package viajes.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import viajes.demo.dto.ResetAsientosResponse;
import viajes.demo.entity.Asiento;
import viajes.demo.entity.Reserva;
import viajes.demo.exception.NotFoundException;
import viajes.demo.repository.AsientoRepository;
import viajes.demo.repository.ReservaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AsientoService {

    private final AsientoRepository asientoRepository;
    private final ReservaRepository reservaRepository;

    public List<Asiento> findByDestino(Long destinoId) {
        return asientoRepository.findByDestinoIdOrderByNumero(destinoId);
    }

    public Asiento findById(Long id) {
        return asientoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Asiento no encontrado: " + id));
    }

    @Transactional
    public Asiento marcarOcupado(Long destinoId, int numero) {
        return cambiarEstado(destinoId, numero, Asiento.AsientoEstado.OCUPADO);
    }

    @Transactional
    public Asiento liberarAsiento(Long destinoId, int numero) {
        return cambiarEstado(destinoId, numero, Asiento.AsientoEstado.DISPONIBLE);
    }

    @Transactional
    public ResetAsientosResponse resetearAsientos(Long destinoId) {
        List<Reserva> reservas = reservaRepository.findByDestinoId(destinoId);
        int reservasEliminadas = reservas.size();
        if (!reservas.isEmpty()) {
            reservaRepository.deleteAllInBatch(reservas);
        }

        List<Asiento> asientos = asientoRepository.findByDestinoIdOrderByNumero(destinoId);
        for (Asiento asiento : asientos) {
            asiento.setEstado(Asiento.AsientoEstado.DISPONIBLE);
        }
        asientoRepository.saveAll(asientos);

        return new ResetAsientosResponse(asientos.size(), asientos.size(), reservasEliminadas);
    }

    private Asiento cambiarEstado(Long destinoId, int numero, Asiento.AsientoEstado estado) {
        Asiento asiento = asientoRepository.findByDestinoIdAndNumero(destinoId, numero)
                .orElseThrow(() -> new NotFoundException("Asiento no encontrado: " + numero));
        asiento.setEstado(estado);
        return asientoRepository.save(asiento);
    }
}
