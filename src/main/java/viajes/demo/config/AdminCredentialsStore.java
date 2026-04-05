package viajes.demo.config;

import org.springframework.stereotype.Component;

/**
 * Almacén mutable de credenciales del administrador.
 * Se inicializa desde AdminProperties (application.properties / env vars)
 * y puede actualizarse en caliente desde el panel de administración.
 * Nota: los cambios son en memoria — al reiniciar el servidor se restauran
 * los valores de las variables de entorno.
 */
@Component
public class AdminCredentialsStore {

    private volatile String username;
    private volatile String password;

    public AdminCredentialsStore(AdminProperties props) {
        this.username = props.username();
        this.password = props.password();
    }

    public boolean validate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void update(String newUsername, String newPassword) {
        this.username = newUsername;
        this.password = newPassword;
    }

    public String getUsername() {
        return username;
    }
}
