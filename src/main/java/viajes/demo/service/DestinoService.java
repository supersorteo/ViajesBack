package viajes.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import viajes.demo.dto.DestinoRequest;
import viajes.demo.entity.Asiento;
import viajes.demo.entity.Destino;
import viajes.demo.exception.NotFoundException;
import viajes.demo.repository.AsientoRepository;
import viajes.demo.repository.DestinoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DestinoService {

    private final DestinoRepository destinoRepository;
    private final AsientoRepository asientoRepository;

    public List<Destino> findAll() {
        return destinoRepository.findAll();
    }

    public List<Destino> findAllDisponibles() {
        return destinoRepository.findByDisponibleTrue();
    }

    public Destino findById(Long id) {
        return destinoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Destino no encontrado: " + id));
    }

    @Transactional
    public Destino create(DestinoRequest request) {
        Destino destino = mapToEntity(new Destino(), request);
        Destino saved = destinoRepository.save(destino);
        generarAsientos(saved);
        return saved;
    }

    @Transactional
    public Destino update(Long id, DestinoRequest request) {
        Destino destino = findById(id);
        return destinoRepository.save(mapToEntity(destino, request));
    }

    @Transactional
    public void delete(Long id) {
        Destino destino = findById(id);
        destinoRepository.delete(destino);
    }

    @Transactional
    public Destino toggleDisponible(Long id) {
        Destino destino = findById(id);
        destino.setDisponible(!destino.isDisponible());
        return destinoRepository.save(destino);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private Destino mapToEntity(Destino destino, DestinoRequest req) {
        destino.setNombre(req.nombre());
        destino.setDescripcion(req.descripcion());
        destino.setImagenUrl(req.imagenUrl());
        destino.setPrecio(req.precio());
        destino.setFechaSalida(req.fechaSalida());
        destino.setHoraSalida(req.horaSalida());
        destino.setHoraLlegada(req.horaLlegada());
        destino.setTipo(req.tipo());
        destino.setDisponible(req.disponible());
        destino.setTotalAsientos(req.totalAsientos());
        return destino;
    }

    private void generarAsientos(Destino destino) {
        List<Asiento> asientos = new ArrayList<>();
        for (int i = 1; i <= destino.getTotalAsientos(); i++) {
            Asiento a = new Asiento();
            a.setNumero(i);
            a.setDestino(destino);
            a.setEstado(Asiento.AsientoEstado.DISPONIBLE);
            asientos.add(a);
        }
        asientoRepository.saveAll(asientos);
    }
}
