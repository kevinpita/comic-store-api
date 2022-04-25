/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.collection;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/collection")
public class CollectionController {
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public List<Collection> getCollections() {
        return collectionService.getCollections();
    }

    @GetMapping("/{id}")
    public Collection getCollection(@Valid @PathVariable Long id) {
        return collectionService.getCollection(id);
    }

    @PostMapping
    public Collection createCollection(@Valid Collection collection) {
        return collectionService.createCollection(collection);
    }
}
