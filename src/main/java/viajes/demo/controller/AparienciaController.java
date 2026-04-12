package viajes.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viajes.demo.config.AdminCredentialsStore;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AparienciaController {

    private final AdminCredentialsStore credentialsStore;

    /** Público: el frontend lo llama al iniciar para obtener la imagen de fondo */
    @GetMapping("/api/apariencia")
    public ResponseEntity<Map<String, String>> getApariencia() {
        String anterior = credentialsStore.getHeaderBgUrlAnterior();
        var body = new java.util.HashMap<String, String>();
        body.put("headerBgUrl", credentialsStore.getHeaderBgUrl());
        if (anterior != null) body.put("headerBgUrlAnterior", anterior);
        return ResponseEntity.ok(body);
    }

    /** Admin: actualiza la imagen de fondo del header */
    @PutMapping("/api/admin/apariencia")
    public ResponseEntity<Map<String, String>> updateApariencia(@RequestBody Map<String, String> body) {
        String url = body.getOrDefault("headerBgUrl", "");
        credentialsStore.updateHeaderBgUrl(url);
        return ResponseEntity.ok(Map.of("headerBgUrl", url));
    }

    /** Admin: revierte al fondo anterior (intercambia actual <-> anterior) */
    @PostMapping("/api/admin/apariencia/revertir")
    public ResponseEntity<Map<String, String>> revertirApariencia() {
        String revertida = credentialsStore.revertirHeaderBgUrl();
        return ResponseEntity.ok(Map.of("headerBgUrl", revertida));
    }
}
