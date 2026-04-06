package viajes.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viajes.demo.dto.DestinoRequest;
import viajes.demo.dto.DestinoResponse;
import viajes.demo.dto.ResetAsientosResponse;
import viajes.demo.service.AsientoService;
import viajes.demo.service.DestinoService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/destinos")
@RequiredArgsConstructor
public class AdminDestinoController {

    private final DestinoService destinoService;
    private final AsientoService asientoService;

    @GetMapping
    public ResponseEntity<List<DestinoResponse>> getAll() {
        return ResponseEntity.ok(
                destinoService.findAll().stream().map(DestinoResponse::from).toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(DestinoResponse.from(destinoService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<DestinoResponse> create(@Valid @RequestBody DestinoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DestinoResponse.from(destinoService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinoResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody DestinoRequest request) {
        return ResponseEntity.ok(DestinoResponse.from(destinoService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        destinoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/disponible")
    public ResponseEntity<DestinoResponse> toggleDisponible(@PathVariable Long id) {
        return ResponseEntity.ok(DestinoResponse.from(destinoService.toggleDisponible(id)));
    }

    @PostMapping("/{id}/resetear-asientos")
    public ResponseEntity<ResetAsientosResponse> resetearAsientos(@PathVariable Long id) {
        return ResponseEntity.ok(asientoService.resetearAsientos(id));
    }
}
