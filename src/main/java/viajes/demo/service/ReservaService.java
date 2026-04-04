package viajes.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import viajes.demo.dto.ReservaRequest;
import viajes.demo.entity.Asiento;
import viajes.demo.entity.Destino;
import viajes.demo.entity.Reserva;
import viajes.demo.repository.AsientoRepository;
import viajes.demo.repository.ReservaRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final AsientoRepository asientoRepository;
    private final DestinoService destinoService;

    public Reserva findById(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada: " + id));
    }

    @Transactional
    public Reserva crear(ReservaRequest request) {
        Destino destino = destinoService.findById(request.destinoId());

        boolean yaTomado = reservaRepository.existsByDestinoIdAndNumeroAsiento(
                request.destinoId(), request.numeroAsiento());
        if (yaTomado) {
            throw new IllegalStateException("El asiento " + request.numeroAsiento() + " ya está reservado.");
        }

        // Marcar asiento como ocupado
        Asiento asiento = asientoRepository
                .findByDestinoIdAndNumero(request.destinoId(), request.numeroAsiento())
                .orElseThrow(() -> new RuntimeException("Asiento no encontrado: " + request.numeroAsiento()));
        asiento.setEstado(Asiento.AsientoEstado.OCUPADO);
        asientoRepository.save(asiento);

        // Crear reserva
        Reserva reserva = new Reserva();
        reserva.setDestino(destino);
        reserva.setNumeroAsiento(request.numeroAsiento());
        reserva.setNombrePasajero(request.nombrePasajero());
        reserva.setEmail(request.email());
        return reservaRepository.save(reserva);
    }
}
