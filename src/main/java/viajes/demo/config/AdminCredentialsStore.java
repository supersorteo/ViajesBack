package viajes.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import viajes.demo.entity.AdminConfig;
import viajes.demo.repository.AdminConfigRepository;

/**
 * Almacén de credenciales del administrador respaldado por la base de datos.
 * En el primer arranque crea la fila inicial con los valores de AdminProperties
 * (application.properties / variables de entorno). Los cambios posteriores
 * se persisten en la tabla admin_config y sobreviven a los redespliegues.
 */
@Component
@RequiredArgsConstructor
public class AdminCredentialsStore {

    private final AdminConfigRepository configRepository;
    private final AdminProperties props;

    public boolean validate(String username, String password) {
        AdminConfig cfg = getOrCreate();
        return cfg.getUsername().equals(username) && cfg.getPassword().equals(password);
    }

    public void update(String newUsername, String newPassword) {
        AdminConfig cfg = getOrCreate();
        cfg.setUsername(newUsername);
        cfg.setPassword(newPassword);
        configRepository.save(cfg);
    }

    public String getUsername() {
        return getOrCreate().getUsername();
    }

    public String getPassword() {
        return getOrCreate().getPassword();
    }

    public void reset() {
        AdminConfig cfg = getOrCreate();
        cfg.setUsername(props.username());
        cfg.setPassword(props.password());
        configRepository.save(cfg);
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private AdminConfig getOrCreate() {
        return configRepository.findById(1L).orElseGet(() -> {
            AdminConfig cfg = new AdminConfig();
            cfg.setUsername(props.username());
            cfg.setPassword(props.password());
            return configRepository.save(cfg);
        });
    }
}
