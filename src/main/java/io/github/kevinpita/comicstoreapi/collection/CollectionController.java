/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.collection;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/collections")
public class CollectionController {
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public List<Collection> getCollections() {
        return collectionService.getCollections();
    }

    @GetMapping("{id}")
    public Collection getCollection(@PathVariable Long id) {
        return collectionService.getCollection(id);
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
