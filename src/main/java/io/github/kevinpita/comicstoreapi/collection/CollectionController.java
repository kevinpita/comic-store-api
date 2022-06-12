/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.collection;

import io.github.kevinpita.comicstoreapi.response.CustomResponse;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/collections")
public class CollectionController {
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getCollections() {
        List<Collection> collections = collectionService.getCollections();
        List<CollectionDto> data =
                collections.stream().map(CollectionService::from).collect(Collectors.toList());
        return CustomResponse.builder()
                .error(false)
                .message("Collections")
                .data(data)
                .build()
                .withResponse(200);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomResponse> getCollection(@PathVariable Long id) {
        Collection collection = collectionService.getCollection(id);
        CollectionDto data = CollectionService.from(collection);
        return CustomResponse.builder()
                .error(false)
                .message("Collection " + id)
                .data(data)
                .build()
                .withResponse(200);
    }

    @PostMapping
    public Collection createCollection(@Valid @RequestBody Collection collection) {
        return collectionService.createCollection(collection);
    }

    @PutMapping("{id}")
    public Collection updateCollection(
            @PathVariable Long id,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String description) {
        return collectionService.updateCollection(id, publisher, description);
    }

    @DeleteMapping("{id}")
    public void deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
    }
}
