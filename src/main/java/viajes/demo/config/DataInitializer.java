package viajes.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viajes.demo.entity.Asiento;
import viajes.demo.entity.Destino;
import viajes.demo.repository.AsientoRepository;
import viajes.demo.repository.DestinoRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final DestinoRepository destinoRepository;
    private final AsientoRepository asientoRepository;

    @Bean
    CommandLineRunner initData() {
        // Datos de prueba comentados — la app ahora es completamente dinámica.
        // Los destinos se gestionan desde el panel de administración.
        return args -> {};

        /* Datos estáticos originales (no eliminar, son referencia)
        return args -> {
            if (destinoRepository.count() > 0) return;

            List<Destino> destinos = List.of(
                crearDestino("Chascomús",
                    "Disfrutá de un fin de semana increíble en la laguna...",
                    "https://images.unsplash.com/photo-1549646462-8e36d4bdce23?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80",
                    15000.0, LocalDate.of(2025, 11, 15), LocalTime.of(8, 0), LocalTime.of(10, 30), "Ida y Vuelta", true),
                crearDestino("Mar del Plata",
                    "Viaje Especial Fin de Semana Largo...",
                    "https://images.unsplash.com/photo-1596423735880-5ffdf52e463a?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80",
                    25000.0, LocalDate.of(2025, 11, 22), LocalTime.of(6, 0), LocalTime.of(11, 0), "Especial", false),
                crearDestino("Córdoba Capital",
                    "Sierras y relax en un servicio Premium...",
                    "https://images.unsplash.com/photo-1621285090176-9d33b3793fdf?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=80",
                    32000.0, LocalDate.of(2025, 12, 5), LocalTime.of(22, 30), LocalTime.of(8, 0), "Premium", false)
            );

            for (Destino destino : destinos) {
                Destino saved = destinoRepository.save(destino);
                generarAsientos(saved);
            }
        };
        */
    }

    private Destino crearDestino(String nombre, String descripcion, String imagenUrl,
                                  Double precio, LocalDate fechaSalida,
                                  LocalTime horaSalida, LocalTime horaLlegada,
                                  String tipo, boolean disponible) {
        Destino d = new Destino();
        d.setNombre(nombre);
        d.setDescripcion(descripcion);
        d.setImagenUrl(imagenUrl);
        d.setPrecio(precio);
        d.setFechaSalida(fechaSalida);
        d.setHoraSalida(horaSalida);
        d.setHoraLlegada(horaLlegada);
        d.setTipo(tipo);
        d.setDisponible(disponible);
        d.setTotalAsientos(40);
        return d;
    }

    private void generarAsientos(Destino destino) {
        List<Asiento> asientos = new ArrayList<>();
        for (int i = 1; i <= destino.getTotalAsientos(); i++) {
            Asiento a = new Asiento();
            a.setNumero(i);
            a.setDestino(destino);
            a.setEstado(Asiento.AsientoEstado.DISPONIBLE);
            asientos.add(a);
        }
        asientoRepository.saveAll(asientos);
    }
}
