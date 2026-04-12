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

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservaService {

    private static final Set<Reserva.ReservaEstado> ESTADOS_ACTIVOS =
            EnumSet.of(Reserva.ReservaEstado.PENDIENTE, Reserva.ReservaEstado.CONFIRMADA);

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

        boolean yaTomado = reservaRepository.existsByDestinoIdAndNumeroAsientoAndEstadoIn(
                request.destinoId(), request.numeroAsiento(), ESTADOS_ACTIVOS);
        if (yaTomado) {
            throw new IllegalStateException("El asiento " + request.numeroAsiento() + " ya esta reservado.");
        }

        ocuparAsiento(request.destinoId(), request.numeroAsiento());

        Reserva reserva = new Reserva();
        reserva.setDestino(destino);
        reserva.setNumeroAsiento(request.numeroAsiento());
        reserva.setNombrePasajero(request.nombrePasajero());
        reserva.setTelefono(request.telefono());
        reserva.setDni(request.dni());
        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva cambiarEstado(Long id, Reserva.ReservaEstado nuevoEstado) {
        Reserva reserva = findById(id);
        Reserva.ReservaEstado estadoActual = reserva.getEstado();

        if (estadoActual == nuevoEstado) {
            return reserva;
        }

        if (!esEstadoActivo(estadoActual) && esEstadoActivo(nuevoEstado)) {
            boolean yaTomado = reservaRepository.existsByDestinoIdAndNumeroAsientoAndEstadoInAndIdNot(
                    reserva.getDestino().getId(),
                    reserva.getNumeroAsiento(),
                    ESTADOS_ACTIVOS,
                    reserva.getId()
            );
            if (yaTomado) {
                throw new IllegalStateException("No se puede reactivar la reserva porque el asiento ya esta ocupado.");
            }
            ocuparAsiento(reserva.getDestino().getId(), reserva.getNumeroAsiento());
        } else if (esEstadoActivo(estadoActual) && !esEstadoActivo(nuevoEstado)) {
            liberarAsiento(reserva);
        }

        reserva.setEstado(nuevoEstado);
        return reservaRepository.save(reserva);
    }

    @Transactional
    public void delete(Long id) {
        Reserva reserva = findById(id);
        if (esEstadoActivo(reserva.getEstado())) {
            liberarAsiento(reserva);
        }
        reservaRepository.delete(reserva);
    }

    private boolean esEstadoActivo(Reserva.ReservaEstado estado) {
        return ESTADOS_ACTIVOS.contains(estado);
    }

    private void ocuparAsiento(Long destinoId, int numeroAsiento) {
        Asiento asiento = asientoRepository
                .findByDestinoIdAndNumero(destinoId, numeroAsiento)
                .orElseThrow(() -> new NotFoundException("Asiento no encontrado: " + numeroAsiento));
        asiento.setEstado(Asiento.AsientoEstado.OCUPADO);
        asientoRepository.save(asiento);
    }

    private void liberarAsiento(Reserva reserva) {
        asientoRepository
                .findByDestinoIdAndNumero(reserva.getDestino().getId(), reserva.getNumeroAsiento())
                .ifPresent(asiento -> {
                    asiento.setEstado(Asiento.AsientoEstado.DISPONIBLE);
                    asientoRepository.save(asiento);
                });
    }
}
