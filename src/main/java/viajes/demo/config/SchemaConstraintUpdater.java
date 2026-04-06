package viajes.demo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchemaConstraintUpdater implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) {
        actualizarConstraintEstadoAsientos();
    }

    private void actualizarConstraintEstadoAsientos() {
        jdbcTemplate.execute("ALTER TABLE asientos DROP CONSTRAINT IF EXISTS asientos_estado_check");
        jdbcTemplate.execute("""
                ALTER TABLE asientos
                ADD CONSTRAINT asientos_estado_check
                CHECK (estado IN ('DISPONIBLE', 'OCUPADO', 'INACTIVO'))
                """);
        log.info("Constraint asientos_estado_check actualizada para permitir INACTIVO.");
    }
}
