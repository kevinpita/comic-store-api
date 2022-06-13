/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.creator;

import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreatorRepository;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatorService {
    private final CreatorRepository repository;
    private final ComicCreatorRepository comicCreatorRepository;

    public CreatorService(CreatorRepository repository, ComicCreatorRepository comicCreatorRepository) {
        this.repository = repository;
        this.comicCreatorRepository = comicCreatorRepository;
    }

    public Iterable<Creator> findAll() {
        return repository.findAll();
    }

    public Creator findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Creator save(Creator creator) {
        return repository.save(creator);
    }

    public void delete(Creator creator) {
        repository.delete(creator);
    }

    public static CreatorDto from(Creator creator) {
        return CreatorDto.builder()
                .id(creator.getId())
                .name(creator.getName())
                .lastName(creator.getLastName())
                .build();
    }

    public List<Creator> getCreators() {
        return repository.findAll();
    }

    public int countComics(Creator creator) {
        // count number of comics created by this creator
        return comicCreatorRepository.countByCreator(creator);
    }
}
