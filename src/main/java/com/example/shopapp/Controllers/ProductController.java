package com.example.shopapp.Controllers;

import com.example.shopapp.DTOS.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") String productId) {
        return ResponseEntity.ok("Product with ID: " + productId);
    }

    @PostMapping(value = {"/upload"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            //@RequestPart("file") MultipartFile file
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            List<MultipartFile> files = productDTO.getFiles();
            files = files == null ? new ArrayList<MultipartFile>() : files;
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    if (file.getSize() == 0) {
                        continue;
                    }
                    //Kiem tra kich thuoc file va dinh dang
                    if (file.getSize() > 10 * 1024 * 1024) {
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                                .body("File is too large! Maximum size is 10MB");
                    }

                    String filename = file.getOriginalFilename();
                    String fileExtension = filename.substring(filename.lastIndexOf(".") + 1);
                    if (!Arrays.asList("jpg", "jpeg", "png", "gif").contains(fileExtension.toLowerCase())) {
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                .body(("File must be an image"));
                    }
                    String contentType = file.getContentType();

                    if (contentType == null || !contentType.startsWith("image/")) {
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                .body("File must be an image");
                    }
                    String fileName = storeFile(file);
                    //Luu vao bang product_images
                }

            }

            return ResponseEntity.status(HttpStatus.OK).body("Product created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //Them UUID vao truoc ten file de dam bao ten file la duy nhat
        String uniqueFileName = UUID.randomUUID() + "_" + fileName;
        //Duong dan den thu muc ma ban muon luu file
        Path uploadDir = Paths.get("uploads");
        //Kiem tra va tao thu muc neu khong ton tai
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        //Duong dan day du den file
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        // Sao chep file vao thu muc dich
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }
}
