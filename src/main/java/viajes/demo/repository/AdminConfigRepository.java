package viajes.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import viajes.demo.entity.AdminConfig;

public interface AdminConfigRepository extends JpaRepository<AdminConfig, Long> {}
