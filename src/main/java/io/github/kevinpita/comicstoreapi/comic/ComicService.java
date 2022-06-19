/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comic;

import io.github.kevinpita.comicstoreapi.collection.Collection;
import io.github.kevinpita.comicstoreapi.collection.CollectionRepository;
import io.github.kevinpita.comicstoreapi.comiccopy.ComicCopy;
import io.github.kevinpita.comicstoreapi.comiccopy.ComicCopyRepository;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreator;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreatorDto;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreatorRepository;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreatorService;
import io.github.kevinpita.comicstoreapi.creator.Creator;
import io.github.kevinpita.comicstoreapi.creator.CreatorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ComicService {
    private final ComicRepository comicRepository;
    private final CollectionRepository collectionRepository;
    private final CreatorRepository creatorRepository;
    private final ComicCreatorRepository comicCreatorRepository;
    private final ComicCopyRepository comicCopyRepository;

    public ComicService(
            ComicRepository comicRepository,
            CollectionRepository collectionRepository,
            CreatorRepository creatorRepository,
            ComicCreatorRepository comicCreatorRepository,
            ComicCopyRepository comicCopyRepository) {
        this.comicRepository = comicRepository;
        this.collectionRepository = collectionRepository;
        this.creatorRepository = creatorRepository;
        this.comicCreatorRepository = comicCreatorRepository;
        this.comicCopyRepository = comicCopyRepository;
    }

    public Comic getComic(Long id) {
        return comicRepository.findById(id).orElse(null);
    }

    @Transactional
    public Comic createComic(ComicDto comicDto, boolean update) {

        Comic comic = fromDto(comicDto, update);

        if (comic.getComicCreators() != null && comic.getCopies() != null) {
            comic.getComicCreators().clear();
            comic.getCopies().clear();
        } else {
            comic.setComicCreators(new ArrayList<>());
            comic.setCopies(new ArrayList<>());
        }

        comic = comicRepository.save(comic);

        fillCopyFromDto(comic, comicDto);
        fillCreatorFromDto(comic, comicDto);

        comicRepository.save(comic);

        return comicRepository
                .findById(comic.getId())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comic not found"));
    }

    public List<Comic> getAllComics() {
        return comicRepository.findAll();
    }

    public static ComicDto from(Comic comic) {
        ComicDto.CollectionDto collection =
                ComicDto.CollectionDto.builder()
                        .id(comic.getCollection().getId())
                        .name(comic.getCollection().getName())
                        .build();

        List<ComicCreatorDto> comicCreators =
                comic.getComicCreators().stream()
                        .map(ComicCreatorService::from)
                        .collect(Collectors.toList());

        List<ComicDto.ComicCopyDto> copies =
                comic.getCopies().stream().map(ComicService::fromCopy).collect(Collectors.toList());

        return ComicDto.builder()
                .id(comic.getId())
                .title(comic.getTitle())
                .description(comic.getDescription())
                .issueNumber(comic.getIssueNumber())
                .collection(collection)
                .comicCreators(comicCreators)
                .copies(copies)
                .build();
    }

    public Comic fromDto(ComicDto comicDto, boolean update) {
        Collection collection =
                collectionRepository
                        .findById(comicDto.getCollection().getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!update) {
            boolean issueExists =
                    comicRepository.issueNumberExists(
                            comicDto.getIssueNumber(), collection.getId());
            if (issueExists) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Issue number already exists");
            }
            return Comic.builder()
                    .id(comicDto.getId())
                    .title(comicDto.getTitle())
                    .description(comicDto.getDescription())
                    .issueNumber(comicDto.getIssueNumber())
                    .collection(collection)
                    .build();
        } else {
            Comic comic =
                    comicRepository
                            .findById(comicDto.getId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            //            comic.getComicCreators()
            //                    .forEach(comicCreator ->
            // comicCreatorRepository.delete(comicCreator));
            //            comic.getCopies().forEach(comicCopy ->
            // comicCopyRepository.delete(comicCopy));
            return comic;
        }
    }

    public void fillCopyFromDto(Comic comic, ComicDto comicDto) {
        comicDto.getCopies()
                .forEach(
                        (comicCopyDto -> {
                            var comicCopy =
                                    ComicCopy.builder()
                                            .comic(comic)
                                            .price(comicCopyDto.getPrice())
                                            .cover(comicCopyDto.getCover())
                                            .state(comicCopyDto.getState())
                                            .purchaseDate(comicCopyDto.getPurchaseDate());
                            if (comicCopyDto.getId() != 0) {
                                comicCopy.id(comicCopyDto.getId());
                            }
                            comic.getCopies().add(comicCopy.build());
                        }));
    }

    public void fillCreatorFromDto(Comic comic, ComicDto comicDto) {
        comicDto.getComicCreators()
                .forEach(
                        comicCreatorDto -> {
                            Creator creator =
                                    creatorRepository
                                            .findById(comicCreatorDto.getCreator().getId())
                                            .orElseThrow(
                                                    () ->
                                                            new ResponseStatusException(
                                                                    HttpStatus.NOT_FOUND));
                            var comicCreator =
                                    ComicCreator.builder()
                                            .comic(comic)
                                            .creator(creator)
                                            .role(comicCreatorDto.getRole());
                            if (comicCreatorDto.getId() != 0) {
                                comicCreator.id(comicCreatorDto.getId());
                            }
                            comic.getComicCreators().add(comicCreator.build());
                        });
    }

    public static ComicDto.ComicCopyDto fromCopy(ComicCopy comicCopy) {
        return ComicDto.ComicCopyDto.builder()
                .id(comicCopy.getId())
                .purchaseDate(comicCopy.getPurchaseDate())
                .state(comicCopy.getState())
                .price(comicCopy.getPrice())
                .cover(comicCopy.getCover())
                .build();
    }

    public void deleteComic(Long id) {
        comicRepository.deleteById(id);
    }
}
