package viajes.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin_config")
@Getter
@Setter
public class AdminConfig {

    @Id
    private Long id = 1L; // siempre fila única

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 1000)
    private String headerBgUrl;

    @Column(length = 1000)
    private String headerBgUrlAnterior;
}
