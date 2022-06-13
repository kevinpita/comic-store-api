/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.image;

import io.github.kevinpita.comicstoreapi.collection.Collection;
import io.github.kevinpita.comicstoreapi.collection.CollectionService;
import io.github.kevinpita.comicstoreapi.comic.Comic;
import io.github.kevinpita.comicstoreapi.comic.ComicService;
import io.github.kevinpita.comicstoreapi.image.exception.InvalidModelIdException;
import io.github.kevinpita.comicstoreapi.image.exception.ModelNotFoundException;
import io.github.kevinpita.comicstoreapi.response.CustomResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final CollectionService collectionService;
    private final ComicService comicService;

    public ImageService(CollectionService collectionService, ComicService comicService) {
        this.collectionService = collectionService;
        this.comicService = comicService;
    }

    public ResponseEntity<CustomResponse> uploadImage(MultipartFile image, String type, Long id) {
        Path path;

        path = getFolderByPath(type, id);

        if (!createFolderIfNotExists(path)) {
            return CustomResponse.builder()
                    .error(true)
                    .message("Error creating directory")
                    .build()
                    .withResponse(500);
        }

        path = getFilePath(id, path);

        try {
            Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return CustomResponse.builder()
                    .error(true)
                    .message("Error copying file")
                    .build()
                    .withResponse(500);
        }

        return CustomResponse.builder()
                .error(false)
                .message("Image uploaded")
                .build()
                .withResponse(200);
    }

    public ResponseEntity<byte[]> getImage(String type, Long id) throws IOException {
        Path path = getFilePath(id, getFolderByPath(type, id));
        if (!Files.exists(path)) {
            path = getDefaultImage(type);
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(Files.readAllBytes(path));
    }

    private Path getDefaultImage(String type) {
        Path path;
        if (type.equals("collection")) {
            path = Path.of("images/default/collection.png");
        } else {
            path = Path.of("images/default/comic.png");
        }
        return path;
    }

    private Path getFilePath(Long id, Path path) {
        String filename = String.format("%s.%s", id, "png");
        path = path.resolve(filename);
        return path;
    }

    private boolean createFolderIfNotExists(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    private Path getFolderByPath(String type, Long id) {
        Path path = null;
        switch (type) {
            case "collection":
                path = Path.of("images/collection/");
                Collection collection = collectionService.getCollection(id);
                if (collection == null) {
                    throw new InvalidModelIdException("Collection not found");
                }
                break;
            case "comic":
                path = Path.of("images/comic/");
                Comic comic = comicService.getComic(id);
                if (comic == null) {
                    throw new InvalidModelIdException("Comic not found");
                }
                break;
            default:
                throw new ModelNotFoundException("Type not found");
        }
        return path;
    }
}
