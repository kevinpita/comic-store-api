/* Kevin Pita 2022 */
package io.github.kevinpita.comicstoreapi.comic;

import io.github.kevinpita.comicstoreapi.collection.Collection;
import io.github.kevinpita.comicstoreapi.collection.CollectionRepository;
import io.github.kevinpita.comicstoreapi.comiccopy.ComicCopy;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreator;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreatorDto;
import io.github.kevinpita.comicstoreapi.comiccreator.ComicCreatorService;
import io.github.kevinpita.comicstoreapi.creator.Creator;
import io.github.kevinpita.comicstoreapi.creator.CreatorRepository;
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

    public ComicService(
            ComicRepository comicRepository,
            CollectionRepository collectionRepository,
            CreatorRepository creatorRepository) {
        this.comicRepository = comicRepository;
        this.collectionRepository = collectionRepository;
        this.creatorRepository = creatorRepository;
    }

    public Comic getComic(Long id) {
        return comicRepository.findById(id).orElse(null);
    }

    @Transactional
    public Comic createComic(ComicDto comicDto) {

        Comic comic = fromDto(comicDto);

        fillCopyFromDto(comic, comicDto);
        fillCreatorFromDto(comic, comicDto);

        comic = comicRepository.save(comic);

        return comic;
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

    public Comic fromDto(ComicDto comicDto) {
        Collection collection =
                collectionRepository
                        .findById(comicDto.getCollection().getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        boolean issueExists =
                comicRepository.issueNumberExists(comicDto.getIssueNumber(), collection.getId());
        if (issueExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Issue number already exists");
        }

        return Comic.builder()
                .id(comicDto.getId())
                .title(comicDto.getTitle())
                .description(comicDto.getDescription())
                .issueNumber(comicDto.getIssueNumber())
                .collection(collection)
                .build();
    }

    public void fillCopyFromDto(Comic comic, ComicDto comicDto) {
        List<ComicCopy> copies =
                comicDto.getCopies().stream()
                        .map(
                                (comicCopyDto -> {
                                    return ComicCopy.builder()
                                            .comic(comic)
                                            .price(comicCopyDto.getPrice())
                                            .cover(comicCopyDto.getCover())
                                            .state(comicCopyDto.getState())
                                            .purchaseDate(comicCopyDto.getPurchaseDate())
                                            .build();
                                }))
                        .collect(Collectors.toList());
        comic.setCopies(copies);
    }

    public void fillCreatorFromDto(Comic comic, ComicDto comicDto) {
        List<ComicCreator> creators =
                comicDto.getComicCreators().stream()
                        .map(
                                (comicCreatorDto -> {
                                    Creator creator =
                                            creatorRepository
                                                    .findById(comicCreatorDto.getCreator().getId())
                                                    .orElseThrow(
                                                            () ->
                                                                    new ResponseStatusException(
                                                                            HttpStatus.NOT_FOUND));
                                    return ComicCreator.builder()
                                            .comic(comic)
                                            .creator(creator)
                                            .build();
                                }))
                        .collect(Collectors.toList());
        comic.setComicCreators(creators);
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
}
