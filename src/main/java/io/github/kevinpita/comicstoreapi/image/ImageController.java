/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.image;

import io.github.kevinpita.comicstoreapi.response.CustomResponse;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload/{type}/{id}")
    public ResponseEntity<CustomResponse> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable String type,
            @PathVariable Long id) {
        return imageService.uploadImage(image, type, id);
    }

    @GetMapping("/{type}/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String type, @PathVariable Long id)
            throws IOException {
        return imageService.getImage(type, id);
    }

    @DeleteMapping("/{type}/{id}")
    public ResponseEntity<CustomResponse> deleteImage(
            @PathVariable String type, @PathVariable Long id) throws IOException {
        boolean delete = imageService.deleteImage(type, id);
        return CustomResponse.builder()
                .error(!delete)
                .message("Image deleted")
                .data(delete)
                .build()
                .withResponse(delete ? 200 : 500);
    }
}
