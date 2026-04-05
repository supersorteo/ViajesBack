package viajes.demo.config;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AdminTokenStore {

    private static final Duration TTL = Duration.ofHours(24);
    private final Map<String, Instant> tokens = new ConcurrentHashMap<>();

    public String generateToken() {
        String token = UUID.randomUUID().toString();
        tokens.put(token, Instant.now().plus(TTL));
        return token;
    }

    public boolean isValid(String token) {
        Instant expiry = tokens.get(token);
        if (expiry == null) return false;
        if (Instant.now().isAfter(expiry)) {
            tokens.remove(token);
            return false;
        }
        return true;
    }

    public void revoke(String token) {
        tokens.remove(token);
    }
}
