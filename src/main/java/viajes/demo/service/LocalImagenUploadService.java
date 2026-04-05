package viajes.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Implementación local: guarda las imágenes en disco.
 * Activa solo cuando el perfil de Spring NO es "production".
 * Las imágenes se sirven desde http://localhost:{port}/uploads/{filename}.
 */
@Service
@Profile("!production")
public class LocalImagenUploadService implements ImagenUploadService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${server.port:8080}")
    private int serverPort;

    @Override
    public String upload(MultipartFile file) throws IOException {
        Path dir = Paths.get(uploadDir);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + (ext != null ? "." + ext : "");
        Files.write(dir.resolve(filename), file.getBytes());

        return "http://localhost:" + serverPort + "/uploads/" + filename;
    }
}
