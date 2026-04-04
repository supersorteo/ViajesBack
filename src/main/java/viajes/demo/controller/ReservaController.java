package viajes.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viajes.demo.dto.ReservaRequest;
import viajes.demo.dto.ReservaResponse;
import viajes.demo.service.ReservaService;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponse> crear(@Valid @RequestBody ReservaRequest request) {
        ReservaResponse response = ReservaResponse.from(reservaService.crear(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ReservaResponse.from(reservaService.findById(id)));
    }
}
