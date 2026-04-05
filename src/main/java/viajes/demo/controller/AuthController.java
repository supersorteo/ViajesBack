package viajes.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import viajes.demo.config.AdminCredentialsStore;
import viajes.demo.config.AdminTokenStore;
import viajes.demo.dto.CambiarCredencialesRequest;
import viajes.demo.dto.LoginRequest;
import viajes.demo.dto.LoginResponse;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AdminCredentialsStore credentialsStore;
    private final AdminTokenStore tokenStore;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        if (!credentialsStore.validate(request.username(), request.password())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        }
        String token = tokenStore.generateToken();
        return ResponseEntity.ok(new LoginResponse(token, credentialsStore.getUsername()));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            tokenStore.revoke(authHeader.substring(7));
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Cambia las credenciales del administrador (en memoria).
     * Requiere token válido (protegido por AdminAuthInterceptor vía /api/admin/**).
     */
    @PutMapping("/admin/credenciales")
    public ResponseEntity<Map<String, String>> cambiarCredenciales(
            @Valid @RequestBody CambiarCredencialesRequest request) {
        credentialsStore.update(request.nuevoUsername(), request.nuevaPassword());
        return ResponseEntity.ok(Map.of("username", request.nuevoUsername()));
    }
}
