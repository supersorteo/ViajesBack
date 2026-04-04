package viajes.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viajes.demo.dto.AsientoResponse;
import viajes.demo.dto.DestinoResponse;
import viajes.demo.service.AsientoService;
import viajes.demo.service.DestinoService;

import java.util.List;

@RestController
@RequestMapping("/api/destinos")
@RequiredArgsConstructor
public class DestinoController {

    private final DestinoService destinoService;
    private final AsientoService asientoService;

    @GetMapping
    public ResponseEntity<List<DestinoResponse>> getAll() {
        List<DestinoResponse> destinos = destinoService.findAll()
                .stream()
                .map(DestinoResponse::from)
                .toList();
        return ResponseEntity.ok(destinos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(DestinoResponse.from(destinoService.findById(id)));
    }

    @GetMapping("/{id}/asientos")
    public ResponseEntity<List<AsientoResponse>> getAsientos(@PathVariable Long id) {
        List<AsientoResponse> asientos = asientoService.findByDestino(id)
                .stream()
                .map(AsientoResponse::from)
                .toList();
        return ResponseEntity.ok(asientos);
    }
}
