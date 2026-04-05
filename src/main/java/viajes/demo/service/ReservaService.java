package viajes.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import viajes.demo.dto.ReservaRequest;
import viajes.demo.entity.Asiento;
import viajes.demo.entity.Destino;
import viajes.demo.entity.Reserva;
import viajes.demo.exception.NotFoundException;
import viajes.demo.repository.AsientoRepository;
import viajes.demo.repository.ReservaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final AsientoRepository asientoRepository;
    private final DestinoService destinoService;

    public List<Reserva> findAll() {
        return reservaRepository.findAllWithDestino();
    }

    public List<Reserva> findByDestino(Long destinoId) {
        return reservaRepository.findAllByDestinoIdWithDestino(destinoId);
    }

    public Reserva findById(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada: " + id));
    }

    @Transactional
    public Reserva crear(ReservaRequest request) {
        Destino destino = destinoService.findById(request.destinoId());

        boolean yaTomado = reservaRepository.existsByDestinoIdAndNumeroAsiento(
                request.destinoId(), request.numeroAsiento());
        if (yaTomado) {
            throw new IllegalStateException("El asiento " + request.numeroAsiento() + " ya está reservado.");
        }

        Asiento asiento = asientoRepository
                .findByDestinoIdAndNumero(request.destinoId(), request.numeroAsiento())
                .orElseThrow(() -> new NotFoundException("Asiento no encontrado: " + request.numeroAsiento()));
        asiento.setEstado(Asiento.AsientoEstado.OCUPADO);
        asientoRepository.save(asiento);

        Reserva reserva = new Reserva();
        reserva.setDestino(destino);
        reserva.setNumeroAsiento(request.numeroAsiento());
        reserva.setNombrePasajero(request.nombrePasajero());
        reserva.setEmail(request.email());
        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva cambiarEstado(Long id, Reserva.ReservaEstado nuevoEstado) {
        Reserva reserva = findById(id);

        if (nuevoEstado == Reserva.ReservaEstado.CANCELADA
                && reserva.getEstado() != Reserva.ReservaEstado.CANCELADA) {
            liberarAsiento(reserva);
        }

        reserva.setEstado(nuevoEstado);
        return reservaRepository.save(reserva);
    }

    @Transactional
    public void delete(Long id) {
        Reserva reserva = findById(id);
        if (reserva.getEstado() == Reserva.ReservaEstado.CONFIRMADA) {
            liberarAsiento(reserva);
        }
        reservaRepository.delete(reserva);
    }

    // ── Helper ───────────────────────────────────────────────────────────────

    private void liberarAsiento(Reserva reserva) {
        asientoRepository
                .findByDestinoIdAndNumero(reserva.getDestino().getId(), reserva.getNumeroAsiento())
                .ifPresent(a -> {
                    a.setEstado(Asiento.AsientoEstado.DISPONIBLE);
                    asientoRepository.save(a);
                });
    }
}
