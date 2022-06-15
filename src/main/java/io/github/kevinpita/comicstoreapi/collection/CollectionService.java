/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.collection;

import io.github.kevinpita.comicstoreapi.comic.Comic;
import io.github.kevinpita.comicstoreapi.comic.ComicDto;
import io.github.kevinpita.comicstoreapi.comic.ComicService;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
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

    @Transactional
    public Collection updateCollection(Long id, CollectionDto dto) {
        Collection collectionToUpdate =
                collectionRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                String.format("Collection %d Not Found", id)));

        collectionToUpdate.setName(dto.getName());
        collectionToUpdate.setDescription(dto.getDescription());
        collectionToUpdate.setPublisher(dto.getPublisher());

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

    public static CollectionDto fromNew(Collection collection) {
        return CollectionDto.builder()
                .id(collection.getId())
                .name(collection.getName())
                .publisher(collection.getPublisher())
                .description(collection.getDescription())
                .build();
    }

    public static CollectionDto from(Collection collection) {
        List<Comic> comics = collection.getComics();
        List<ComicDto> comicsDto =
                comics.stream().map(ComicService::from).collect(Collectors.toList());
        return CollectionDto.builder()
                .id(collection.getId())
                .name(collection.getName())
                .description(collection.getDescription())
                .publisher(collection.getPublisher())
                .comics(comicsDto)
                .build();
    }

    public Collection create(CollectionDto collectionDto) {
        // check if collection DTO already exists by name
        boolean collectionExists = collectionRepository.existsByName(collectionDto.getName());

        if (collectionExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format("Collection %s already exists", collectionDto.getName()));
        }

        Collection collection =
                Collection.builder()
                        .name(collectionDto.getName())
                        .description(collectionDto.getDescription())
                        .publisher(collectionDto.getPublisher())
                        .build();

        return collectionRepository.save(collection);
    }
}
