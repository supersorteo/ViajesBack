package viajes.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import viajes.demo.service.ImagenUploadService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/imagenes")
@RequiredArgsConstructor
public class ImagenController {

    private final ImagenUploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        try {
            String url = uploadService.upload(file);
            return ResponseEntity.ok(Map.of("url", url));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error al subir la imagen"));
        }
    }
}
