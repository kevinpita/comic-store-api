/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.collection;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CollectionService {
    private final CollectionRepository collectionRepository;

    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public List<Collection> getCollections() {
        return collectionRepository.findAll();
    }

    public Collection getCollection(Long id) {
        return collectionRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        String.format("Collection %d Not Found", id)));
    }

    public Collection createCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    public Collection updateCollection(Long id, String publisher, String description) {
        Collection collectionToUpdate =
                collectionRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                String.format("Collection %d Not Found", id)));

        if (publisher != null && !publisher.isBlank()) {
            collectionToUpdate.setPublisher(publisher);
        }

        if (description != null && !description.isBlank()) {
            collectionToUpdate.setPublisher(description);
        }

        return collectionToUpdate;
    }

    public void deleteCollection(Long id) {
        boolean collectionExists = collectionRepository.existsById(id);
        if (!collectionExists) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("Collection %d Not Found", id));
        }

        collectionRepository.deleteById(id);
    }
}
