package viajes.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import viajes.demo.entity.Destino;
import viajes.demo.repository.DestinoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DestinoService {

    private final DestinoRepository destinoRepository;

    public List<Destino> findAll() {
        return destinoRepository.findAll();
    }

    public Destino findById(Long id) {
        return destinoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destino no encontrado: " + id));
    }

    @Transactional
    public Destino save(Destino destino) {
        return destinoRepository.save(destino);
    }
}
