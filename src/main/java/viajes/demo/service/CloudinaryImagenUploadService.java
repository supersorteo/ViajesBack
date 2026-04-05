package viajes.demo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Implementación de producción: sube las imágenes a Cloudinary.
 * Activa solo cuando el perfil de Spring es "production".
 */
@Service
@Profile("production")
@RequiredArgsConstructor
public class CloudinaryImagenUploadService implements ImagenUploadService {

    private final Cloudinary cloudinary;

    @Override
    @SuppressWarnings("unchecked")
    public String upload(MultipartFile file) throws IOException {
        Map<String, Object> result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("folder", "sonata-viajes")
        );
        return result.get("secure_url").toString();
    }
}
