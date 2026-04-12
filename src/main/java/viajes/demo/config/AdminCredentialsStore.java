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

    private static final String DEFAULT_HEADER_BG =
            "https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?auto=format&fit=crop&w=1920&q=80";

    public String getHeaderBgUrl() {
        String url = getOrCreate().getHeaderBgUrl();
        return url != null ? url : DEFAULT_HEADER_BG;
    }

    public String getHeaderBgUrlAnterior() {
        return getOrCreate().getHeaderBgUrlAnterior();
    }

    public void updateHeaderBgUrl(String url) {
        AdminConfig cfg = getOrCreate();
        // Guarda la actual como anterior antes de reemplazarla
        cfg.setHeaderBgUrlAnterior(cfg.getHeaderBgUrl() != null ? cfg.getHeaderBgUrl() : DEFAULT_HEADER_BG);
        cfg.setHeaderBgUrl(url);
        configRepository.save(cfg);
    }

    public String revertirHeaderBgUrl() {
        AdminConfig cfg = getOrCreate();
        String anterior = cfg.getHeaderBgUrlAnterior();
        if (anterior == null) anterior = DEFAULT_HEADER_BG;
        // Intercambia actual <-> anterior
        cfg.setHeaderBgUrlAnterior(cfg.getHeaderBgUrl());
        cfg.setHeaderBgUrl(anterior);
        configRepository.save(cfg);
        return anterior;
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
