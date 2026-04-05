package viajes.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viajes.demo.dto.EstadoReservaRequest;
import viajes.demo.dto.ReservaResponse;
import viajes.demo.service.ReservaService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reservas")
@RequiredArgsConstructor
public class AdminReservaController {

    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaResponse>> getAll(
            @RequestParam(required = false) Long destinoId) {
        List<ReservaResponse> result = destinoId != null
                ? reservaService.findByDestino(destinoId).stream().map(ReservaResponse::from).toList()
                : reservaService.findAll().stream().map(ReservaResponse::from).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ReservaResponse.from(reservaService.findById(id)));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReservaResponse> cambiarEstado(@PathVariable Long id,
                                                          @Valid @RequestBody EstadoReservaRequest request) {
        return ResponseEntity.ok(ReservaResponse.from(
                reservaService.cambiarEstado(id, request.estado())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
