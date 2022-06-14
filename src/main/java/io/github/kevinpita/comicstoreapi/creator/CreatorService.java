/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.creator;

import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreatorRepository;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CreatorService {
    private final CreatorRepository repository;
    private final ComicCreatorRepository comicCreatorRepository;

    public CreatorService(
            CreatorRepository repository, ComicCreatorRepository comicCreatorRepository) {
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

    public void delete(Long creatorId) {
        Creator creator = repository.findById(creatorId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Creator not found"));
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

    public Creator create(CreatorDto creatorDto) {
        String lastName = creatorDto.getLastName();

        int count =
                repository.countByNameAndLastName(creatorDto.getName(), creatorDto.getLastName());
        if (count > 0) {
            lastName += String.format(" (%d)", count + 1);
        }

        Creator creator = Creator.builder().name(creatorDto.getName()).lastName(lastName).build();
        repository.save(creator);
        return creator;
    }

    public void update(CreatorDto creatorDto) {
        String lastName = creatorDto.getLastName();
        int count = 0;
        String[] creatorLastNamePart = lastName.split(Pattern.quote("("));
        if (creatorLastNamePart.length > 1) {
            lastName = creatorLastNamePart[0];
            count = Integer.parseInt(creatorLastNamePart[1].replace(")", ""));
        }

        if (count == 0) {
            count =
                    repository.countByNameLastNameAndId(
                            creatorDto.getName(), creatorDto.getLastName(), creatorDto.getId());
            count++;
        }

        if (count > 1) {
            lastName += String.format(" (%d)", count);
        }

        System.out.println(lastName);
        System.out.println(count);

        Creator creator =
                repository
                        .findById(creatorDto.getId())
                        .orElseThrow(
                                () ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "entity not found"));
        System.out.println(creator);
        creator.setName(creatorDto.getName());
        creator.setLastName(lastName);
        repository.save(creator);
    }
}
